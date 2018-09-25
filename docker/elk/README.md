## 启动命令

```bash
docker run --ulimit nofile=65536:65536 -p 5601:5601 -p 9200:9200 -p 5044:5044 -p 5045:5045 -p 5046:5046 -d --restart=always --name jd-search-elk sebp/elk




-e ES_JAVA_OPTS="-Xms512m -Xmx512m"# 不知为何用-v /etc/logstash:/etc/logstash-v /etc/localtime:/etc/localtime

```