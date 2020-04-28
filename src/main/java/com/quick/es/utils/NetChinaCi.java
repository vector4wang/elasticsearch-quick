package com.quick.es.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 *
 * @author wangxc
 * @date: 2020/4/21 下午8:30
 *
 */
public class NetChinaCi {
	public static String generateChinaCi()  {
		System.out.println("=====================");
		HashMap<String, String> param = new HashMap<>();
		param.put("line_number", "10");
		param.put("use_three", "on");
		Document doc = null;
		try {
			doc = Jsoup.connect("http://songci.herokuapp.com/").data(param).timeout(10000).execute().parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content = doc.select("#text").text();
		String replace = content.replace("\"", "");
		content = replace.replaceAll("(\\r\\n|\\n|\\n\\r)", " ");
		try {
			IoUtil.write(new FileOutputStream(new File("/Users/wangxc/Code/Github/elasticsearch-quick/doc/cis/" + UUID.fastUUID() + ".txt")), Charset.defaultCharset(), true, content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(content);
		return content;
	}
}
