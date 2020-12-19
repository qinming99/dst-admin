<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <#include "../common/header.ftl"/>
</head>
<body>

<div id="app">

    <template>
        <el-tabs v-model="activeName">
            <el-tab-pane label="服务器面板" name="first">

                <el-row v-loading="loading"
                        element-loading-text="拼命加载中"
                        element-loading-spinner="el-icon-loading"
                        element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-col :span="16">
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span>游戏状态</span>
                            </div>
                            <el-form ref="form" label-position="left" label-width="150px">
                                <el-row>
                                    <el-col :span="6">
                                        <el-form-item label="饥荒状态：">
                                            <el-button v-if="masterStatus" icon="el-icon-video-play" type="primary">地面运行中
                                            </el-button>
                                            <el-button v-if="!masterStatus" icon="el-icon-video-play" type="warning">地面未运行
                                            </el-button>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="6">
                                        <el-form-item>
                                            <el-button v-if="cavesStatus" icon="el-icon-video-play" type="primary">洞穴运行中</el-button>
                                            <el-button v-if="!cavesStatus" icon="el-icon-video-play" type="warning">洞穴未运行
                                            </el-button>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-form-item label="启动地面和洞穴：">
                                    <el-switch v-model="runStatus" active-text="启动" inactive-text="关闭" :width="50"
                                               @change="controlDst(runStatus,0)"></el-switch>
                                </el-form-item>
                                <el-form-item label="启动地面：">
                                    <el-switch v-model="masterStatus" active-text="启动" inactive-text="关闭" :width="50"
                                               @change="controlDst(masterStatus,1)"></el-switch>
                                </el-form-item>
                                <el-form-item label="启动洞穴：">
                                    <el-switch v-model="cavesStatus" active-text="启动" inactive-text="关闭" :width="50"
                                               @change="controlDst(cavesStatus,2)"></el-switch>
                                </el-form-item>
                                <el-form-item label="快捷操作：">
                                    <el-popover placement="top" width="200" v-model="visible2">
                                        <p>搜索不到服务器时可以尝试更新游戏，更新时将停止服务器哦！</p>
                                        <div style="text-align: right; margin: 0">
                                            <el-button size="mini" type="text" @click="visible2 = false">取消</el-button>
                                            <el-button type="primary" size="mini" @click="updateGame()">确定</el-button>
                                        </div>
                                        <el-button slot="reference" icon="el-icon-s-promotion">更新游戏</el-button>
                                    </el-popover>
                                    <el-button icon="el-icon-refresh" @click="backupGame()">创建备份</el-button>
                                </el-form-item>
                                <el-form-item label="清理游戏存档：">
                                    <el-popover placement="top" width="160" v-model="visible">
                                        <p>确认清理吗？清理之后将停止服务器,删除游戏进度哦！</p>
                                        <div style="text-align: right; margin: 0">
                                            <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                                            <el-button type="warning" size="mini" @click="clearGame()">确定</el-button>
                                        </div>
                                        <el-button slot="reference" type="danger" icon="el-icon-delete">清理</el-button>
                                    </el-popover>
                                </el-form-item>
                                <el-form-item label="恢复备份：">
                                    <el-select v-model="backupName" filterable placeholder="请选择">
                                        <el-option
                                                v-for="item in backupList"
                                                :key="item"
                                                :label="item"
                                                :value="item">
                                        </el-option>
                                    </el-select>
                                    <el-popover placement="top" width="200" v-model="visible1">
                                        <p>确认恢复该存档吗？恢复存档将停止服务器，同时当前游戏进度会被清理哦！</p>
                                        <div style="text-align: right; margin: 0">
                                            <el-button size="mini" type="text" @click="visible1 = false">取消</el-button>
                                            <el-button type="primary" size="mini" @click="restoreBackup()">确定</el-button>
                                        </div>
                                        <el-button slot="reference" icon="el-icon-refresh-left">恢复</el-button>
                                    </el-popover>
                                </el-form-item>
                            </el-form>
                        </el-card>
                    </el-col>
                    <el-col :span="7">
                        <el-card class="box-card" style="margin-left: 10px">
                            <div slot="header" class="clearfix">
                                <span>服务器状况</span>
                            </div>
                            <el-row>
                                <el-col>
                                    <el-progress type="dashboard" :stroke-width="10" :percentage="cpuInfo"
                                                 :color="colors"></el-progress>
                                    <div>
                                        <span>CPU核心数：{{cpuNum}}  使用率：{{cpuInfo}}%</span>
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row style="margin-top: 20px">
                                <el-col>
                                    <el-progress type="dashboard" :stroke-width="10" :percentage="menInfo"
                                                 :color="colors"></el-progress>
                                    <div>
                                        <span>总内存：{{menTotal}}GB  使用率：{{menInfo}}%</span>
                                    </div>
                                </el-col>
                            </el-row>
                        </el-card>
                    </el-col>
                </el-row>

            </el-tab-pane>
            <el-tab-pane label="远程控制面板" name="second">

                <el-row v-loading="loading"
                        element-loading-text="拼命加载中"
                        element-loading-spinner="el-icon-loading"
                        element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-col :span="23">
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span>后台控制</span>
                            </div>
                            <el-form ref="form" label-position="left" label-width="180px">
                                <el-form-item label="发送公告通知：">
                                    <el-row>
                                        <el-col :span="10">
                                            <el-input type="textarea" :rows="3" maxlength="50" placeholder="请输入内容" show-word-limit v-model="broadcastContent"></el-input>
                                        </el-col>
                                        <el-col :span="5"> <el-button @click="sendBroadcast()" style="margin-left: 10px" >发送</el-button></el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="踢出玩家：">
                                    <el-row>
                                        <el-col :span="10">
                                            <el-input  maxlength="15" placeholder="请输入玩家id" show-word-limit v-model="kickUserId"></el-input>
                                        </el-col>
                                        <el-col :span="5">
                                            <el-popover placement="top" width="200" v-model="visible3">
                                                <p>确认踢出该玩家吗？</p>
                                                <div style="text-align: right; margin: 0">
                                                    <el-button size="mini" type="text" @click="visible3 = false">取消</el-button>
                                                    <el-button type="primary" size="mini" @click="kickPlayer()">确定</el-button>
                                                </div>
                                                <el-button slot="reference" style="margin-left: 10px" icon="el-icon-refresh-left">踢出</el-button>
                                            </el-popover>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="回滚指定的天数1-5天：">
                                    <el-row>
                                        <el-col :span="10">
                                            <el-input type="number"  maxlength="1" placeholder="请输入天数" show-word-limit v-model="dayNum"></el-input>
                                        </el-col>
                                        <el-col :span="5">
                                            <el-popover placement="top" width="200" v-model="visible4">
                                                <p>确认回滚吗？</p>
                                                <div style="text-align: right; margin: 0">
                                                    <el-button size="mini" type="text" @click="visible4 = false">取消</el-button>
                                                    <el-button type="primary" size="mini" @click="rollback()">确定</el-button>
                                                </div>
                                                <el-button slot="reference" style="margin-left: 10px" icon="el-icon-refresh-left">回滚</el-button>
                                            </el-popover>
                                        </el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="重置世界：">
                                    <el-row>
                                        <el-col :span="10">
                                            <el-popover placement="top" width="200" v-model="visible5">
                                                <p>确认重置吗？</p>
                                                <div style="text-align: right; margin: 0">
                                                    <el-button size="mini" type="text" @click="visible5 = false">取消</el-button>
                                                    <el-button type="primary" size="mini" @click="regenerate()">确定</el-button>
                                                </div>
                                                <el-button slot="reference" icon="el-icon-refresh-left">重置</el-button>
                                            </el-popover>
                                        </el-col>
                                    </el-row>
                                </el-form-item>

                            </el-form>

                        </el-card>
                    </el-col>
                </el-row>

            </el-tab-pane>
        </el-tabs>
    </template>


</div>


</body>


<script>
    let vue = new Vue({
        el: '#app',
        data: {
            loading: false,
            runStatus: false,
            masterStatus: false,//地面状态
            cavesStatus: false,//洞穴状态
            backupName: null,//选择的备份文件名称
            backupList: [],
            visible: false,
            visible1: false,
            visible2: false,
            visible3: false,
            visible4: false,
            visible5: false,
            cpuInfo: 0,
            cpuNum: 0,
            menInfo: 0,
            menTotal: 0,
            timer: null,
            activeName:'first',
            broadcastContent:null,//广播内容
            kickUserId:null,//踢出的玩家
            dayNum:null,//回滚天数
            colors: [
                {color: '#00ff00', percentage: 10},
                {color: '#669900', percentage: 20},
                {color: '#29c1ab', percentage: 30},
                {color: '#33cc00', percentage: 40},
                {color: '#7fff80', percentage: 50},
                {color: '#996600', percentage: 60},
                {color: '#b2cc80', percentage: 70},
                {color: '#cc3300', percentage: 80},
                {color: '#e59980', percentage: 90},
                {color: '#ff0000', percentage: 100},
            ],
        },
        created() {
            //拉取服务器信息
            this.getSystemInfo();
            this.timer = setInterval(function () {
                vue.getSystemInfo();
            }, 2000);
        },
        destroyed() {
            clearInterval(this.timer)
        },
        methods: {
            //启动饥荒服务
            controlDst(status, type) {
                if (status) {
                    this.loading = true;
                    get("/home/start", {type: type}).then((data) => {
                        this.loading = false;
                        if (data) {
                            this.warningMessage(data.message);
                        }
                        this.getSystemInfo();
                    })
                } else {
                    this.loading = true;
                    get("/home/stop", {type: type}).then((data) => {
                        this.loading = false;
                        if (data) {
                            this.warningMessage(data.message);
                        }
                        this.getSystemInfo();
                    })
                }
            },
            //清理
            clearGame() {
                this.visible = false;//隐藏
                this.loading = true;
                get("/home/delRecord").then((data) => {
                    this.loading = false;
                    this.getSystemInfo();
                    this.successMessage("清理成功");
                })
            },
            //更新
            updateGame() {
                this.visible2 = false;//隐藏
                this.loading = true;
                get("/home/updateGame").then((data) => {
                    this.loading = false;
                    if (data) {
                        this.warningMessage(data.message);
                    }
                    this.getSystemInfo();
                })
            },
            //备份
            backupGame() {
                this.loading = true;
                get("/home/backup").then((data) => {
                    this.loading = false;
                    if (data) {
                        this.warningMessage(data.message);
                    }
                    this.getSystemInfo();
                })
            },
            //恢复存档
            restoreBackup() {
                this.visible1 = false;//隐藏
                if (this.backupName) {
                    this.loading = true;
                    get("/home/restore", {name: this.backupName}).then((data) => {
                        this.loading = false;
                        if (data) {
                            this.warningMessage(data.message);
                        }
                        this.getSystemInfo();
                    })
                } else {
                    this.$message({
                        message: '请选择一个存档记录',
                        type: 'warning'
                    });
                }
            },
            sendBroadcast(){
                if (this.broadcastContent) {
                    get("/home/sendBroadcast", {message: this.broadcastContent}).then((data) => {
                        if (data) {
                            this.warningMessage(data.message);
                        }
                        this.successMessage("执行成功");
                    })
                } else {
                    this.$message({
                        message: '请填写公告内容',
                        type: 'warning'
                    });
                }
            },
            kickPlayer(){
                if (this.kickUserId) {
                    get("/home/kickPlayer", {userId: this.kickUserId}).then((data) => {
                        if (data) {
                            this.warningMessage(data.message);
                        }
                        this.successMessage("执行成功");
                    })
                } else {
                    this.$message({
                        message: '请填写玩家id',
                        type: 'warning'
                    });
                }
            },
            //重置世界
            regenerate(){
                get("/home/regenerate").then((data) => {
                    if (data) {
                        this.warningMessage(data.message);
                    }
                    this.successMessage("执行成功");
                })
            },
            //回滚世界
            rollback(){
                if (this.dayNum) {
                    get("/home/rollback", {dayNum: this.dayNum}).then((data) => {
                        if (data) {
                            this.warningMessage(data.message);
                        }
                        this.successMessage("执行成功");
                    })
                } else {
                    this.$message({
                        message: '请填写回滚天数',
                        type: 'warning'
                    });
                }
            },
            successMessage(message) {
                this.$message({
                    message: message,
                    type: 'success'
                });
            },
            warningMessage(message) {
                this.$message({
                    message: message,
                    type: 'warning'
                });
            },
            getSystemInfo() {
                get("/home/getSystemInfo").then((data) => {
                    if (data) {
                        this.menInfo = data.mem.usage;
                        this.cpuInfo = data.cpu.used;
                        this.menTotal = data.mem.total;
                        this.cpuNum = data.cpu.cpuNum;
                        this.masterStatus = data.masterStatus;
                        this.cavesStatus = data.cavesStatus;
                        this.backupList = data.backupList;
                        if (data.masterStatus && data.cavesStatus) {
                            this.runStatus = true;
                        } else {
                            this.runStatus = false;
                        }
                    }
                })
            },
        }
    });

</script>


</html>
