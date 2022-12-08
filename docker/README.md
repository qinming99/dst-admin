# Docker 构建指南

- author: [@dzzhyk](https://github.com/dzzhyk)
- update: 2022-12-05 20:16:42

**首先请确保服务器已经安装docker环境，且服务器架构amd64 (操作系统: Win|MacOS|Linux均可)**

## 1. 使用已有镜像

1. 容器首次启动时自动安装steamcmd、饥荒服务端并启动dst-admin
2. 需要服务器开放8080、10888、10998-10999端口；开服完成后，访问8080端口进入后台管理界面
3. 容器入口脚本为`dst_admin_docker.sh`，可以进入容器后自行修改

```shell script
$ docker pull dzzhyk/dst-admin:latest
$ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 dzzhyk/dst-admin:latest
```

4. 容器启动日志、dst-admin日志

```shell script
$ docker logs dst-admin
```

5. 注意：如果你使用了某些Docker镜像源，那么可能latest拉取到的非最新版本，此时请手动尝试拉取新版：

```shell script
$ docker pull dzzhyk/dst-admin:v最新版本号
```

## 2. 手动构建镜像

这里以1.5.0为例

1. 打开终端terminal，cd进入docker目录，执行build_image.sh脚本

```shell script
$ cd docker
$ ./build_image.sh <Docker用户名> <dst-admin版本>
# 例如: ./build_image.sh wolfgang 1.5.0
```

2. 构建完成后，查看并启动构建好的image:

```shell script
$ docker images
$ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 wolfgang/dst-admin:v1.5.0
```

构建镜像速度依据构建机网络决定，默认docker源国内并不稳定，如遇网络错误、构建慢请尝试镜像源、科学上网。