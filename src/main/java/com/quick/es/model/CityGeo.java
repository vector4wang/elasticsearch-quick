package com.quick.es.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author wangxc
 * @date: 2020/4/29 下午9:50
 *
 */
@NoArgsConstructor
@Data
public class CityGeo {

	/**
	 * name : 上海市
	 * log : 121.48
	 * lat : 31.22
	 * children : [{"name":"上海","log":"121.48","lat":"31.22"},{"name":"嘉定","log":"121.24","lat":"31.4"},{"name":"宝山","log":"121.48","lat":"31.41"},{"name":"川沙","log":"121.7","lat":"31.19"},{"name":"南汇","log":"121.76","lat":"31.05"},{"name":"奉贤","log":"121.46","lat":"30.92"},{"name":"松江","log":"121.24","lat":"31"},{"name":"金山","log":"121.16","lat":"30.89"},{"name":"青浦","log":"121.1","lat":"31.15"},{"name":"崇明","log":"121.4","lat":"31.73"}]
	 */

	private String name;
	private String log;
	private String lat;
	private List<ChildrenBean> children;

	@NoArgsConstructor
	@Data
	public static class ChildrenBean {
		/**
		 * name : 上海
		 * log : 121.48
		 * lat : 31.22
		 */

		private String name;
		private String log;
		private String lat;
	}
}
