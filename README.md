## dst-admin:Steam饥荒联机版管理后台
> 工作之余，发挥余热，弄了一个steam版本的饥荒管理后台，方便有兴趣折腾服务器的小伙伴可以快速的搭建服务器。

## 支持的功能
1.  一键启动停止地面和洞穴服务
2.  支持服务器资源监控
3.  设置服务器房间信息，包括世界和MOD
4.  支持饥荒存档管理，存档恢复，可以手动备份，同时系统每天6点，
18点自动备份两次
6.  支持自动更新游戏，每天凌晨6点自动更新

## 要求
1.  环境要求**Ubuntu** 16.04 64位或32位（已测试，其他版本Ubuntu未测试）
2.  Ubuntu上需要安装**JDK 1.8**

## 注意事项
1.  服务默认监听端口8080
2.  默认用户名/密码 admin/123456

## 快速开始
###  在Ubuntu服务器中安装jdk1.8 
```
#更新软件源
sudo apt-get update
#安装openJDK1.8
sudo apt-get install openjdk-8-jdk
#查看版本
java -version
显示 "openjdk version "1.8.0_252"就表示安装完成
```      
###  下载最新版dst-admin安装包

```bash
curl -L https://github.com/qinming99/dst-admin/releases/download/v1.0.1/dst-admin-1.0.1.jar --output dst-admin.jar
```

或者

```bash
wget https://github.com/qinming99/dst-admin/releases/download/v1.0.1/dst-admin-1.0.1.jar  -O dst-admin.jar
```

###  启动dst-admin
```
#前台启动
java -jar dst-admin.jar 
#后台启动
nohup java -jar dst-admin.jar &
```
###  执行饥荒安装脚本，安装饥荒客户端
```
#启动完成将释放一个用于安装饥荒steam服务器的脚本
cd shell
#执行脚本，期间可能需要输入密码
./install.sh
```


## 饥荒交流群

QQ群： **1005887957**
