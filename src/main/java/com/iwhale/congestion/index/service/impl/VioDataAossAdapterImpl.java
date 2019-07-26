package com.iwhale.congestion.index.service.impl;

import com.iwhale.congestion.index.config.kafka.KafkaProducer;
import com.iwhale.congestion.index.config.kafka.KafkaProducerConfig;
import com.iwhale.congestion.index.dao.spdao.VcciInfoMapper;
import com.iwhale.congestion.index.dto.VioDataDto;
import com.iwhale.congestion.index.service.VioDataAossAdapter;
import com.ztesoft.uboss.bigdata.utils.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilili
 *
 */
@Slf4j
@Service
public class VioDataAossAdapterImpl implements VioDataAossAdapter {

    @Autowired
    private VcciInfoMapper vcciInfoMapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;

    private boolean isFirstHandle = true;

    private long offset_time_s = 3600 * 1000;

    private List<String> cache_xh = new ArrayList<>();

    @Override
    public void restTaskHandlerGaoDe() {
        log.info("~~~~~~~~~开始采集违法数据~~~~~~~~~");
        long qry_begin_time = System.currentTimeMillis() - offset_time_s;
        if (isFirstHandle) {
            isFirstHandle = false;
            cache_xh.addAll(getCacheXh(qry_begin_time));
        }

        List<VioDataDto> jjds = qryWfInfo(qry_begin_time);
        if(jjds != null && !jjds.isEmpty()){
            List<VioDataDto> wids = new ArrayList<>();
            for(VioDataDto dict : jjds){
                if(!cache_xh.contains(dict.getLsh())){
                    wids.add(dict);
                    cache_xh.add(dict.getLsh());
                }
            }
            if (0 != wids.size()) {
                log.info("违法数据开始写入kafka");
                long startTime = System.currentTimeMillis();
                kafkaProducer.sendMessage("AD_VIO_INFO", StringUtil.toJSON(wids));
                long endTime = System.currentTimeMillis();
                log.info("违法数据发送kafka执行时间.......{}ms " , (endTime - startTime));
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
        List<String> dictList = vcciInfoMapper.qryHistoryXh(timestamp);
        if(dictList != null && !dictList.isEmpty()){
            for(String dict : dictList){
                xhs.add(dict);
            }
        }
        return xhs;
    }

    /**
     * get wf date
     * @param beignTimestamp
     * @return
     */
    @Override
    public List<VioDataDto> qryWfInfo(long beignTimestamp){
        List<VioDataDto> result = null;
        result = vcciInfoMapper.qryWfInfo(beignTimestamp);
        return result;
    }
}
