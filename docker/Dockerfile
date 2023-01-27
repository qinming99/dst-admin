FROM debian:stretch-slim

ENV LANG C.UTF-8
RUN rm -f /etc/localtime \
    && ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

RUN echo "" > /etc/apt/sources.list
RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ stretch main contrib non-free" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ stretch-updates main contrib non-free" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ stretch-backports main contrib non-free" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian-security stretch/updates main contrib non-free" >> /etc/apt/sources.list

VOLUME /root

ADD dst-admin.jar dst-admin.jar
ADD dst_admin_docker.sh dst_admin_docker.sh
RUN chmod 755 dst_admin_docker.sh

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
        screen \
        sudo \
    && apt-get clean

EXPOSE 8080/tcp
EXPOSE 10888/udp
EXPOSE 10998/udp
EXPOSE 10999/udp


ENTRYPOINT ["./dst_admin_docker.sh"]
