<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <#import "user/spring.ftl" as spring>
    <title><@spring.message code="setting.player.title"/></title>
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
        <el-tab-pane label="<@spring.message code="setting.system.time.task"/>" name="first">
            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span><@spring.message code="setting.system.time.task.desc"/></span>
                </div>
                <div style="margin: 5px" v-for="item in scheduleVO.backupTimeList">
                        <@spring.message code="setting.system.task.execution.time"/>：{{item.time}},

                        <@spring.message code="setting.system.task.execution.status"/>：
                        <strong style="color: green" v-if="item.count > 0">
                            <@spring.message code="setting.system.task.execution.status.done"/></strong><strong style="color: orange" v-if="item.count === 0">
                        <@spring.message code="setting.system.task.execution.status.not.performed"/></strong>
                </div>

                <el-row style="margin: 5px">
                    <el-col :span="5">
                        <el-button :size="size" type="primary" @click="addBackupTime()"><@spring.message code="setting.player.admin.add"/> <@spring.message code="setting.system.task.execution.time"/></el-button>
                    </el-col>
                </el-row>

                <tempate v-for="(item,key) in backupTimeList">
                    <div style="margin: 5px">
                        <el-time-picker style="width:120px" :size="size" placeholder="<@spring.message code="home.pane1.card1.dst.please.choose"/> <@spring.message code="setting.system.task.execution.time"/>" v-model="item.time" clearable></el-time-picker>
                        <el-button :size="size" type="warning" style="margin-left: 5px" @click="delBackupTime(key)"><@spring.message code="setting.player.admin.delete"/></el-button>
                    </div>
                </tempate>
            </el-card>

            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span><@spring.message code="setting.system.time.task.update.game"/></span>
                    <span style="color: red;margin-left: 40px"><@spring.message code="setting.system.time.task.update.game.not.start"/>：</span>
                        <el-checkbox v-model="notStartMaster"><@spring.message code="setting.system.ground"/></el-checkbox>
                        <el-checkbox v-model="notStartCaves"><@spring.message code="setting.system.cave"/></el-checkbox>

                    <span style="color: red;margin-left: 40px"><@spring.message code="setting.system.smart.update"/>：</span>
                    <el-switch v-model="smartUpdate" active-text="<@spring.message code="setting.system.open"/>" inactive-text="<@spring.message code="setting.system.close"/>"></el-switch>
                </div>
                    <div v-for="item in scheduleVO.updateTimeList"><@spring.message code="setting.system.task.execution.time"/>：{{item.time}}, <@spring.message code="setting.system.task.execution.status"/>：
                        <strong style="color: green" v-if="item.count > 0">
                            <@spring.message code="setting.system.task.execution.status.done"/></strong><strong style="color: orange" v-if="item.count === 0">
                            <@spring.message code="setting.system.task.execution.status.not.performed"/></strong>
                    </div>
                <el-row style="margin: 5px">
                    <el-col :span="5">
                        <el-button :size="size" type="primary" @click="addUpdateTime()"><@spring.message code="setting.player.admin.add"/> <@spring.message code="setting.system.task.execution.time"/></el-button>
                    </el-col>
                </el-row>

                <tempate v-for="(item,key) in updateTimeList">
                    <div style="margin: 5px">
                        <el-time-picker :size="size" style="width:120px" placeholder="<@spring.message code="home.pane1.card1.dst.please.choose"/> <@spring.message code="setting.system.task.execution.time"/>" v-model="item.time" clearable></el-time-picker>
                        <el-button :size="size" type="warning" style="margin-left: 5px" @click="delUpdateTime(key)"><@spring.message code="setting.player.admin.delete"/></el-button>
                    </div>
                </tempate>
            </el-card>

            <el-card style="margin: 10px; position: sticky; bottom: 0;  z-index: 10;">
                <el-button :size="size" type="primary" @click="saveSchedule()"><@spring.message code="home.pane1.card1.dst.active.save"/></el-button>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="<@spring.message code="setting.system.ground.run.log"/>" name="second">
            <el-card class="card">
                        <el-input :size="size" placeholder="<@spring.message code="setting.system.run.log.desc"/>" v-model="num1" type="number" clearable></el-input>
                    <el-button :size="size" type="primary" @click="getDstLog(0,num1)"><@spring.message code="setting.system.pull"/></el-button>
            </el-card>
            <el-card class="card">
                <ul>
                    <li v-for="log in masterLog">{{ log }}</li>
                </ul>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="<@spring.message code="setting.system.cave.run.log"/>" name="third">
            <el-card class="card">
                        <el-input :size="size" placeholder="<@spring.message code="setting.system.run.log.desc"/>" v-model="num2" type="number" clearable></el-input>
                    <el-button :size="size" type="primary" @click="getDstLog(1,num2)"><@spring.message code="setting.system.pull"/></el-button>
            </el-card>
            <el-card class="card">
                <ul>
                    <li v-for="log in cavesLog">{{ log }}</li>
                </ul>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="<@spring.message code="setting.system.player.chat.log"/>" name="fourth">
            <el-card class="card">
                        <el-input :size="size" placeholder="<@spring.message code="setting.system.run.log.desc"/>" v-model="num3" type="number" clearable></el-input>
                    <el-button :size="size" type="primary" @click="getDstLog(2,num3)"><@spring.message code="setting.system.pull"/></el-button>
            </el-card>
            <el-card class="card">
                <ul>
                    <li v-for="log in chatLog">{{ log }}</li>
                </ul>
            </el-card>
        </el-tab-pane>
        <el-tab-pane label="<@spring.message code="setting.system.laboratory"/>" name="fifth">
            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span><@spring.message code="setting.system.smart.update"/></span>
                </div>
                <ul>
                    <li>Klei <@spring.message code="setting.system.dst.latest.version"/>：
                        <strong style="color: green">{{versionMap.steamVersion}}</strong>
                    </li>
                    <li><@spring.message code="setting.system.dst.now.version"/>：
                        <strong style="color: green">{{versionMap.localVersion}}</strong>
                    </li>
                </ul>
            </el-card>
<#--        </el-tab-pane>-->
<#--        <el-tab-pane label="<@spring.message code="setting.system.advanced.settings"/>" name="sixth">-->
            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span><@spring.message code="setting.system.advanced.tips"/></span>
                </div>
                <el-row style="margin: 5px">
                    <el-col :span="3">
                        <@spring.message code="setting.system.advanced.master.port"/>：
                    </el-col>
                    <el-col :span="5">
                        <el-input type="number" v-model="gamePort.masterPort" placeholder="<@spring.message code="setting.system.advanced.master.tips"/>"/>
                    </el-col>
                </el-row>
                <el-row style="margin: 5px">
                    <el-col :span="3">
                        <@spring.message code="setting.system.advanced.ground.port"/>：
                    </el-col>
                    <el-col :span="5">
                        <el-input type="number" v-model="gamePort.groundPort" placeholder="<@spring.message code="setting.system.advanced.ground.tips"/>"/>
                    </el-col>
                </el-row>
                <el-row style="margin: 5px">
                    <el-col :span="3">
                        <@spring.message code="setting.system.advanced.caves.port"/>：
                    </el-col>
                    <el-col :span="5">
                        <el-input type="number" v-model="gamePort.cavesPort" placeholder="<@spring.message code="setting.system.advanced.caves.tips"/>"/>
                    </el-col>
                </el-row>
            </el-card>
            <el-card style="margin: 10px; position: sticky; bottom: 0;  z-index: 10;">
                <el-button :size="size" type="primary" @click="saveGamePort()"><@spring.message code="home.pane1.card1.dst.active.save"/></el-button>
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
            num1: 20,
            num2: 20,
            num3: 20,
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
            labelPosition:'left',
            size:'medium',
            gamePort:{},
        },
        created() {
            this.getScheduleList();
            this.getVersion();
            this.getLabelPosition();
            this.getGamePort();
        },
        mounted(){
         window.onresize = () => {
                this.getLabelPosition()
            }
          },
        methods: {
            getLabelPosition(){
                let windowWidth = window.innerWidth
                    console.log('getLabelPosition',windowWidth)
                    if(windowWidth < 768){
                    this.labelPosition = 'top'
                    this.size= 'mini'
                }
                else {
                    this.labelPosition = 'left'
                    this.size = 'medium'
                }

            },
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
                        this.$message({message: data.message, type: 'warning'});
                    } else {
                        this.$message({message: '<@spring.message code="player.save.success"/>', type: 'success'});
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
            getGamePort(){
                get("/system/getGamePort").then((data) => {
                    this.gamePort = data;
                })
            },
            saveGamePort(){
                post("/system/saveGamePort",this.gamePort).then((data) => {
                    if (data) {
                        this.$message({message: data.message, type: 'warning'});
                    } else {
                        this.$message({message: '<@spring.message code="player.save.success"/>', type: 'success'});
                        this.getGamePort()
                    }
                })
            }

        }
    });


</script>

</html>
