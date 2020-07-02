### 准备

1、没有ES环境的可以用docker快速装一个

注意版本号，此处用的事6.7.0

```bash
docker pull sebp/elk:640
```

这里使用的是Transport Client链接，用的事9300端口，所以要修改一下容器中Elasticsearch的配置文件，具体问题看这里[issues/162](https://github.com/docker-library/elasticsearch/issues/162)

2、创建一个配置文件

elasticsearch-slave.yml内容如下

```yml
transport.host: 0.0.0.0
http.host: 0.0.0.0
discovery.zen.minimum_master_nodes: 1
```



3、启动容器

```bash
sudo docker run -it -p 5601:5601  -p 5044:5044 -p 9200:9200 \
		-p 9300:9300 \
  	-v /Users/wangxc/Develop/docker/elk6709300/elasticsearch-slave.yml:/etc/elasticsearch/elasticsearch.yml \
  	--name elk670transport  75d33974662
```

75d33974662是image编号，你也可以直接使用sebp/elk:640



4、访问Kibana

启动完成之后，访问http://localhost:5601/app/kibana#/dev_tools/，可以在这里写script

### Rollover使用步骤

1、创建索引模板

目的是让后面自动生成的索引都按照这个模板来创建

```
PUT _template/order-template
{
  "index_patterns": "order-*",
  "settings": {
    "index": {
      "number_of_shards": "3",
      "number_of_replicas": "1"
    }
  },
  "mappings": {
    "_doc": {
      "_source": {
        "enabled": true
      },
      "properties": {
        "uid": {
          "type": "keyword"
        },
        "nick": {
          "type": "keyword"
        },
        "chatTime": {
          "type": "date",
          "format": "yyyy-MM-dd HH:mm:ss"
        }
      }
    }
  },
  "aliases": {
    "order-search": {}
  }
}
```



2、指定写索引别名

```
PUT /order-20200320
{
  "aliases": {
    "order-write": {}
  }
}
```

有生成别名规则



3、定期执行Rollover请求

```
POST /order-write/_rollover/order-20200323
{
  "conditions": {
   "max_age": "3s"
   "max_doc": 10
  }
}
```

- 这一动作要定期去出发，代码里写个定时任务就能搞定

- `conditions`是判断上一个索引是否满足，满足的话就rollover，不满足就不动

    基于此，你就可以在任意时间任意条件去创建下一个索引了

4、要自己定义的几个名字有

- 索引模板名：order-template
- 索引正则表达式：order-*
- 搜索索引名： order-search
- 写索引名：order-write
- 物理索引名：order-20200320或按照你自己定义的来，但是要满足order-*

### 实现

看代码，直接运行


### 多索引
需求如图
![multi-alias](https://github.com/vector4wang/elasticsearch-quick/blob/sb-transport-rollover/doc/multi-alias.png)


```bash
PUT _template/travel-manual-template
{
  "index_patterns": "travel-manual-*",
  "settings": {
    "index": {
      "number_of_shards": "3",
      "number_of_replicas": "1"
    }
  },
  "mappings": {
    "_doc": {
      "_source": {
        "enabled": true
      },
      "properties": {
        "clutch": {
          "type": "keyword"
        },
        "steering_wheel": {
          "type": "keyword"
        },
        "seat": {
          "type": "keyword"
        },
        "navigator": {
          "type": "keyword"
        }
      }
    }
  },
  "aliases": {
    "travel-manual-search": {},
    "travel-search": {}
  }
}

PUT /travel-manual-20200702
{
  "aliases": {
    "travel-manual-write": {}
  }
}

######################################

PUT _template/travel-auto-template
{
  "index_patterns": "travel-auto-*",
  "settings": {
    "index": {
      "number_of_shards": "3",
      "number_of_replicas": "1"
    }
  },
  "mappings": {
    "_doc": {
      "_source": {
        "enabled": true
      },
      "properties": {
        "clutch": {
          "type": "keyword"
        },
        "steering_wheel": {
          "type": "keyword"
        },
        "seat": {
          "type": "keyword"
        },
        "navigator": {
          "type": "keyword"
        }
      }
    }
  },
  "aliases": {
    "travel-auto-search": {},
    "travel-search": {}
  }
}


PUT /travel-auto-20200702
{
  "aliases": {
    "travel-auto-write": {}
  }
}



POST travel-auto-write/_doc
{
  "clutch":"auto黑哦黑clutch",
  "steering_wheel":"auto黑哦黑steering_wheel",
  "seat":"auto黑哦黑seat",
  "navigator":"auto黑哦黑哈seat"
}

POST travel-manual-write/_doc
{
  "clutch":"amanual黑哦黑clutch1",
  "steering_wheel":"manual黑哦黑steering_wheel1",
  "seat":"manual黑哦黑seat1",
  "navigator":"manual黑哦黑哈seat1"
}


GET travel-auto-search/_search
GET travel-manual-search/_search
GET travel-search/_search

```

> 两类数据分别对应两个索引模板，并且有各自的rollover条件，各自有索引别名，同时他们有共同的别名
>三个索引，完成单独对A，单独对B查，也可以通过索引C同时查询A和B

数据格式最好一样
