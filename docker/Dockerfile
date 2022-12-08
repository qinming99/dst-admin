FROM ubuntu:16.04

RUN sed -i "s@http://.*archive.ubuntu.com@http://repo.huaweicloud.com@g" /etc/apt/sources.list \
    && sed -i "s@http://.*security.ubuntu.com@http://repo.huaweicloud.com@g" /etc/apt/sources.list

VOLUME /root

ADD dst-admin.jar dst-admin.jar
ADD dst_admin_docker.sh dst_admin_docker.sh
RUN chmod 755 dst_admin_docker.sh

ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai

RUN dpkg --add-architecture i386 \
    && apt-get update \
    && apt-get install -y --no-install-recommends --no-install-suggests  \
        libstdc++6:i386 \
        libgcc1:i386 \
        lib32gcc1 \
        lib32stdc++6 \
        libcurl4-gnutls-dev:i386 \
        wget \
        ca-certificates \
        openjdk-8-jre \
        screen

EXPOSE 8080/tcp
EXPOSE 10888/udp
EXPOSE 10998/udp
EXPOSE 10999/udp


ENTRYPOINT ["./dst_admin_docker.sh"]
