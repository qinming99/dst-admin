# Docker 构建指南

- author: [@dzzhyk](https://github.com/dzzhyk)
- update: 2021-12-01 18:32:25

**首先请确保服务器已经安装docker环境，且服务器架构Linux/amd64**


## 使用已有镜像部署

这里提供一个打包好的镜像，执行如下指令开服：

注意：会拉取最新构建的版本（目前1.3.1），**后续版本请自己动手本地构建镜像**

```shell script
$ docker pull dzzhyk/dst-admin:latest
$ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 dzzhyk/dst-admin:latest
```

需要服务器开放8080、10888、10998-10999端口

开服完成后，访问8080端口进入后台管理界面即可。


## 本地构建镜像

打开终端terminal，cd进入file目录，执行build_image.sh脚本

```shell script
$ cd file
$ ./build_image.sh <Docker用户名> <dst-admin版本>
# 例如: ./build_image.sh xiaoming 1.3.1
```

构建完成后，查看构建好的image:

```shell script
$ docker images
$ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 <镜像名称>
```

构建速度依据构建机网速决定，如遇网络错误请尝试科学上网。
