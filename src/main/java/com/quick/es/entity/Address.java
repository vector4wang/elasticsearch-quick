package com.quick.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
	private Location location;

	@AllArgsConstructor
	@Data
	public static class Location {
		private String lat;
		private String lon;
	}
}
