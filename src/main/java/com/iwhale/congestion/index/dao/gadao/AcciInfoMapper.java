package com.iwhale.congestion.index.dao.gadao;

import com.iwhale.congestion.index.dto.AcciInfoDataDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AcciInfoMapper {

	List<String> qryHistoryXh(long timestamp);

	List<AcciInfoDataDto> qryWxSgInfo(long beignTimestamp);

}
