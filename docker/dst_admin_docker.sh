#!/bin/bash

USER_DIR=$(cd ~ && pwd -P)

function docker_log() {
    echo "[dst_admin_docker.sh][$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

docker_log "------------------------------------------------------------------"
docker_log "-- 本次启动时间: $(date +'%Y-%m-%d %H:%M:%S')"
docker_log "------------------------------------------------------------------"
docker_log "-- 欢迎使用 dst-admin docker镜像喵~"
docker_log "-- 镜像用法、更新日志及常见问题: https://hub.docker.com/r/dzzhyk/dst-admin"
docker_log "-- 镜像作者: dzzhyk@qq.com"
docker_log "-- 更新时间: 2024-03-03 14:14:30"
docker_log "------------------------------------------------------------------"
docker_log "-- STEP 1 检查*必要*命令安装情况喵!"
docker_log "------------------------------------------------------------------"
docker_log "当前用户: [$(whoami)]:${USER_DIR}"
cmds=(wget java screen sudo tar ps ping)
for i in ${cmds[*]}; do
    if command -v $i >/dev/null 2>&1; then
        docker_log "required command exists: ${i}"
    else
        docker_log "required command NOT exists: ${i}, failed"
        exit -2
    fi
done
docker_log "------------------------------------------------------------------"
docker_log "-- STEP 1 完成"
docker_log "------------------------------------------------------------------"

docker_log "------------------------------------------------------------------"
docker_log "-- STEP 2 检查steamcmd安装情况并更新喵~"
docker_log "------------------------------------------------------------------"
retry=1
while [ ! -d "${USER_DIR}/steamcmd" ] || [ ! -e "${USER_DIR}/steamcmd/steamcmd.sh" ]; do
    if [ $retry -gt 3 ]; then
        docker_log "重试3次下载steamcmd失败，请检查网络连接，然后重启容器(docker restart)以触发下载喵~"
        exit -2
    fi
    docker_log "喵~未找到可用的steamcmd, 第${retry}次尝试安装steamcmd喵~"
    wget http://media.steampowered.com/installer/steamcmd_linux.tar.gz -P $USER_DIR/steamcmd
    tar -zxvf $USER_DIR/steamcmd/steamcmd_linux.tar.gz -C $USER_DIR/steamcmd
    sleep 3
    ((retry++))
done
docker_log "------------------------------------------------------------------"
docker_log "-- steamcmd找到喵!尝试更新steamcmd喵~"
docker_log "-- 更新速度依据宿主机网速决定喵~"
docker_log "------------------------------------------------------------------"
bash $USER_DIR/steamcmd/steamcmd.sh +quit
docker_log "------------------------------------------------------------------"
docker_log "-- STEP 2 完成"
docker_log "------------------------------------------------------------------"

docker_log "------------------------------------------------------------------"
docker_log "-- STEP 3 检查饥荒服务端安装情况并更新喵~"
docker_log "-- 安装速度依据宿主机网速决定，约需要5~10分钟"
docker_log "------------------------------------------------------------------"
retry=1
while [ ! -d "${USER_DIR}/dst" ] || [ ! -e "${USER_DIR}/dst/bin/dontstarve_dedicated_server_nullrenderer" ]; do
    if [ $retry -gt 3 ]; then
        docker_log "重试3次下载饥荒服务端失败，请检查网络连接，然后重启容器(docker restart)以触发下载"
        exit -2
    fi
    docker_log "未找到可用饥荒服务端, 第${retry}次尝试安装饥荒服务端喵~"
    bash $USER_DIR/steamcmd/steamcmd.sh +force_install_dir $USER_DIR/dst +login anonymous +app_update 343050 validate +quit
    ln -s /usr/lib32/libstdc++.so.6 $USER_DIR/dst/bin/lib32/libstdc++.so.6
    mkdir -p $USER_DIR/.klei/DoNotStarveTogether/MyDediServer
    sleep 3
    ((retry++))
done
docker_log "------------------------------------------------------------------"
docker_log "-- 尝试更新饥荒服务端喵~"
docker_log "-- 更新速度依据宿主机网速决定喵~"
docker_log "------------------------------------------------------------------"
bash $USER_DIR/steamcmd/steamcmd.sh +force_install_dir $USER_DIR/dst +login anonymous +app_update 343050 validate +quit
docker_log "------------------------------------------------------------------"
docker_log "-- STEP 3 完成"
docker_log "------------------------------------------------------------------"

docker_log "------------------------------------------------------------------"
docker_log "-- STEP 4 启动dst-admin管理端喵~"
docker_log "-- meow~meow~ 发现*任何*使用docker版本dst-admin问题，可以通过邮件反馈容器日志: dzzhyk@qq.com"
docker_log "-- 导出日志: $ docker logs dst-admin(容器名) > dst-admin-docker.log"
docker_log "------------------------------------------------------------------"
java -jar -Xms256m -Xmx256m ./dst-admin.jar
