#!/usr/bin/env bash
#jar包名称
server_name="dst-admin"

status() {
  if [[ -n $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
    echo -e "\033[36m ##: 正常运行中(Running)~ \033[0m"
  else
    echo -e "\033[31m 已经停止(Has stopped) \033[0m"
  fi
}

start() {
  if [[ -z $(ps -ef | grep -v grep | grep ${server_name} | sed -n '1P' | awk '{print $2}') ]]; then
    #    停止了
    nohup java -jar -Xms100m -Xmx100m ${server_name}'.jar' --server.port=8080  >>server.log 2>&1 &
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

main() {

  if [ ! -f ${server_name}".jar" ]; then
    echo -e "\033[31m 当前目录不存在  ${server_name}.jar  文件 （The current directory does not exist  ${server_name}.jar） \033[0m"
  else
#    存在文件
    echo -e "\033[42;30m ###[ 控制台(Console) ]### \033[0m"
    echo -e "\033[32m 0. \033[0m 启动服务(Start service)"
    echo -e "\033[32m 1. \033[0m 停止服务(Stop service)"
    echo -e "\033[32m 2. \033[0m 查看服务状态(Check service status)"
    echo -e "\033[32m 3. \033[0m 重启服务(Restart service)"

    read -p "请输入数字0-3的选项,回车确认(Please enter the number 0-3 option, press Enter to confirm): " choose
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
      echo -e "\033[31m 请输入合法的数字(lease enter a legal number) \033[0m"
      ;;
    esac
  fi

}

main
