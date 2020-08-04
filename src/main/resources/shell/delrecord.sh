#!/bin/bash

master="$HOME/.klei/DoNotStarveTogether/MyDediServer/Master/"
cave="$HOME/.klei/DoNotStarveTogether/MyDediServer/Caves/"

dst_dir=(${master} ${cave})
dst_name=("Master" "Caves")
dst_zh=("地上" "洞穴")
dst_sh=("overworld" "cave")

# 停止
stop(){
	ps -ef|grep ${dst_name[$1]}|awk '{print $2}'|xargs kill -9
	if [[ -z `ps -ef | grep -v grep |grep -v "dst.sh"|grep ${dst_name[$1]}|sed -n '1P'|awk '{print $2}'` ]]; then
		echo  -e "\033[32m ##: ${dst_zh[$1]}已停止... \033[0m"
	fi
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


main(){
	del 0
	del 1
}

main $1




