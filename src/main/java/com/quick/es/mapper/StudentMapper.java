package com.quick.es.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quick.es.entity.Student;
import com.quick.es.model.EsStudent;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vector4wang
 * @since 2020-01-06
 */
public interface StudentMapper extends BaseMapper<Student> {

	List<EsStudent> selectAllStudentScore();

}
