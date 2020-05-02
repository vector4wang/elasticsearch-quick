package com.quick.es;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.quick.es.model.CityGeo;
import com.quick.es.utils.NetChinaCi;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 *
 * @author wangxc
 * @date: 2020/4/21 下午8:20
 *
 */
public class TestMain {
	public static void main(String[] args) throws IOException {
		List<File> files = FileUtil.loopFiles("/Users/wangxc/Code/Github/elasticsearch-quick/doc/cis/");
		for (File file : files) {
			String s = FileUtil.readString(file, Charset.defaultCharset());
			System.out.println(s);
		}
	}
}
