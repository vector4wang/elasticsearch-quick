package com.quick.es.mapper;

import com.quick.es.entity.BossJdInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BossJdInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BossJdInfo record);

    int insertSelective(BossJdInfo record);

    BossJdInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BossJdInfo record);

    int updateByPrimaryKeyWithBLOBs(BossJdInfo record);

    int updateByPrimaryKey(BossJdInfo record);

	List<BossJdInfo> selectAll();
}