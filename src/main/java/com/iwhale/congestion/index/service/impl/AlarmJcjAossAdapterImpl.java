package com.iwhale.congestion.index.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iwhale.congestion.index.config.kafka.KafkaProducer;
import com.iwhale.congestion.index.config.kafka.KafkaProducerConfig;
import com.iwhale.congestion.index.dao.spdao.AlarmJcjMapper;
import com.iwhale.congestion.index.dto.AlarmCJDDateDto;
import com.iwhale.congestion.index.dto.AlarmJJDDateDto;
import com.iwhale.congestion.index.service.AlarmJcjAossAdapter;
import com.ztesoft.uboss.bigdata.utils.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <Description> 警情采集<br> 
 *  
 * @author shilili<br>
 *
 */
@Slf4j
@Service
public class AlarmJcjAossAdapterImpl implements AlarmJcjAossAdapter {

	@Autowired
	private AlarmJcjMapper warnInstanceGetDataDao;

	@Autowired
	private KafkaProducer kafkaProducer;

	@Autowired
	private KafkaProducerConfig kafkaProducerConfig;

	private boolean isFirstHandle = true;
	
	private long offset_time_s = 3600 * 1000;
	
	private List<String> cache_jjdbh = new ArrayList<>();

	private List<String> cache_cjdbh = new ArrayList<String>();

    private String convert_base_path = "http://33.104.18.119:25001/as/coord/convert?ak=ec85d3648154874552835438ac6a02b2";

	@Override
	public void dbTaskHandlerAlarm(){
		log.info("~~~~~~~~~开始采集110接报警数据~~~~~~~~~");
		long qry_begin_time = System.currentTimeMillis() - offset_time_s;
		if (isFirstHandle) {
			isFirstHandle = false;
			cache_jjdbh.addAll(getCacheJJDbh(qry_begin_time));
			cache_cjdbh.addAll(getCacheCJDbh(qry_begin_time));
		}

		List<AlarmJJDDateDto> jjds = qryjjd(qry_begin_time);
		if(jjds != null && !jjds.isEmpty()){
			List<AlarmJJDDateDto> wids = new ArrayList<>();
			for(AlarmJJDDateDto dict : jjds){
				if(!cache_jjdbh.contains(dict.getJjdbh())){
					convert(dict);
					wids.add(dict);
					cache_jjdbh.add(dict.getJjdbh());
				}
			}
			if (0 != wids.size()) {
				log.info("接警单数据开始写入kafka");
				long startTime = System.currentTimeMillis();
				kafkaProducer.sendMessage("AD_JCJ_JJD_INFO", StringUtil.toJSON(wids));
				long endTime = System.currentTimeMillis();
				log.info("接警单数据发送kafka执行时间.......{}ms " , (endTime - startTime));
			}
		}

		List<AlarmCJDDateDto> cjds = qrycjd(qry_begin_time);
		if(cjds != null && !jjds.isEmpty()){
			List<AlarmCJDDateDto> wids = new ArrayList<AlarmCJDDateDto>();
			for(AlarmCJDDateDto dict : cjds){
				if(!cache_cjdbh.contains(dict.getCjdbh())){
					wids.add(dict);
					cache_cjdbh.add(dict.getCjdbh());
				}
			}
			if (0 != wids.size()) {
				log.info("处警单数据开始写入kafka");
				long startTime = System.currentTimeMillis();
				kafkaProducer.sendMessage("AD_JCJ_CJD_INFO", StringUtil.toJSON(wids));
				long endTime = System.currentTimeMillis();
				log.info("处警单数据发送kafka执行时间.......{}ms " , (endTime - startTime));
			}
		}
	}

	/**
	 * get jjdbh
	 * @param timestamp
	 * @return
	 */
	@Override
	public List<String> getCacheJJDbh(long timestamp){
		List<String> jjdbhs = new ArrayList<>();
		List<String> dictList = warnInstanceGetDataDao.qryHistoryJJD(timestamp);
		if(dictList != null && !dictList.isEmpty()){
            for(String dict : dictList){
                jjdbhs.add(dict);
            }
        }
		return jjdbhs;
	}

	/**
	 * get cjdbh
	 * @param timestamp
	 * @return
	 */
	@Override
	public List<String> getCacheCJDbh(long timestamp){
		List<String> cjdbhs = new ArrayList<>();
		List<String> dictList = warnInstanceGetDataDao.qryHistoryCJD(timestamp);
		if(dictList != null && !dictList.isEmpty()){
            for(String dict : dictList){
                cjdbhs.add(dict);
            }
        }
		return cjdbhs;
	}

    private void convert(AlarmJJDDateDto wid){
        if (wid.getDhdwjd() == null || wid.getDhdwwd() == null
                || "".equals(wid.getDhdwjd()) || "".equals(wid.getDhdwwd())) {
            return;
        }
        String request_path = this.convert_base_path + "&coords="
                + wid.getDhdwjd() + "," + wid.getDhdwwd();

        BufferedReader br = null;
        try{
            URL realUrl = new URL(request_path);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            String result = stringBuffer.toString();
            JSONObject jsonobj = JSONObject.parseObject(result);
            String status = jsonobj.getString("status");
            if("0".equals(status)){
                JSONArray xys = jsonobj.getJSONArray("result");
                if(xys != null && xys.size() > 0){
                    JSONObject xy = xys.getJSONObject(0);
                    wid.setDhdwjd(xy.getString("x"));
                    wid.setDhdwwd(xy.getString("y"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * get jjd date
	 * @param beignTimestamp
	 * @return
	 */
	@Override
	public List<AlarmJJDDateDto> qryjjd(long beignTimestamp){
		List<AlarmJJDDateDto> result = null;
		result = warnInstanceGetDataDao.qryjjd(beignTimestamp);
		return result;
	}

	/**
	 * get cjd date
	 * @param beignTimestamp
	 * @return
	 */
	@Override
	public List<AlarmCJDDateDto> qrycjd(long beignTimestamp){
		List<AlarmCJDDateDto> result = null;
		result = warnInstanceGetDataDao.qrycjd(beignTimestamp);
		return result;
	}

}
