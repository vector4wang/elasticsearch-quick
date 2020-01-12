package com.quick.es.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author vector4wang
 * @since 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CourseScore implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;

	private Integer stuId;

	private String className;

	private Double score;


}
