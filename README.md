# jd-search-4-docker

三键启动所有服务
MySQL+ELK+JD-Search-Api

> 本来是想build成是哪个image，然后使用docker-compose一键启动的，但是做到后来发现compose虽说是按照顺序启动的，
但是并不会等你启动完毕后再去启动另外一个容器，官方相关链接:https://docs.docker.com/compose/startup-order/ 不想去折腾了
就还是按照分块的去启动吧

## Mysql启动与数据导入

在src/docker/mysql的根目录下执行
```bash
docker build -t jd-search-app-mysql .
```

等待构建结束，启动该容器
```bash
docker run -d -p 3306:3306 jd-search-app-mysql 
```
该过程包括mysql服务的启动--->数据的导入--->创建用户访问权限
日志与数据库如下

Delete Link
[![WX20180926-220649@2x.png](https://i.loli.net/2018/09/26/5bab930495914.png)](https://i.loli.net/2018/09/26/5bab930495914.png)
[![WX20180926-220739@2x.png](https://i.loli.net/2018/09/26/5bab9304d4b94.png)](https://i.loli.net/2018/09/26/5bab9304d4b94.png)



## elk部署
本来想只部署es的，后来想一想算了， 还是搞一套吧，dockerhub有现成的，直接用即可，命令如下

```bash
docker run --ulimit nofile=65536:65536 \
        -p 5601:5601 \
        -p 9200:9200 \
        -p 5044:5044 \
        -p 5045:5045 \
        -p 5046:5046 \
        -d --restart=always \
        --name jd-search-elk \
        sebp/elk
```
注：如果内存不是很够的话，可以加上下面参数
`-e ES_JAVA_OPTS="-Xms512m -Xmx512m"`
启动完毕，访问地址http://localhost:9200/就可以看到es的信息了
```json
{
    "name": "vK92yOz",
    "cluster_name": "elasticsearch",
    "cluster_uuid": "WMVWK588TeiunfuBWxDKxg",
    "version": {
    "number": "6.4.0",
    "build_flavor": "default",
    "build_type": "tar",
    "build_hash": "595516e",
    "build_date": "2018-08-17T23:18:47.308994Z",
    "build_snapshot": false,
    "lucene_version": "7.4.0",
    "minimum_wire_compatibility_version": "5.6.0",
    "minimum_index_compatibility_version": "5.0.0"
    },
    "tagline": "You Know, for Search"
}
```

## app

最后就是搜索服务了，直接启动该服务即可
过程包括
自动创建索引--->创建mapping--->自动索引数据(前提是上面两个服务都ok)

## 玩耍

搜索服务只简单的提供了三个接口，自己可以接着开发
使用kibana可以做些统计，如图
[![dashboard.png](https://i.loli.net/2018/09/27/5bace96366f3c.png)](https://i.loli.net/2018/09/27/5bace96366f3c.png)

