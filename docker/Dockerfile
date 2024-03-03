FROM debian:bullseye-slim

ENV LANG C.UTF-8
RUN rm -f /etc/localtime \
    && ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

RUN echo "" > /etc/apt/sources.list
RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ bullseye main contrib non-free" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ bullseye-updates main contrib non-free" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ bullseye-backports main contrib non-free" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian-security bullseye-security main contrib non-free" >> /etc/apt/sources.list

VOLUME /root

ADD dst-admin.jar dst-admin.jar
ADD dst_admin_docker.sh dst_admin_docker.sh
RUN chmod 755 dst_admin_docker.sh

RUN dpkg --add-architecture i386 \
    && apt-get update \
    && apt-get install -y --no-install-recommends --no-install-suggests  \
        libstdc++6:i386 \
        libgcc1:i386 \
        lib32gcc-s1 \
        lib32stdc++6 \
        libcurl4-gnutls-dev:i386 \
        wget \
        ca-certificates \
        screen \
        sudo \
        ca-certificates-java \
        java-common \
        libcups2 \
        liblcms2-2 \
        libjpeg62-turbo \
        libfontconfig1 \
        libnss3 \
        libfreetype6 \
        libpcsclite1 \
        libx11-6 \
        libxext6 \
        libxi6 \
        libxrender1 \
        libxtst6 \
        procps \
        inetutils-ping


# amd64 openjdk-8-jre-headless
RUN wget http://snapshot.debian.org/archive/debian-security/20220210T090326Z/pool/updates/main/o/openjdk-8/openjdk-8-jre-headless_8u312-b07-1~deb9u1_amd64.deb
RUN dpkg -i openjdk-8-jre-headless_8u312-b07-1~deb9u1_amd64.deb
RUN apt-get clean
RUN rm -rf openjdk-8-jre-headless_8u312-b07-1~deb9u1_amd64.deb

EXPOSE 8080/tcp
EXPOSE 10888/udp
EXPOSE 10998/udp
EXPOSE 10999/udp


ENTRYPOINT ["./dst_admin_docker.sh"]
