#!/bin/bash

dst_dir=(${master} ${cave})
dst_name=("Master" "Caves")
dst_zh=("地上" "洞穴")
dst_sh=("overworld" "cave")

# 查看状态
status(){
	if [[ -n `ps -ef | grep -v grep |grep -v "dst.sh"|grep ${dst_name[$1]}|sed -n '1P'|awk '{print $2}'` ]]; then
		echo -e "\033[36m ##: ${dst_zh[$1]}正常运行中~ \033[0m"
    else
        echo -e "\033[31m ${dst_zh[$1]}状态:关闭 \033[0m"
	fi
}


#　启动
start(){
    cd ~/dst/bin
    # dst=${dst_dir[$1]}
    if [[ -z `ps -ef | grep -v grep |grep -v "dst.sh"|grep ${dst_name[$1]}|sed -n '1P'|awk '{print $2}'` ]]; then
		screen -dm sh ${dst_sh[$1]}.sh && if [[ `echo $?` -eq 0 ]];
		then
			echo -e "\033[36m ##: ${dst_zh[$1]}启动成功~ \033[0m"
		fi
	else
		echo -e "\033[31m !!!${dst_zh[$1]}正在运行中!!! \033[0m"
	fi
}

# 停止
stop(){
	ps -ef|grep ${dst_name[$1]}|awk '{print $2}'|xargs kill -9
	if [[ -z `ps -ef | grep -v grep |grep -v "dst.sh"|grep ${dst_name[$1]}|sed -n '1P'|awk '{print $2}'` ]]; then
		echo  -e "\033[32m ##: ${dst_zh[$1]}已停止... \033[0m"
	fi
}


# 重启
restart(){
	stop $1
	start $1
}

# 更新游戏版本
updst(){
  #重启游戏
  restart 0
	restart 1
}

main(){
  #更新游戏并重启
  updst
}

main $1

