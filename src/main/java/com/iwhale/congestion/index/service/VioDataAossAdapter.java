package com.iwhale.congestion.index.service;



import com.iwhale.congestion.index.dto.VioDataDto;

import java.util.List;

/**
 * Created by sll on 2019/4/18.
 */
public interface VioDataAossAdapter {

    void restTaskHandlerGaoDe();


    /**
     * get xh
     * @param timestamp
     * @return
     */
    List<String> getCacheXh(long timestamp);

    /**
     * get wf data
     * @param beignTimestamp
     * @return
     */
    List<VioDataDto> qryWfInfo(long beignTimestamp);

}
