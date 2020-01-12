# springboot-jest 

先用docker安装一个ELK
```bash
## 下载镜像，这里使用7.0以下的
docker pull sebp/elk:654

## 启动
docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -d -it --name elk sebp/elk

```

然后可以再浏览器访问http://localhost:5601 按到Kibana的页面了

SpringBoot使用jest替代官方sdk的使用方法

简单易用，无需担心版本不兼容的问题

配置好es的链接参数，直接运行项目，程序会自动的创建索引、mapping并且会插入几个文档供接下来的CURD使用

如图
[![es-view.png](https://i.loli.net/2018/08/04/5b65658ea06d5.png)](https://i.loli.net/2018/08/04/5b65658ea06d5.png)
代码提供了最基本的增删改查功能
[![swagger-api.png](https://i.loli.net/2018/08/04/5b65658ea31e2.png)](https://i.loli.net/2018/08/04/5b65658ea31e2.png)
