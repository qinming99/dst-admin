# Docker Support

- author: [@dzzhyk](https://github.com/dzzhyk)
- update: 2023-01-27 11:44:11

**Make sure install Docker env FIRST with arch type: amd64 (Win|MacOS|Linux)**

[browse in dockerhub.com](https://hub.docker.com/r/dzzhyk/dst-admin)

## 1. Quick Start

1. Automatically install `steamcmd`, `Don't Starve server` and start `dst-admin` when the container starts for the first
   time
2. Ports 8080, 10888, and 1098-10999 must be enabled on the server. After the server is started, access port 8080 to go
   to the background management page
3. The container ENTRYPOINT is `dst_admin_docker.sh`. You can modify it after entering the container

   ```shell
   $ docker pull dzzhyk/dst-admin:latest
   $ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 dzzhyk/dst-admin:latest
   ```

4. Check `dst-admin` log

   ```shell
   $ docker logs dst-admin
   ```

5. Restart container to check for updates for `steamcmd` and `Don't Starve server`

   ```shell
   $ docker restart dst-admin
   ```

## 2. Build images

1. Open terminal and change dictionary into `dst-admin/docker`，exec build_image.sh

   ```shell
   $ cd docker
   $ ./build_image.sh <Docker username> <dst-admin version>
   # For example: ./build_image.sh wolfgang 1.5.0
   ```

2. Check your own image:

   ```shell
   $ docker images
   $ docker run --name dst-admin -d -p8080:8080 -p10888:10888 -p10998-10999:10998-10999 wolfgang/dst-admin:v1.5.0
   ```
