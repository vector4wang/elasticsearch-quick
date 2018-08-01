package com.quick.es.service;

import com.quick.es.entity.Recipes;

import java.util.List;

/**
 * Created with IDEA
 * User: vector
 * Data: 2018/4/3 0003
 * Time: 14:06
 * Description:
 */
public interface RecipesService {

	/**
	 * 创建索引
	 */
	void createIndex();

	/**
	 * 创建映射
	 */
	void createIndexMapping();

	/**
	 * 插入测试数据
	 */
	void insert();

	/**
	 * 批量插入
	 */
	void bulkInsert();



	void save(Recipes recipes);

	/**
	 * 根据文档ID删除文档
	 * @param id
	 * @return
	 */
	boolean deleteByDocId(String id);

	List<Recipes> search(String content);
}
