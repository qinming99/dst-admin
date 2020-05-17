
#!/bin/bash

master="$HOME/.klei/DoNotStarveTogether/MyDediServer/Master/"
cave="$HOME/.klei/DoNotStarveTogether/MyDediServer/Caves/"

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
# 	echo ""
	start $1
}

# 重置
reset(){
	del $1
# 	echo ""
	start $1
}

#删除存档
del(){
	stop $1

	dir=${dst_dir[$1]}

	if test -d ${dir}"save"
	then
		# rm -r ${dir}"save"&&rm -r `find ${dir} -name "*.txt"` && rm -r ${dir}"backup"
		rm -r ${dir}"save"
		echo -e "\033[32m ##: ${dst_zh[$1]}文件删除完毕~ \033[0m"
	fi
}

# 更新游戏版本
updst(){
	if [ $1 ];then
		stop $1
	else
		stop 0
		stop 1
	fi

	screen -dm ~/steamcmd/steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit
	if [[ `echo $?` -eq 0 ]]; then
		echo -e "\033[46;37m ##: 饥荒游戏版本更新成功~ \033[0m"
	fi
}

action(){
    stop 0
    start 0
}


main(){
	action
}

main $1




