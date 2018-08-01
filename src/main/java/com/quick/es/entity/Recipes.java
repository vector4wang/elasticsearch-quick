package com.quick.es.entity;

import java.io.Serializable;

/**
 * @author vector
 * @Data 2018/8/1 0001
 * @Description 文档类型
 */
public class Recipes implements Serializable {

	public static final String INDEX_NAME = "recipes";
	public static final String TYPE = "item";

	private Long id;
	private String name;
	private float rating;
	private String type;

	public Recipes() {
	}

	public Recipes(Long id, String name, float rating, String type) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
