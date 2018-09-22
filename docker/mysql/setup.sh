#!/bin/bash 
set -e 
# 查看mysql服务的状态，方便调试
echo `service mysql status` 
echo '1.启动mysql' 
# 启动mysql 
service mysql start 
# 使进程休眠
sleep 3 
echo `service mysql status` 
echo '2.开始导入数据' 
#导入sql文件
mysql < /mysql/data.sql 
echo '3.导入数据完毕....' 
sleep 3 
echo `service mysql status` 
# 由于最开始设置mysqlmysql为免密登陆，为了安全，在此设置mysql密码
echo '4.开始修改密码....' 
# 导入修改mysql权限设置的文件
mysql < /mysql/privileges.sql 
echo '5.修改密码完毕....' 
#sleep 3 
echo `service mysql status` 
echo 'mysql容器启动完毕,且数据导入成功' 

# 在docker容器中防止执行完之后自行停止，加上下面一句命令让其挂起
tail -f /dev/null
