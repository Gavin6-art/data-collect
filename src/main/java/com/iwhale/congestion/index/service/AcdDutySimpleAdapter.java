package com.iwhale.congestion.index.service;


import java.util.List;

import com.iwhale.congestion.index.dto.AcdDutySimpleWritDto;

public interface AcdDutySimpleAdapter {

    void dbTaskHandler();

    List<String> getCacheXh(long timestamp);

    List<AcdDutySimpleWritDto> qryAcdDutySimpleInfo(long beignTimestamp);

}
