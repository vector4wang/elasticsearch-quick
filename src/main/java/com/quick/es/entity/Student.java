package com.quick.es.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class Student implements Serializable {

	public final static String INDEX_NAME = "student";
	public final static String TYPE_NAME = "student_score";


    private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;

    private String name;

	private Integer age;


}
