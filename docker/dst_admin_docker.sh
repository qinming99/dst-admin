#!/bin/bash

CONTAINER_ALREADY_STARTED="DST_ADMIN_ALREADY_STARTED_PLACEHOLDER"
if [ ! -e $CONTAINER_ALREADY_STARTED ]; then
    touch $CONTAINER_ALREADY_STARTED
    echo "---------------------------------------------"
    echo "-- 首次启动dst-admin 准备安装饥荒服务端 请等待 --"
    echo "---------------------------------------------"
    wget https://steamcdn-a.akamaihd.net/client/installer/steamcmd_linux.tar.gz -P ~/steamcmd
    tar -zxvf ~/steamcmd/steamcmd_linux.tar.gz -C ~/steamcmd
    bash ~/steamcmd/steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit
    cp ~/steamcmd/linux32/libstdc++.so.6 ~/dst/bin/lib32/
    mkdir -p ~/.klei/DoNotStarveTogether/MyDediServer
fi

echo "-------------------"
echo "-- 启动dst-admin --"
echo "-------------------"
java -jar -Xms256m -Xmx256m ./dst-admin.jar
