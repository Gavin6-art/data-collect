package com.iwhale.congestion.index.service;


import com.iwhale.congestion.index.dto.AcciInfoDataDto;

import java.util.List;

/**
 * Created by sll on 2019/4/18.
 */
public interface AcciInfoAossAdapter {

    void dbTaskHandlerAlarm();

    /**
     * get xh
     * @param timestamp
     * @return
     */
    List<String> getCacheXh(long timestamp);

    /**
     * get wxsg data
     * @param beignTimestamp
     * @return
     */
    List<AcciInfoDataDto> qryWxSgInfo(long beignTimestamp);

}
