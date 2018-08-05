package com.quick.es.entity;

import java.io.Serializable;

/**
 * @author vector
 * @Data 2018/8/1 0001
 * @Description 文档类型
 */
public class Recipes implements Serializable {

	public static final String INDEX_NAME = "recipes_analyzed";
	public static final String TYPE = "item";

	private Long id;
	private String name4KeyWord; // 不分词
	private String name4Standard;// 默认的standard分词
	private String name4IK;// 使用ik分词
	private float rating;
	private String type;

	public Recipes(Long id, String name4Standard, float rating, String type) {
		this.id = id;
		this.name4Standard = name4Standard;
		this.name4IK = name4Standard;
		this.name4KeyWord = name4Standard;
		this.rating = rating;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName4KeyWord() {
		return name4KeyWord;
	}

	public void setName4KeyWord(String name4KeyWord) {
		this.name4KeyWord = name4KeyWord;
	}

	public String getName4Standard() {
		return name4Standard;
	}

	public void setName4Standard(String name4Standard) {
		this.name4Standard = name4Standard;
	}

	public String getName4IK() {
		return name4IK;
	}

	public void setName4IK(String name4IK) {
		this.name4IK = name4IK;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
