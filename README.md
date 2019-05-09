# springboot-jest 

使用jest 测试 ES 7.x的使用情况，可能会遇到的问题如下

- _all无效，已经不支持这个字段
- Mapping方法无效，使用Map
- jieba插件升级到7.0版本
- 默认5分片5副本调整为1分片1副本
- 基于性能提升，默认查询仅返回10000条数据，使用track_total_hits返回所有数据
- nested_path、nested_filter无效
- aggs返回结构调整
- aggs filter空条件会报错
- perference=_primary_first无效
- 分片刷新机制不一样，当不设置refresh_interval时，只有请求时才会刷新




# 取消了type

目前jest对es的版本支持对应关系如下

Jest Version | Elasticsearch Version
--- | ---
\>= 6.0.0 | 6
\>= 5.0.0 | 5
\>= 2.0.0 | 2
0.1.0 - 1.0.0 | 1
<= 0.0.6 | < 1

所以目前还没有针对es 7.x的版本，但是jest聪明的是，它的请求与返回都是可以拼接的，我们可以把请求参数中的type置空，然后在响应体中，可以直接过去jsonstring
当然了es7.0 改版的只是一小部分接口，大多数的都不需要怎么改变，但是一定要测试过了才能切换~

个人使用和测试api的方式，分别打开如下两个链接
https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-mapping.html

https://github.com/searchbox-io/Jest/blob/master/jest/src/test/java/io/searchbox/indices/GetMappingIntegrationTest.java

一个是ES的官方文档，一个是jest的IntegrationTest，两者结合，效率杠杠的~~~~

