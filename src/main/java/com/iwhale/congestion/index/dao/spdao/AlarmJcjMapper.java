package com.iwhale.congestion.index.dao.spdao;

import com.iwhale.congestion.index.dto.AlarmCJDDateDto;
import com.iwhale.congestion.index.dto.AlarmJJDDateDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AlarmJcjMapper {

	List<String> qryHistoryJJD(long timestamp);

	List<String> qryHistoryCJD(long timestamp);

	List<AlarmJJDDateDto> qryjjd(long beignTimestamp);

	List<AlarmCJDDateDto> qrycjd(long beignTimestamp);

}
