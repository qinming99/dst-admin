# Docker 构建指南

- author: [@dzzhyk](https://github.com/dzzhyk)
- update: 2022-10-30 16:34:32

**首先请确保服务器已经安装docker环境，且服务器架构Linux/amd64**

## 1. 使用快速启动镜像

1. 容器首次启动时自动安装steamcmd、饥荒服务端、dst-admin，镜像大小只有200M，下载到本地很快
2. 容器入口脚本为dst_admin_docker.sh，可以进入容器后自行修改
3. 允许docker-compose、kubernetes等批量操作

```shell script
$ docker pull dzzhyk/dst-admin:latest
$ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 dzzhyk/dst-admin:latest
```

4容器启动日志、dst-admin日志

```shell script
$ docker logs dst-admin
```

## 2. 本地构建快速启动镜像

这里以1.4.0为例

1. 打开终端terminal，cd进入docker目录，执行build_image.sh脚本

```shell script
$ cd docker
$ ./build_image.sh <Docker用户名> <dst-admin版本>
# 例如: ./build_image.sh wolfgang 1.4.0
```

2. 构建完成后，查看并启动构建好的image:

```shell script
$ docker images
$ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 <镜像名>:<版本号(v)开头>
```

构建镜像速度依据构建机网络决定，默认docker源国内并不稳定，如遇网络错误、构建慢请尝试镜像源、科学上网。

## 3. 使用完全镜像\(不推荐\)

> 为什么之后不推荐完全安装镜像？
> 1. 游戏一直在更新，完全安装镜像不保证最新的饥荒服务端版本，开服后需要借助管理端更新
> 2. 单个完全安装镜像约3G，镜像大小太大，其实服务端的安装和配置完全可以放到本地去做
> 3. 鉴于现在还有很多朋友在用且最新的可能存在不稳定的情况，1.3.1版本的目前仍然可用

1. 这里提供一个完全安装好的镜像，执行如下指令开服：

   注意：**后续不会再提供**

```shell script
$ docker pull dzzhyk/dst-admin:v1.3.1
$ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 dzzhyk/dst-admin:v1.3.1
```

2. 需要服务器开放8080、10888、10998-10999端口

开服完成后，访问8080端口进入后台管理界面即可。
