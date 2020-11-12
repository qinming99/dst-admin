#!/bin/bash

project="dst-server"    # 项目名称
sys=$(uname -s)         # 操作系统
machine=$(uname -m)     # 架构版本

darwin_steamcmd_link="https://steamcdn-a.akamaihd.net/client/installer/steamcmd_osx.tar.gz"
linux_steamcmd_link="https://steamcdn-a.akamaihd.net/client/installer/steamcmd_linux.tar.gz"

# 创建服务器环境
create() {
    ./steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit
    cp ~/steamcmd/linux32/libstdc++.so.6 ~/dst/bin/lib32/
    cd ~/dst/bin
    echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Master > overworld.sh
    echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Caves > cave.sh
    chmod +x overworld.sh
    chmod +x cave.sh
    mkdir -p ~/.klei/DoNotStarveTogether/MyDediServer
    cd ~
    echo -e "${project} - 初始化完成\n${project} - 执行dstStart.sh脚本按照指示进行\n${project} - ./dstStart.sh"
    exit 0
}

# 配置环境
main() {
    # mac
    if (( "$sys" == "Darwin" )); then
        echo "${project} - 操作系统：MacOS"
        mkdir ~/steamcmd
        cd ~/steamcmd
        curl -sqL ${darwin_steamcmd_link} | tar zxvf -
        create
    fi

    # linux
    if (( "$sys" == "Linux" )); then
        distribution=$(lsb_release -i | awk '{print $3}')   # 发行版本
        echo "${project} - 操作系统：Linux"
        echo "${project} - ${distribution} ${machine}"
        if (( "$machine" == "x86_64" )); then
            case $distribution in
                CentOS)
                    echo "${project} - 安装CentOS依赖环境"
                    mkdir ~/steamcmd
                    cd ~/steamcmd
                    wget ${linux_steamcmd_link}
                    tar -xvzf steamcmd_linux.tar.gz
                    sudo yum install -y glibc.i686 libstdc++.i686
                    sudo yum install -y screen
                    create
                ;;
                Ubuntu)
                    echo "${project} - 安装Ubuntu依赖环境"
                    mkdir ~/steamcmd
                    cd ~/steamcmd
                    wget ${linux_steamcmd_link}
                    tar -xvzf steamcmd_linux.tar.gz
                    sudo apt-get update
                    sudo apt-get install -y lib32gcc1
                    sudo apt-get install -y libcurl4-gnutls-dev:i386
                    sudo apt-get install -y screen
                    create
                ;;
                ManjaroLinux)
                    echo "${project} - 安装Manjaro依赖环境，请根据提示安装"
                    pacman -Sy base-devel
                    git clone https://aur.archlinux.org/steamcmd.git
                    cd steamcmd
                    makepkg -darwin_steamcmd_link
                    # ./steamcmd
                    ./steamcmd +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit
                    cp ~/steamcmd/linux32/libstdc++.so.6 ~/dst/bin/lib32/
                    cd ~/dst/bin
                    echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Master > overworld.sh
                    echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Caves > cave.sh
                    chmod +x overworld.sh
                    chmod +x cave.sh
                    mkdir -p ~/.klei/DoNotStarveTogether/MyDediServer
                    cd ~
                ;;
                *)
                    echo "${project} - 暂不支持该Linux发行版 ${distribution}"
                ;;
            esac
        else
            echo "${project} - 不受支持的架构版本 ${machine}"
        fi
    fi
}

main