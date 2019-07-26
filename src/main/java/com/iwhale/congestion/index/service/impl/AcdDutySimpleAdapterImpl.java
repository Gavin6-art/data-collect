package com.iwhale.congestion.index.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.iwhale.congestion.index.dto.AcdDutySimpleWritDto;
import com.iwhale.congestion.index.service.AcdDutySimpleAdapter;
import com.iwhale.congestion.index.service.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwhale.congestion.index.config.kafka.KafkaProducer;
import com.iwhale.congestion.index.config.kafka.KafkaProducerConfig;
import com.iwhale.congestion.index.dao.sgdao.AcdDutyMapper;
import com.ztesoft.uboss.bigdata.utils.common.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <Description> 简易事故认定书采集<br>
 *  
 * @author shilili<br>
 *
 */
@Slf4j
@Service
public class AcdDutySimpleAdapterImpl implements AcdDutySimpleAdapter {

	@Autowired
	private AcdDutyMapper acdDutyMapper;

	@Autowired
	private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;

	private boolean isFirstHandle = true;
	
	private long offset_time_s = 10 * 60 * 1000;
	
//	private static List<String> cache_xh = new ArrayList<>();

	@Override
	public void dbTaskHandler(){
		log.info("~~~~~~~~~开始采集简易事故认定书数据~~~~~~~~~");
		long qry_begin_time = System.currentTimeMillis() - offset_time_s;
/*		if (isFirstHandle) {
			isFirstHandle = false;
			cache_xh.addAll(getCacheXh(qry_begin_time));
		}*/
		log.info("cache_duty__simple_bh:{}", Cache.cache_duty_simple_bh.toString());
		List<AcdDutySimpleWritDto> jjds = qryAcdDutySimpleInfo(qry_begin_time);
		if(jjds != null && !jjds.isEmpty()){
			List<AcdDutySimpleWritDto> wids = new ArrayList<>();
			for(AcdDutySimpleWritDto dict : jjds){
				if(!Cache.cache_duty_simple_bh.contains(dict.getSgbh())){
					wids.add(dict);
					Cache.cache_duty_simple_bh.add(dict.getSgbh());
				}
			}
			if (0 != wids.size()) {
				log.info("简易事故认定书数据开始写入kafka");
				long startTime = System.currentTimeMillis();
				kafkaProducer.sendMessage("ACD_DUTY_SIMPLE_INFO", StringUtil.toJSON(wids));
				long endTime = System.currentTimeMillis();
				log.info("简易事故认定书数据发送kafka执行时间.......{}ms " , (endTime - startTime));
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
		List<String> dictList = acdDutyMapper.qryHistoryDutySimpleSgbh(timestamp);
		if(dictList != null && !dictList.isEmpty()){
            for(String dict : dictList){
                xhs.add(dict);
            }
        }
		return xhs;
	}

	@Override
	public List<AcdDutySimpleWritDto> qryAcdDutySimpleInfo(long beignTimestamp){
		List<AcdDutySimpleWritDto> result = null;
		result = acdDutyMapper.qryDutySimpleInfo(beignTimestamp);
		return result;
	}
}
