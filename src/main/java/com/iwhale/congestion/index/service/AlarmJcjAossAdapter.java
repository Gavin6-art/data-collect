package com.iwhale.congestion.index.service;

import com.iwhale.congestion.index.dto.AlarmCJDDateDto;
import com.iwhale.congestion.index.dto.AlarmJJDDateDto;

import java.util.List;

/**
 * Created by sll on 2019/4/18.
 */
public interface AlarmJcjAossAdapter {

    void dbTaskHandlerAlarm();

    /**
     * get jjdbh
     * @param timestamp
     * @return
     */
    List<String> getCacheJJDbh(long timestamp);

    /**
     * get cjdbh
     * @param timestamp
     * @return
     */
    List<String> getCacheCJDbh(long timestamp);

    /**
     * get jjd data
     * @param beignTimestamp
     * @return
     */
    List<AlarmJJDDateDto> qryjjd(long beignTimestamp);

    /**
     * get cjd data
     * @param beignTimestamp
     * @return
     */
    List<AlarmCJDDateDto> qrycjd(long beignTimestamp);
}
