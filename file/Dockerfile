FROM ubuntu:16.04

RUN sed -i "s@http://.*archive.ubuntu.com@http://repo.huaweicloud.com@g" /etc/apt/sources.list \
    && sed -i "s@http://.*security.ubuntu.com@http://repo.huaweicloud.com@g" /etc/apt/sources.list

VOLUME /root

ADD dst-admin.jar dst-admin.jar
ADD install.sh install.sh

ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai

RUN set -x \
    && dpkg --add-architecture i386 \
    && apt-get update \
    && apt-get install -y --no-install-recommends --no-install-suggests libstdc++6:i386 \
        libgcc1:i386 \
        lib32gcc1 \
        lib32stdc++6 \
        libcurl4-gnutls-dev:i386 \
        wget \
        ca-certificates \
        git \
        vim \
        openjdk-8-jdk \
        screen

EXPOSE 8080/tcp
EXPOSE 10888/udp
EXPOSE 10998/udp
EXPOSE 10999/udp

RUN chmod 755 install.sh \
    && ./install.sh

CMD ["nohup","java", "-jar", "-Xms256m", "-Xmx256m", "dst-admin.jar", "&"]
