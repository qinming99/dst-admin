# Docker 构建指南

- author: [@dzzhyk](https://github.com/dzzhyk)
- update: 2023-01-27 11:44:11

**首先请确保服务器已经安装docker环境，且服务器架构amd64 (操作系统: Win|MacOS|Linux均可)**

[镜像帮助及常见问题](https://hub.docker.com/r/dzzhyk/dst-admin)

## 1. 使用已有镜像

1. 容器首次启动时自动安装`steamcmd`、`饥荒服务端`并启动`dst-admin`
2. 需要服务器开放8080、10888、10998-10999端口；开服完成后，访问8080端口进入后台管理界面
3. 容器入口脚本为`dst_admin_docker.sh`，可以进入容器后自行修改

- 国内用户推荐：
    ```shell
    $ docker pull registry.cn-hangzhou.aliyuncs.com/dzzhyk/dst-admin:latest
    $ docker run --name dst-admin -d -p8080:8080 -p10888:10888/udp -p10998-10999:10998-10999/udp registry.cn-hangzhou.aliyuncs.com/dzzhyk/dst-admin:latest
    ```
- 官方源：
    ```shell
    $ docker pull dzzhyk/dst-admin:latest
    $ docker run --name dst-admin -d -p8080:8080 -p10888:10888/udp -p10998-10999:10998-10999/udp dzzhyk/dst-admin:latest
    ```

4. 容器启动日志、dst-admin日志

    ```shell
    $ docker logs dst-admin
    ```

5. 如果你使用了某些Docker镜像源，那么可能latest拉取到的非最新版本，推荐你用docker官方国内源 https://registry.docker-cn.com

6. 容器启动时会自动检查steamcmd和饥荒服务端更新，手动触发方式：

    ```shell
    $ docker restart dst-admin
    ```

## 2. 手动构建镜像

1. 打开终端terminal，cd进入docker目录，执行build_image.sh脚本

   ```shell
   $ cd docker
   $ ./build_image.sh <Docker用户名> <dst-admin版本>
   # 例如: ./build_image.sh wolfgang 1.5.0
   ```

2. 构建完成后，查看并启动构建好的image:

   ```shell
   $ docker images
   $ docker run --name dst-admin -d -p8080:8080 -p10888:10888/udp -p10998-10999:10998-10999/udp wolfgang/dst-admin:v1.5.0
   ```

   构建镜像速度依据构建机网络决定，默认docker源国内并不稳定，如遇网络错误、构建慢请尝试镜像源、科学上网。