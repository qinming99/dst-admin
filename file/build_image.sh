#!/bin/bash

############################
# build_image.sh
# 本地构建docker镜像
# @author: dzzhyk
# @since: 2021-12-01 18:46:01
############################

if [[ -z $1 || -z $2 ]]; then
  echo "用法: ./build_image.sh <Docker用户名> <dst-admin版本>"
  exit 1
fi

USERNAME=$1
CLIENT_VERSION=$2

wget http://clouddn.tugos.cn/release/dst-admin-${CLIENT_VERSION}.jar -O ./dst-admin.jar

docker build -t ${USERNAME}/dst-admin:v${CLIENT_VERSION} .