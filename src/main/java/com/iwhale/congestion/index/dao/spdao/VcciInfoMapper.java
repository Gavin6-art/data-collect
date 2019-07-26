package com.iwhale.congestion.index.dao.spdao;

import com.iwhale.congestion.index.dto.VioDataDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VcciInfoMapper {

	List<String> qryHistoryXh(long timestamp);

	List<VioDataDto> qryWfInfo(long beignTimestamp);

}
