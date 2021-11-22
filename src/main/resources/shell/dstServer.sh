#!/bin/bash

server_name="dst-admin"

installJdk() {
  #更新软件源
  sudo apt-get update
  #安装jdk8
  sudo apt-get install -y openjdk-8-jdk
}

settingSwap() {
  sudo dd if=/dev/zero of=/swapfile count=4096 bs=1M
  sudo chmod 600 /swapfile
  sudo mkswap /swapfile
  sudo swapon /swapfile
}

installAdmin() {
  if [[ ! -f ${server_name}'.jar' ]]; then
    wget http://clouddn.tugos.cn/release/dst-admin-1.3.1.jar -O dst-admin.jar
  else
    echo -e "dst-admin.jar 已下载"
  fi
}

start() {
  if [[ -z $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
    #    停止了
    nohup java -jar -Xms100m -Xmx100m ${server_name}'.jar' --server.port=8080 >>server.log &
    sleep 2
    if [[ -n $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
      echo -e "\033[36m ##: 启动成功(Start successfully) ~ \033[0m"
    else
      echo -e "\033[31m 启动失败(failed to activate) \033[0m"
    fi
  else
    echo -e "\033[32m ##: 服务已经在运行中(The service is already running) \033[0m"
  fi

}

stop() {
  ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}' | xargs kill -9
  if [[ -z $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
    echo -e "\033[32m ##: 已停止(stopped) ... \033[0m"
  fi
}

installDst() {
  #准备安装饥荒
  sudo apt-get install -y lib32gcc1
  sudo apt-get install -y libcurl4-gnutls-dev:i386
  sudo apt-get install -y screen

  mkdir ~/steamcmd
  cd ~/steamcmd
  if [[ ! -f 'steamcmd_linux.tar.gz' ]]; then
    wget https://steamcdn-a.akamaihd.net/client/installer/steamcmd_linux.tar.gz
  else
    echo -e "steamcmd_linux.tar.gz 已下载"
  fi
  tar -xvzf steamcmd_linux.tar.gz
  ./steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit
  cp ~/steamcmd/linux32/libstdc++.so.6 ~/dst/bin/lib32/
  mkdir -p ~/.klei/DoNotStarveTogether/MyDediServer
  cd ~
}

main() {
  installJdk
  echo -e "jdk安装完成,接下来设置虚拟内存，请勿中断"
  settingSwap
  echo -e "虚拟内存设置完成4GB，接下来下载饥荒管理后台，请勿中断"
  installAdmin
  echo -e "饥荒管理后台下载成功"
  stop
  start
  echo -e "开始安装steam饥荒，比较耗时，请勿中断，如果失败，请重试！！"
  installDst
  echo -e "饥荒服务器安装完成"
}

main


