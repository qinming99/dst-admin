#!/bin/bash

project="dst-admin"    # 项目名称
sys=$(uname -s)         # 操作系统
machine=$(uname -m)     # 架构版本

darwin_steamcmd_link="https://steamcdn-a.akamaihd.net/client/installer/steamcmd_osx.tar.gz"
linux_steamcmd_link="https://steamcdn-a.akamaihd.net/client/installer/steamcmd_linux.tar.gz"

# 创建Linux服务器环境
create() {
    echo "正在安装steamcmd..."
    res=`./steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit`
    res=$res | grep "0x202"
    if [ -z "$res" ]; then
        echo "${project} - 0x202错误 请检查可用空间是否 > 24GB"
        echo "${project} - 删除可能的 ~/steamcmd ~/Steam ~/dst 目录后重试 ./install.sh"
        rm -rf ~/Steam
        rm -rf ~/dst
        rm -rf ~/steamcmd
        exit 1;
    fi
    cp ~/steamcmd/linux32/libstdc++.so.6 ~/dst/bin/lib32/
    cd ~/dst/bin
    echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Master > overworld.sh
    echo ./dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Caves > cave.sh
    chmod +x overworld.sh
    chmod +x cave.sh
    mkdir -p ~/.klei/DoNotStarveTogether/MyDediServer
    cd ~
    echo -e "${project} - 初始化完成\n${project} - 执行dstStart.sh脚本按照指示进行\n${project} - ./dstStart.sh"
    exit 1
}

# 配置环境
main() {
    # mac
    if [ "$sys" == "Darwin" ]; then
        echo "${project} - 操作系统：MacOS"
        mkdir ~/steamcmd && cd ~/steamcmd
        wget ${darwin_steamcmd_link}
        tar -zxvf steamcmd_osx.tar.gz
        ./steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit
        cd ~/dst
        mkdir ~/dst/bin
        echo ~/dst/dontstarve_dedicated_server_nullrenderer.app/Contents/MacOS/dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Master > ~/dst/bin/overworld.sh
        echo ~/dst/dontstarve_dedicated_server_nullrenderer.app/Contents/MacOS/dontstarve_dedicated_server_nullrenderer -console -cluster MyDediServer -shard Caves > ~/dst/bin/cave.sh
        chmod +x ~/dst/bin/overworld.sh
        chmod +x ~/dst/bin/cave.sh
        mkdir -p ~/.klei/DoNotStarveTogether/MyDediServer
        cd ~
        echo -e "${project} - 初始化完成\n${project} - 执行dstStart.sh脚本按照指示进行\n${project} - ./dstStart.sh"
        exit 1
    fi

    # linux
    if [ "$sys" == "Linux" ]; then
        distribution=$(lsb_release -i | awk '{print $3}')   # 发行版本
        echo "${project} - 操作系统：Linux"
        echo "${project} - ${distribution} ${machine}"
        if [ "$machine" == "x86_64" ]; then
            case $distribution in
                CentOS)
                    echo "${project} - 安装CentOS依赖环境"
                    mkdir ~/steamcmd && cd ~/steamcmd
                    wget ${linux_steamcmd_link}
                    tar -xvzf steamcmd_linux.tar.gz
                    sudo yum install -y glibc.i686 libstdc++.i686 ncurses-libs.i686 screen libcurl.i686
                    sudo yum install -y SDL2.x86_64 SDL2_gfx-devel.x86_64 SDL2_image-devel.x86_64 SDL2_ttf-devel.x86_64
                    # CentOS需要建立libcurl-gnutls.so.4软连接
                    ln -s /usr/lib/libcurl.so.4 /usr/lib/libcurl-gnutls.so.4
                    create
                ;;
                Ubuntu)
                    echo "${project} - 安装Ubuntu依赖环境"
                    mkdir ~/steamcmd && cd ~/steamcmd
                    wget ${linux_steamcmd_link}
                    tar -xvzf steamcmd_linux.tar.gz
                    sudo apt-get update
                    sudo apt-get install -y lib32gcc1 libcurl4-gnutls-dev:i386 libsdl2-2.0 libsdl2-dev screen
                    sudo apt-get install -y libsdl-image1.2-dev libsdl-mixer1.2-dev libsdl-ttf2.0-dev libsdl-gfx1.2-dev
                    create
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
