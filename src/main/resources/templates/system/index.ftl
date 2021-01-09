<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>饥荒管理平台游戏日志查看</title>
    <#include "../common/header.ftl"/>
</head>
<style>
    .card {
        margin: 10px;
    }
</style>
<body>

<div id="sys_index">
    <el-tabs v-model="activeName">
        <el-tab-pane label="定时任务设置" name="first">
            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span>定时生成存档备份任务</span>
                </div>
                <ul>
                    <li v-for="item in scheduleVO.backupTimeList">执行时间：{{item.time}}, 执行状态：
                        <strong style="color: green" v-if="item.count > 0">
                            已经执行</strong><strong style="color: orange" v-if="item.count === 0">
                            未执行</strong>
                    </li>
                </ul>
                <el-row style="margin: 5px">
                    <el-col :span="5">
                        <el-button type="primary" @click="addBackupTime()">添加执行时间</el-button>
                    </el-col>
                </el-row>

                <tempate v-for="(item,key) in backupTimeList">
                    <el-row style="margin: 5px">
                        <el-col :span="5">
                            <el-time-picker placeholder="选择执行时间" v-model="item.time" clearable></el-time-picker>
                        </el-col>
                        <el-button type="warning" style="margin-left: 5px" @click="delBackupTime(key)">删除</el-button>
                    </el-row>
                </tempate>
            </el-card>

            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span>定时更新游戏任务</span>
                    <span style="color: red;margin-left: 40px">更新时不启动：</span>
                        <el-checkbox v-model="notStartMaster">地面</el-checkbox>
                        <el-checkbox v-model="notStartCaves">洞穴</el-checkbox>

                    <span style="color: red;margin-left: 40px">智能更新：</span>
                    <el-switch v-model="smartUpdate" active-text="开" inactive-text="关"></el-switch>
                </div>
                <ul>
                    <li v-for="item in scheduleVO.updateTimeList">执行时间：{{item.time}}, 执行状态：
                        <strong style="color: green" v-if="item.count > 0">
                            已经执行</strong><strong style="color: orange" v-if="item.count === 0">
                            未执行</strong>
                    </li>
                </ul>
                <el-row style="margin: 5px">
                    <el-col :span="5">
                        <el-button type="primary" @click="addUpdateTime()">添加执行时间</el-button>
                    </el-col>
                </el-row>

                <tempate v-for="(item,key) in updateTimeList">
                    <el-row style="margin: 5px">
                        <el-col :span="5">
                            <el-time-picker placeholder="选择执行时间" v-model="item.time" clearable></el-time-picker>
                        </el-col>
                        <el-button type="warning" style="margin-left: 5px" @click="delUpdateTime(key)">删除</el-button>
                    </el-row>
                </tempate>
            </el-card>

            <el-card style="margin: 10px; position: sticky; bottom: 0;  z-index: 10;">
                <el-button type="primary" @click="saveSchedule()">保存</el-button>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="地面程序运行日志" name="second">
            <el-card class="card">
                <el-row>
                    <el-col :span="5">
                        <el-input placeholder="请输入需要拉取的日志行数" v-model="num1" type="number" clearable></el-input>
                    </el-col>
                    <el-button type="primary" @click="getDstLog(0,num1)">拉取</el-button>
                </el-row>
            </el-card>
            <el-card class="card">
                <ul>
                    <li v-for="log in masterLog">{{ log }}</li>
                </ul>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="洞穴程序运行日志" name="third">
            <el-card class="card">
                <el-row>
                    <el-col :span="5">
                        <el-input placeholder="请输入需要拉取的日志行数" v-model="num2" type="number" clearable></el-input>
                    </el-col>
                    <el-button type="primary" @click="getDstLog(1,num2)">拉取</el-button>
                </el-row>
            </el-card>
            <el-card class="card">
                <ul>
                    <li v-for="log in cavesLog">{{ log }}</li>
                </ul>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="玩家聊天日志" name="fourth">
            <el-card class="card">
                <el-row>
                    <el-col :span="5">
                        <el-input placeholder="请输入需要拉取的日志行数" v-model="num3" type="number" clearable></el-input>
                    </el-col>
                    <el-button type="primary" @click="getDstLog(2,num3)">拉取</el-button>
                </el-row>
            </el-card>
            <el-card class="card">
                <ul>
                    <li v-for="log in chatLog">{{ log }}</li>
                </ul>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="实验室" name="fifth">
            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span>智能更新</span>
                </div>
                <ul>
                    <li>Klei饥荒最新版本号：
                        <strong style="color: green">{{versionMap.steamVersion}}</strong>
                    </li>
                    <li>当前服务器的版本号：
                        <strong style="color: green">{{versionMap.localVersion}}</strong>
                    </li>
                </ul>
            </el-card>
        </el-tab-pane>
    </el-tabs>
</div>

</body>

<script>

    new Vue({
        el: '#sys_index',
        data: {
            activeName: 'first',
            num1: 100,
            num2: 100,
            num3: 100,
            masterLog: [],
            cavesLog: [],
            chatLog: [],
            scheduleVO: undefined,
            updateTimeList: [],
            backupTimeList: [],
            notStartMaster:false,
            notStartCaves:false,
            smartUpdate:false,
            versionMap: {},
            model: {
                backupTimeList: [],
                updateTimeList: [],
            },
        },
        created() {
            this.getScheduleList();
            this.getVersion();
        },
        methods: {
            getScheduleList() {
                get("/system/getScheduleList").then((data) => {
                    this.scheduleVO = data;
                    //初始化
                    this.updateTimeList = [];
                    this.backupTimeList = [];
                    if (this.scheduleVO.updateTimeList) {
                        this.scheduleVO.updateTimeList.forEach(e => {
                            let obj = {};
                            obj.time = "2020-10-23 " + e.time;
                            obj.count = e.count;
                            this.updateTimeList.push(obj);
                        })
                    }
                    if (this.scheduleVO.backupTimeList) {
                        this.scheduleVO.backupTimeList.forEach(e => {
                            let obj = {};
                            obj.time = "2020-10-23 " + e.time;
                            obj.count = e.count;
                            this.backupTimeList.push(obj);
                        })
                    }
                    this.notStartMaster = data.notStartMaster ? data.notStartMaster : false;
                    this.notStartCaves = data.notStartCaves ? data.notStartCaves : false;
                    this.smartUpdate = data.smartUpdate ? data.smartUpdate : false;
                })
            },
            getVersion(){
                get("/system/getVersion").then((data) => {
                    this.versionMap = data;
                });
            },
            addUpdateTime() {
                this.updateTimeList.push({count: 0, time: '2020-10-23 06:00:00'});
            },
            delUpdateTime(key) {
                this.updateTimeList.splice(key, 1);
            },
            addBackupTime() {
                this.backupTimeList.push({count: 0, time: '2020-10-23 06:00:00'});
            },
            delBackupTime(key) {
                this.backupTimeList.splice(key, 1);
            },
            //格式化时间
            formatTime(date, fmt) {
                if (date == null || date == undefined || date == '') {
                    return null;
                }
                var date = new Date(date);
                if (/(y+)/.test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
                }
                var o = {
                    'M+': date.getMonth() + 1,
                    'd+': date.getDate(),
                    'h+': date.getHours(),
                    'm+': date.getMinutes(),
                    's+': date.getSeconds()
                };
                for (var k in o) {
                    if (new RegExp('(' + k + ')').test(fmt)) {
                        var str = o[k] + '';
                        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : ('00' + str).substr(str.length));
                    }
                }
                return fmt;
            },
            saveSchedule() {
                let params = {};
                params.backupTimeList = [];
                params.updateTimeList = [];
                if (this.backupTimeList.length > 0) {
                    this.backupTimeList.forEach(e => {
                        let formatTime = this.formatTime(e.time, "yyyy-MM-dd hh:mm:ss");
                        let obj = {time: formatTime, count: e.count};
                        params.backupTimeList.push(obj);
                    })
                }
                if (this.updateTimeList.length > 0) {
                    this.updateTimeList.forEach(e => {
                        let formatTime = this.formatTime(e.time, "yyyy-MM-dd hh:mm:ss");
                        let obj = {time: formatTime, count: e.count};
                        params.updateTimeList.push(obj);
                    })
                }
                params.notStartMaster = this.notStartMaster;
                params.notStartCaves = this.notStartCaves;
                params.smartUpdate = this.smartUpdate;
                post("/system/saveSchedule", params).then((data) => {
                    if (data) {
                        this.$message({message: data.message, type: 'success'});
                    } else {
                        this.$message({message: '保存成功！', type: 'success'});
                        this.getScheduleList()
                    }
                })
            },
            getDstLog(type, rowNum) {
                let params = {type: type, rowNum: rowNum}
                get("/system/getDstLog", params).then((data) => {
                    switch (type){
                        case 0:
                            this.masterLog = data;
                            break;
                        case 1:
                            this.cavesLog = data;
                            break;
                        case 2:
                            this.chatLog = data;
                            break;
                    }
                })
            },

        }
    });


</script>

</html>
