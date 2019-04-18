# quick-elasticsearch
ElasticSearch的使用笔记

[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)


这里除了会记录一些ES的使用代码和一些功能实现，也会有ELK的相关使用方法，如下图，比较简单的一个统计

 
[![职位仪表盘.png](https://i.loli.net/2018/04/03/5ac333ab0021a.png)](https://i.loli.net/2018/04/03/5ac333ab0021a.png)

代码里已经实现了

- createIndex 创建索引
- createMapping 创建mapping
- putDocument 批量索引(包括数据已存在更新，不存在就插入)
- deleteDocument

目前再继续完善，如果感兴趣那就请持续关注~~~

# ssb-jest-base

[branch](https://github.com/vector4wang/elasticsearch-quick/tree/sb-jest-base)

SpringBoot使用jest替代官方sdk的使用方法

简单易用，无需担心版本不兼容的问题

配置好es的链接参数，直接运行项目，程序会自动的创建索引、mapping并且会插入几个文档供接下来的CURD使用

如图
[![es-view.png](https://i.loli.net/2018/08/04/5b65658ea06d5.png)](https://i.loli.net/2018/08/04/5b65658ea06d5.png)
代码提供了最基本的增删改查功能
[![swagger-api.png](https://i.loli.net/2018/08/04/5b65658ea31e2.png)](https://i.loli.net/2018/08/04/5b65658ea31e2.png)

# ssb-jest

[branch](https://github.com/vector4wang/elasticsearch-quick/tree/sb-jest-analysis)

此分支主要是处理分词和搜索相关问题
使用IK分词 github地址：https://github.com/medcl/elasticsearch-analysis-ik
按照上面的说明将插件放在~/elasticsearch/plugins/ik下面，重启即可


## 分词情况

可以这样对字符串进行分词分析
```
post http://localhost:9200/recipes/_analyze
{
  "analyzer": "standard", 
  "text": "奶油鲍鱼汤"
}

```
### 默认分词器 Standard
如果不指定分词，则会使用ES默认的分词，分词结果就会变成一个一个的字符，如下
```json
{
    "tokens": [
        {
            "token": "奶",
            "start_offset": 0,
            "end_offset": 1,
            "type": "<IDEOGRAPHIC>",
            "position": 0
        },
        {
            "token": "油",
            "start_offset": 1,
            "end_offset": 2,
            "type": "<IDEOGRAPHIC>",
            "position": 1
        },
        {
            "token": "鲍",
            "start_offset": 2,
            "end_offset": 3,
            "type": "<IDEOGRAPHIC>",
            "position": 2
        },
        {
            "token": "鱼",
            "start_offset": 3,
            "end_offset": 4,
            "type": "<IDEOGRAPHIC>",
            "position": 3
        },
        {
            "token": "汤",
            "start_offset": 4,
            "end_offset": 5,
            "type": "<IDEOGRAPHIC>",
            "position": 4
        }
    ]
}
```

### IK分词
在创建mapping的时候指定分词，如下
```json
"jobName": {
  "type": "text",
  "analyzer": "ik_max_word",
  "search_analyzer": "ik_max_word"
}
```
ik分词有两种模式
- ik_max_word
```
{
    "tokens": [
        {
            "token": "奶油",
            "start_offset": 0,
            "end_offset": 2,
            "type": "CN_WORD",
            "position": 0
        },
        {
            "token": "鲍鱼",
            "start_offset": 2,
            "end_offset": 4,
            "type": "CN_WORD",
            "position": 1
        },
        {
            "token": "鱼汤",
            "start_offset": 3,
            "end_offset": 5,
            "type": "CN_WORD",
            "position": 2
        }
    ]
}
```
- ik_smart
```
{
    "tokens": [
        {
            "token": "奶油",
            "start_offset": 0,
            "end_offset": 2,
            "type": "CN_WORD",
            "position": 0
        },
        {
            "token": "鲍",
            "start_offset": 2,
            "end_offset": 3,
            "type": "CN_CHAR",
            "position": 1
        },
        {
            "token": "鱼汤",
            "start_offset": 3,
            "end_offset": 5,
            "type": "CN_WORD",
            "position": 2
        }
    ]
}
```
能直观的看出两者的区别，当然了IK也支持自定义呢分词字典，这个去github主页就能看到使用方法。

## 查询

[点我](http://www.cnblogs.com/yjf512/p/4897294.html)看term和match的区别

