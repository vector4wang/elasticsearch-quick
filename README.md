# quick-elasticsearch
ElasticSearch的使用笔记

这里除了会记录一些ES的使用代码和一些功能实现，也会有ELK的相关使用方法，如下图，比较简单的一个统计

 
[![职位仪表盘.png](https://i.loli.net/2018/04/03/5ac333ab0021a.png)](https://i.loli.net/2018/04/03/5ac333ab0021a.png)

代码里已经实现了

- createIndex 创建索引
- createMapping 创建mapping
- putDocument 批量索引(包括数据已存在更新，不存在就插入)
- deleteDocument

目前再继续完善，如果感兴趣那就请持续关注~~~