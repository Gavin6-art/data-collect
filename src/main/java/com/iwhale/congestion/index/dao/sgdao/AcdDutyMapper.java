package com.iwhale.congestion.index.dao.sgdao;

import java.util.List;

import com.iwhale.congestion.index.dto.AcdDutyDto;
import com.iwhale.congestion.index.dto.AcdDutySimpleWritDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AcdDutyMapper {

	List<String> qryHistoryDutySgbh(long timestamp);

	List<AcdDutyDto> qryDutyInfo(long beignTimestamp);

	List<String> qryHistoryDutySimpleSgbh(long timestamp);

	List<AcdDutySimpleWritDto> qryDutySimpleInfo(long beignTimestamp);

}
