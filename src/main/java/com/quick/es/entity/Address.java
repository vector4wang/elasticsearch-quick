package com.quick.es.entity;

import lombok.Data;

/**
 *
 * @author wangxc
 * @date: 2020/4/28 下午10:47
 *
 */
@Data
public class Address {
	private String province;
	private String city;
	private String streets;
	private String lat;
	private String lon;
}
