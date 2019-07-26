package com.iwhale.congestion.index.service;


import java.util.List;

import com.iwhale.congestion.index.dto.AcdDutyDto;

public interface AcdDutyAdapter {

    void dbTaskHandler();

    List<String> getCacheXh(long timestamp);

    List<AcdDutyDto> qryAcdDutyInfo(long beignTimestamp);

}
