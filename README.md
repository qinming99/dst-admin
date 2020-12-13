## dst-admin:Steam平台饥荒联机版管理后台
> 工作之余，发挥余热，弄了一个steam平台的Don't Starve Together 饥荒联机版管理后台，支持傻瓜式服务器部署，方便有兴趣折腾服务器的小伙伴可以快速的搭建服务器。

## 支持的功能
1.  支持一键启动停止地面和洞穴服务
2.  支持服务器资源监控
3.  支持饥荒房间设置以及世界和MOD设置
4.  支持存档管理，存档恢复，自动备份
5.  支持无人值守时自动更新游戏
6.  支持设置额外管理员或玩家黑名单
7.  支持饥荒运行日志查看
8.  支持上传本地存档

## 环境要求
1.  系统需**Ubuntu** （16.04,18.04已测试，其他版本Ubuntu未测试,其他发行版Linux的搭建可以加群交流）
2.  Java环境需要**JDK 1.8**

## 注意事项
1.  服务默认监听端口8080
2.  默认用户名/密码 admin/123456
3.  饥荒监听端口**10888，10999，10998**（建议开放所有端口，避免一些问题）

## 快速开始
###  在Ubuntu服务器中安装jdk1.8 
```
#更新软件源
sudo apt-get update
#安装openJDK1.8
sudo apt-get install -y openjdk-8-jdk
#查看版本
java -version
显示 "openjdk version "1.8.0_252"就表示安装完成
```      
###  下载最新版dst-admin安装包

```bash
wget http://clouddn.tugos.cn/release/dst-admin-1.0.5.jar -O dst-admin.jar
```

或者

```bash
curl -L https://github.com/qinming99/dst-admin/releases/download/v1.0.5/dst-admin-1.0.5.jar --output dst-admin.jar
```

或者

```bash
wget https://github.com/qinming99/dst-admin/releases/download/v1.0.5/dst-admin-1.0.5.jar  -O dst-admin.jar
```

###  启动dst-admin
```
#启动
java -jar dst-admin.jar 
```
###  执行饥荒安装脚本，安装饥荒客户端
```
#启动完成将释放install.sh脚本，用于安装steam饥荒客户端
#执行脚本，期间可能需要输入密码（可能由于网络问题导致中断需要执行多次该脚本）
./install.sh
```
###  使用dstStart.sh脚本管理dst-admin服务
```
#执行dstStart.sh脚本按照指示进行
./dstStart.sh
```


## 预览图

![img](https://github.com/qinming99/dst-admin/blob/master/images/image1.png)
![img](https://github.com/qinming99/dst-admin/blob/master/images/image2.png)
![img](https://github.com/qinming99/dst-admin/blob/master/images/image3.png)

## 饥荒交流群

QQ群： **1005887957**


## END

- 如果有任何建议或者 Bug:sob: 可以提 issue ，或者可以直接加群联系
