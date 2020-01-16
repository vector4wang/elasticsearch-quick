# springboot-jest 

先用docker安装一个ELK
```bash
## 下载镜像，这里使用7.0以下的
docker pull sebp/elk:654

## 启动
docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -d -it --name elk sebp/elk

```

然后可以再浏览器访问http://localhost:5601 按到Kibana的页面了

