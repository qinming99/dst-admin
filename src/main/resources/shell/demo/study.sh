#!/usr/bin/env bash


ps -ef | grep -v grep |grep 'Caves'|sed -n '1P'|awk '{print $2}'

ps -ef | grep -v grep |grep 'Master'|sed -n '1P'|awk '{print $2}'

ps -ef | grep -v grep |grep 'shell'|sed -n '1P'|awk '{print $2}'

ps -ef | grep -v grep |grep -v "dst.sh"|grep 'Master'|sed -n '1P'|awk '{print $2}'

ps -ef | grep -v grep |grep -v "dst.sh"|grep 'Caves'|sed -n '1P'|awk '{print $2}'