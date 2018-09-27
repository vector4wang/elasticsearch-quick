#!/bin/bash 
set -e 
echo `service mysql status`
echo '1.启动mysql'
service mysql start
sleep 3
echo `service mysql status` 
echo '2.导入数据'
mysql < /mysql/data.sql
sleep 3
echo `service mysql status` 
echo '4.修改用户权限'
mysql < /mysql/privileges.sql
#sleep 3
echo `service mysql status` 
echo 'mysql启动成功'

# 让该脚本挂起，否则docker会停止该容器
tail -f /dev/null
