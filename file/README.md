# Docker

- author: [@dzzhyk](https://github.com/dzzhyk)
- since: 2020-12-08 15:20:26

**首先请确保服务器已经安装docker环境，且服务器架构Linux/amd64**

## 使用已有镜像部署

这里提供一个打包好的镜像，执行如下指令开服：

版本号可能需要更换

```shell script
$ docker pull dzzhyk/dst-admin:v1.0.5
$ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 dzzhyk/dst-admin:v1.0.5
```

需要服务器开放8080、10888、10998-10999端口

开服完成后，访问8080端口进入后台管理界面即可。

<hr>

## 创建本地Docker镜像

> Tips: 
> 其实还是推荐自己创建本地docker镜像的，因为现成的docker镜像大约1.7GB，虽然有国内镜像但是下载可能较慢
> 但是考虑到啊，自己构建的docker镜像还是需要上传到服务器，所以倒不如直接使用docker hub上的镜像了，所以此方法推荐给服务器在本地并且拥有内网穿透或者公网IP的小伙伴

下面是一个本地创建镜像示例：

首先准备构建镜像需要的两个文件：打包好的项目jar，项目初始化脚本install.sh

1. 将打包好的项目jar包复制到`file`目录下，例如`dst-admin-1.0.5.jar`，然后重命名为`dst-admin.jar`
2. 将项目的初始化脚本install.sh复制到`file`目录下，然后重命名为`install.sh`
3. 在file目录下执行如下指令，构建本地docker镜像，构建速度依据网速和机器性能决定，约5min
    
    ${your_name}为你的docker hub账户名

    ```shell script
    $ docker build -t ${your_name}/dst-admin:v1.0.5 .
    ```

4. 构建成功后，查看构建的docker镜像
    ```shell script
    $ docker images
    REPOSITORY                         TAG                 IMAGE ID            CREATED             SIZE
    dzzhyk/dst-admin                   v1.0.5              563c8a774366        2 weeks ago         3.05GB
    ```

5. 创建本地docker实例并运行
    ```shell script
    $ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 ${your_name}/dst-admin:v1.0.5
    ```


