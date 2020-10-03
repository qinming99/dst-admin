#!/usr/bin/env bash

sudo apt-get update
sudo apt-get install -y lib32gcc1
sudo apt-get install -y libcurl4-gnutls-dev:i386
sudo apt-get install -y screen

mkdir ~/steamcmd
cd ~/steamcmd

wget https://steamcdn-a.akamaihd.net/client/installer/steamcmd_linux.tar.gz
tar -xvzf steamcmd_linux.tar.gz
./steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit

cp ~/steamcmd/linux32/libstdc++.so.6 ~/dst/bin/lib32/

cd ~/dst/bin
echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Master > overworld.sh
echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Caves > cave.sh

chmod +x overworld.sh
chmod +x cave.sh

mkdir -p ~/.klei/DoNotStarveTogether/MyDediServer

cd ~



