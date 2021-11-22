# dst-admin:Steam Don't Starve Together Management System
> dst-admin is the Don't Starve Together Management System, 
it is a web program written in Java language, which is powerful, 
convenient and simple to use, and reduces the difficulty of server construction


##  See the [中文文档](/README-zh.md) for Chinese readme.

## The following are features:
1.  Start or close ground/cave at one-click
2.  Server system resources monitor
3.  Don't Starve Together ground/cave/mod settings
4.  Game Archive management,recover,auto backup
5.  Support auto update game
6.  Support Settings extra admin or players' blacklist
7.  Support viewing game logs
8.  Support uploading local archives
9.  Long-range control,kick player,rollback,reset world

## Environment requirement
1.  Operating system  **Ubuntu** （ 16.04 , 18.04 has been tested，other Ubuntu versions are not ）
2.  Java **JDK 1.8**

## Matters need attention
1.  Default server port 8080
2.  Default user name/password admin/123456
3.  Don't Starve Together monitoring port**10888，10999，10998**（It is suggested to open all port to avoid some matters.）

## Fast beginning
###  Install jdk1.8 on Ubuntu server
```
#Update software :
sudo apt-get update
#Install openJDK1.8
sudo apt-get install -y openjdk-8-jdk
#Check version
java -version
if  showing "openjdk version "1.8.0_252"well, finish 
```      
###  Download the newest dst-admin 

```bash
wget http://clouddn.tugos.cn/release/dst-admin-1.3.1.jar -O dst-admin.jar
```


###  Start dst-admin
```
#Start 
java -jar dst-admin.jar 
```
###  Start Don't Starve Together installation script，install Don't Starve Together client
```
#It will release install.sh after starting to install steam and Don't Starve Together client 
#，executes program may requires ur password（probably becuase Internet problem lead to executing the scripts for several times）
./install.sh
```
###  Use dstStart.sh to control dst-admin 
```
#Execute dstStart.sh as the tips
./dstStart.sh
```

## Preview 

![img](https://github.com/qinming99/dst-admin/blob/master/images/image1.png)
![img](https://github.com/qinming99/dst-admin/blob/master/images/image2.png)
![img](https://github.com/qinming99/dst-admin/blob/master/images/yanshi.gif)


## Thanks~

- <a href="https://www.jetbrains.com/?from=dst-admin"><img src="https://github.com/qinming99/dst-admin/blob/master/images/jet-logo.jpg" width="100px" alt="jetbrains">**Thanks for JetBrains affording free License**</a>

