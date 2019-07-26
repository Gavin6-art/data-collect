package com.iwhale.congestion.index.service.impl;

import com.iwhale.congestion.index.config.kafka.KafkaProducer;
import com.iwhale.congestion.index.config.kafka.KafkaProducerConfig;
import com.iwhale.congestion.index.dao.gadao.AcciInfoMapper;
import com.iwhale.congestion.index.dto.AcciInfoDataDto;
import com.iwhale.congestion.index.service.AcciInfoAossAdapter;
import com.ztesoft.uboss.bigdata.utils.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <Description> 微信上报事故采集<br>
 *  
 * @author shilili<br>
 *
 */
@Slf4j
@Service
public class AcciInfoAossAdapterImpl implements AcciInfoAossAdapter {

	@Autowired
	private AcciInfoMapper acciInfoMapper;

	@Autowired
	private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;

	private boolean isFirstHandle = true;
	
	private long offset_time_s = 3600 * 1000;
	
	private List<String> cache_xh = new ArrayList<>();

	@Override
	public void dbTaskHandlerAlarm(){
		log.info("~~~~~~~~~开始采集微信上报事故数据~~~~~~~~~");
		long qry_begin_time = System.currentTimeMillis() - offset_time_s;
		if (isFirstHandle) {
			isFirstHandle = false;
			cache_xh.addAll(getCacheXh(qry_begin_time));
		}

		List<AcciInfoDataDto> jjds = qryWxSgInfo(qry_begin_time);
		if(jjds != null && !jjds.isEmpty()){
			List<AcciInfoDataDto> wids = new ArrayList<>();
			for(AcciInfoDataDto dict : jjds){
				if(!cache_xh.contains(dict.getXh())){
					wids.add(dict);
					cache_xh.add(dict.getXh());
				}
			}
			if (0 != wids.size()) {
			log.info("微信上报事故数据开始写入kafka");
			long startTime = System.currentTimeMillis();
			kafkaProducer.sendMessage("AD_ACCI_INFO", StringUtil.toJSON(wids));
			long endTime = System.currentTimeMillis();
			log.info("微信上报事故数据发送kafka执行时间.......{}ms " , (endTime - startTime));
			}
		}
	}


	/**
	 * get xh
	 * @param timestamp
	 * @return
	 */
	@Override
	public List<String> getCacheXh(long timestamp){
		List<String> xhs = new ArrayList<>();
		List<String> dictList = acciInfoMapper.qryHistoryXh(timestamp);
		if(dictList != null && !dictList.isEmpty()){
            for(String dict : dictList){
                xhs.add(dict);
            }
        }
		return xhs;
	}

	/**
	 * get wxsg date
	 * @param beignTimestamp
	 * @return
	 */
	@Override
	public List<AcciInfoDataDto> qryWxSgInfo(long beignTimestamp){
		List<AcciInfoDataDto> result = null;
		result = acciInfoMapper.qryWxSgInfo(beignTimestamp);
		return result;
	}
}
