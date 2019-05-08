package com.quick.es.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quick.es.entity.ResumeFeatureSimHashIndexModel;
import com.quick.es.entity.ResumeFeatureSimhash;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vector
 * @since 2019-03-29
 */
public interface ResumeFeatureSimhashMapper extends BaseMapper<ResumeFeatureSimhash> {

    ResumeFeatureSimHashIndexModel selectIndexModelById(@Param("resumeId") String resumeId);

    List<String> selectIdByPage(@Param("start") int start,@Param("offset")int offset);
}
