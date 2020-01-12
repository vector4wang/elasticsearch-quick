package com.quick.es.model;

import com.quick.es.entity.CourseScore;
import lombok.Data;

import java.util.List;

/**
 *
 * @author wangxc
 * @date: 2020/1/6 下午9:45
 *
 */
@Data
public class EsStudent {
	private String id;
	private String name;
	private Integer age;
	private List<EsCourseScore> courseScores;
}
