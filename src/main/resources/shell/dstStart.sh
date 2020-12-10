#!/usr/bin/env bash
#jar包名称
server_name="dst-admin"

status() {
  if [[ -n $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
    echo -e "\033[36m ##: 正常运行中~ \033[0m"
  else
    echo -e "\033[31m 已经停止 \033[0m"
  fi
}

start() {
  if [[ -z $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
    #    停止了
    nohup java -jar -Xms150m -Xmx150m ${server_name}'.jar' --server.port=8080  >>server.log &
    if [[ -n $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
      echo -e "\033[36m ##: 启动成功~ \033[0m"
    else
      echo -e "\033[31m 启动失败 \033[0m"
    fi
  else
    echo -e "\033[32m ##: 服务已经在运行中... \033[0m"
  fi

}

stop() {
  ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}' | xargs kill -9
  if [[ -z $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
    echo -e "\033[32m ##: 已停止... \033[0m"
  fi
}

main() {

  if [ ! -f ${server_name}".jar" ]; then
    echo -e "\033[31m 当前目录不存在  ${server_name}.jar  文件 \033[0m"
  else
#    存在文件
    echo -e "\033[42;30m ###[ 控制台 ]### \033[0m"
    echo -e "\033[32m 0. \033[0m 启动服务"
    echo -e "\033[32m 1. \033[0m 停止服务"
    echo -e "\033[32m 2. \033[0m 查看服务状态"
    echo -e "\033[32m 3. \033[0m 重启服务"

    read -p "请输入数字0-2的选项,回车确认: " choose
    case $choose in
    0)
      start
      ;;
    1)
      stop
      ;;
    2)
      status
      ;;
    3)
      stop
      start
      ;;
    *)
      echo -e "\033[31m 请输入合法的数字 \033[0m"
      ;;
    esac
  fi

}

main
