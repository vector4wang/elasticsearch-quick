package com.quick.es.entity;

import lombok.Data;

import java.util.List;

/**
 *
 * @author wangxc
 * @date: 2020年04月20日
 *
 */
@Data
public class Student {


	/**
	 * uid : 10101
	 * nick : test10
	 */

	private String uid;
	private String userName;
	private int age;
	private String birthday;
	private String content;
	private Address address;
	private List<Subject> subjects;
	private Double totalScore;
}
