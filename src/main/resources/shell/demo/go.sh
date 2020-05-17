#!/usr/bin/env bash

udo apt-get update
sudo apt-get install lib32gcc1 libcurl4-gnutls-dev:i386 screen

mkdir ~/steamcmd
cd ~/steamcmd

wget https://steamcdn-a.akamaihd.net/client/installer/steamcmd_linux.tar.gz
tar -xvzf steamcmd_linux.tar.gz
./steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit

cp ~/steamcmd/linux32/libstdc++.so.6 ~/dst/bin/lib32/

cd ~/dst/bin
echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Master > overworld.sh
echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Caves > cave.sh

mkdir -p ~/.klei/DoNotStarveTogether/MyDediServer

cd ~

if [[ `echo $?` -eq 0 ]]; then
	echo -e "\033[42;30m ### 游戏服务器初始化完成,请放入配置文件并执行 ./dst \033[0m"
else
	echo -e "\033[31m 执行出现了错误，可能因为网络不好，请尝试重新执行一次 \033[0m"	
fi


