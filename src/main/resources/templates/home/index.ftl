<!DOCTYPE html>
<html lang="cn">
<head>
    <#import "../system/user/spring.ftl" as spring>
    <meta charset="UTF-8">
    <title><@spring.message code="home.title"/></title>
    <#include "../common/header.ftl"/>
</head>
<body>

<div id="app">

    <template>
        <el-tabs v-model="activeName">
            <el-tab-pane label="<@spring.message code="home.tab-pane1"/>" name="first">
                <el-row v-loading="loading"
                        element-loading-text="<@spring.message code="home.loading-text"/>"
                        element-loading-spinner="el-icon-loading"
                        element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-col :sm="16" :xs="24">
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span><@spring.message code="home.pane1.card1.title1"/></span>
                            </div>
                            <el-form :size="size"  ref="form" :label-position="labelPosition" label-width="150px">
                                <el-form-item label="<@spring.message code="home.pane1.card1.dst.status"/>：">
                                    <el-button v-if="masterStatus" icon="el-icon-video-play" type="primary"><@spring.message code="home.pane1.card1.dst.master.running"/>
                                    </el-button>
                                    <el-button    v-if="!masterStatus" icon="el-icon-video-play" type="warning"><@spring.message code="home.pane1.card1.dst.master.not"/>
                                    </el-button>
                                     <el-button  v-if="cavesStatus" icon="el-icon-video-play" type="primary">
                                        <@spring.message code="home.pane1.card1.dst.caves.running"/></el-button>
                                    <el-button   v-if="!cavesStatus" icon="el-icon-video-play" type="warning">
                                        <@spring.message code="home.pane1.card1.dst.caves.not"/>
                                    </el-button>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card1.dst.start.masterCaves"/>：">
                                    <el-switch  v-model="runStatus" active-text="<@spring.message code="home.pane1.card1.dst.active.on"/>"
                                               inactive-text="<@spring.message code="home.pane1.card1.dst.active.off"/>" :width="50"
                                               @change="controlDst(runStatus,0)"></el-switch>
                                </el-form-item>
                                <el-form-item label="<@spring.message code="home.pane1.card1.dst.start.master"/>：">
                                    <el-switch  v-model="masterStatus" active-text="<@spring.message code="home.pane1.card1.dst.active.on"/>" inactive-text="<@spring.message code="home.pane1.card1.dst.active.off"/>" :width="50"
                                               @change="controlDst(masterStatus,1)"></el-switch>
                                </el-form-item>
                                <el-form-item label="<@spring.message code="home.pane1.card1.dst.start.caves"/>：">
                                    <el-switch  v-model="cavesStatus" active-text="<@spring.message code="home.pane1.card1.dst.active.on"/>" inactive-text="<@spring.message code="home.pane1.card1.dst.active.off"/>" :width="50"
                                               @change="controlDst(cavesStatus,2)"></el-switch>
                                </el-form-item>
                                <el-form-item label="<@spring.message code="home.pane1.card1.dst.quickOperation"/>：">
                                    <el-popover  placement="top" width="200" v-model="visible2">
                                        <p><@spring.message code="home.pane1.card1.dst.search.suggestions"/></p>
                                        <div style="text-align: right; margin: 0">
                                            <el-button  type="text" @click="visible2 = false"><@spring.message code="home.pane1.card1.dst.cancel"/></el-button>
                                            <el-button type="primary"  @click="updateGame()"><@spring.message code="home.pane1.card1.dst.confirm"/></el-button>
                                        </div>
                                        <el-button slot="reference" icon="el-icon-s-promotion"><@spring.message code="home.pane1.card1.dst.updateGame"/></el-button>
                                    </el-popover>
                                    <el-popover  placement="top" width="200" v-model="visible3">
                                        <p><@spring.message code="home.pane1.card1.dst.updateGameMods.suggestions"/></p>
                                        <div style="text-align: right; margin: 0">
                                            <el-button  type="text" @click="visible3 = false"><@spring.message code="home.pane1.card1.dst.cancel"/></el-button>
                                            <el-button type="primary"  @click="updateGameMods()"><@spring.message code="home.pane1.card1.dst.confirm"/></el-button>
                                        </div>
                                        <el-button slot="reference" icon="el-icon-s-promotion"><@spring.message code="home.pane1.card1.dst.updateGameMods"/></el-button>
                                    </el-popover>
                                    <el-button icon="el-icon-refresh" @click="backupGame()"><@spring.message code="home.pane1.card1.dst.createBackup"/></el-button>
                                </el-form-item>
                                <el-form-item  label="<@spring.message code="home.pane1.card1.dst.cleanGameArchive"/>：" >
                                    <el-popover placement="top" width="200" v-model="visible">
                                        <p><@spring.message code="home.pane1.card1.dst.clean.suggestions"/></p>
                                        <div style="text-align: right; margin: 0">
                                            <el-button type="text" @click="visible = false"><@spring.message code="home.pane1.card1.dst.cancel"/></el-button>
                                            <el-button type="warning" size="mini" @click="clearGame()"><@spring.message code="home.pane1.card1.dst.confirm"/></el-button>
                                        </div>
                                        <el-button slot="reference" type="danger" icon="el-icon-delete"><@spring.message code="home.pane1.card1.dst.clean"/></el-button>
                                    </el-popover>
                                </el-form-item>
                                <el-form-item label="<@spring.message code="home.pane1.card1.dst.restoreBackup"/>：">
                                    <el-select v-model="backupName" filterable placeholder="<@spring.message code="home.pane1.card1.dst.please.choose"/>">
                                        <el-option
                                                v-for="item in backupList"
                                                :key="item"
                                                :label="item"
                                                :value="item">
                                        </el-option>
                                    </el-select>
                                    <el-popover placement="top" width="200" v-model="visible1">
                                        <p><@spring.message code="home.pane1.card1.dst.restore.suggestions"/></p>
                                        <div style="text-align: right; margin: 0">
                                            <el-button size="mini" type="text" @click="visible1 = false"><@spring.message code="home.pane1.card1.dst.cancel"/></el-button>
                                            <el-button type="primary" size="mini" @click="restoreBackup()"><@spring.message code="home.pane1.card1.dst.confirm"/></el-button>
                                        </div>
                                        <el-button slot="reference" icon="el-icon-refresh-left"><@spring.message code="home.pane1.card1.dst.restore"/></el-button>
                                    </el-popover>
                                </el-form-item>
                                <el-collapse accordion>
                                    <el-collapse-item title="<@spring.message code="home.pane1.card1.dst.other.fun"/>" name="other_op">
                                        <el-form-item label="<@spring.message code="home.pane1.card1.dst.other.clear.save"/>：">
                                            <el-tooltip class="item" effect="dark"
                                                        content="<@spring.message code="home.pane1.card1.dst.other.tips"/>" placement="bottom">
                                                <el-popover placement="top" width="200" v-model="visible6">
                                                    <p><@spring.message code="home.pane1.card1.dst.other.tips.confirm"/></p>
                                                    <div style="text-align: right; margin: 0">
                                                        <el-button type="text" @click="visible6 = false"><@spring.message code="home.pane1.card1.dst.cancel"/></el-button>
                                                        <el-button type="primary" @click="delCavesRecord()"><@spring.message code="home.pane1.card1.dst.confirm"/></el-button>
                                                    </div>
                                                    <el-button slot="reference" type="warning" icon="el-icon-delete"><@spring.message code="home.pane1.card1.dst.other.only.clean"/></el-button>
                                                </el-popover>
                                            </el-tooltip>
                                        </el-form-item>
                                        <el-form-item label="<@spring.message code="home.pane1.card1.dst.other.del.folder"/>：">
                                            <el-tooltip class="item" effect="dark"
                                                        content="<@spring.message code="home.pane1.card1.dst.other.del.tips"/>" placement="bottom">
                                                <el-popover placement="top" width="200" v-model="visible7">
                                                    <p><@spring.message code="home.pane1.card1.dst.other.del.tips.confirm"/>？</p>
                                                    <div style="text-align: right; margin: 0">
                                                        <el-button type="text" @click="visible7 = false"><@spring.message code="home.pane1.card1.dst.cancel"/></el-button>
                                                        <el-button type="primary" @click="delMyDediServer()"><@spring.message code="home.pane1.card1.dst.confirm"/></el-button>
                                                    </div>
                                                    <el-button slot="reference" type="danger" icon="el-icon-delete"><@spring.message code="home.pane1.card1.dst.other.del"/></el-button>
                                                </el-popover>
                                            </el-tooltip>
                                        </el-form-item>
                                    </el-collapse-item>
                                </el-collapse>
                            </el-form>
                        </el-card>
                    </el-col>
                    <el-col :sm="8" :xs="24">
                        <el-card class="box-card" :style="labelPosition === 'top' ? 'margin-top:10px' : 'margin-left: 10px'">
                            <div slot="header" class="clearfix">
                                <span><@spring.message code="home.pane1.card3.archive.info"/></span>
                            </div>
                            <h5><@spring.message code="setting.room.name"/>：{{gameArchive.clusterName}} </h5>
                            <h5><@spring.message code="setting.game.mode"/>：{{transformGameMode(gameArchive.gameMode)}} </h5>
                            <h5><@spring.message code="setting.game.max.players"/>：{{gameArchive.maxPlayers}} </h5>
                            <h5><@spring.message code="setting.game.password"/>：{{gameArchive.clusterPassword}} </h5>
                            <h5><@spring.message code="home.pane1.card3.archive.day"/>：{{gameArchive.playDay}} </h5>
                            <h5><@spring.message code="home.pane1.card3.archive.season"/>：{{gameArchive.season}} </h5>
                            <h5><@spring.message code="home.pane1.card3.mod.num"/>：{{gameArchive.totalModNum}} </h5>
                        </el-card>
                    </el-col>
                </el-row>

            </el-tab-pane>
            <el-tab-pane label="<@spring.message code="home.tab-pane2"/>" name="second">

                <el-row v-loading="loading"
                        element-loading-text="<@spring.message code="home.loading-text"/>"
                        element-loading-spinner="el-icon-loading"
                        element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-col :sm="16" :xs="24">
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span><@spring.message code="home.card.title2"/></span>
                            </div>
                            <el-form :size="size" ref="form" :label-position="labelPosition" label-width="<#if lang == 'zh'>180px<#else >230px</#if>">
                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.send.announcement.notice"/>：" >
                                    <el-row>
                                        <el-col :span="10">
                                            <el-input type="textarea" :rows="3" maxlength="50" placeholder="<@spring.message code="home.pane1.card2.dst.please.enter.content"/>" show-word-limit v-model="broadcastContent"></el-input>
                                        </el-col>
                                        <el-col :span="5"> <el-button @click="sendBroadcast()" style="margin-left: 10px" icon="el-icon-s-comment" ><@spring.message code="home.pane1.card2.dst.send"/></el-button></el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.master.console.label"/>：" >
                                    <el-row>
                                        <el-col :span="10">
                                            <el-input type="textarea" :rows="3" maxlength="500" placeholder="<@spring.message code="home.pane1.card2.dst.master.console"/>" show-word-limit v-model="masterCommand"></el-input>
                                        </el-col>
                                        <el-col :span="5"> <el-button @click="masterConsole()" style="margin-left: 10px" icon="el-icon-s-comment" ><@spring.message code="home.pane1.card2.dst.console.exec"/></el-button></el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.master.console.label"/>：" >
                                    <el-row>
                                        <el-col :span="10">
                                            <el-input type="textarea" :rows="3" maxlength="500" placeholder="<@spring.message code="home.pane1.card2.dst.caves.console"/>" show-word-limit v-model="cavesCommand"></el-input>
                                        </el-col>
                                        <el-col :span="5"> <el-button @click="cavesConsole()" style="margin-left: 10px" icon="el-icon-s-comment" ><@spring.message code="home.pane1.card2.dst.console.exec"/></el-button></el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.kickOut.the.player"/>：">
                                    <el-row>
                                        <el-col :span="15" v-for="item in playerList" style="padding-top: 5px">
                                            <el-button @click="kickPlayer2(item)"
                                                       icon="el-icon-position"><@spring.message code="home.pane1.card2.dst.kickOut"/> : {{item}}
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.revive"/>：">
                                    <el-row>
                                        <el-col :span="15" v-for="item in playerList" style="padding-top: 5px">
                                            <el-button @click="playerOperate(0,item)"
                                                       icon="el-icon-position"><@spring.message code="home.pane1.card2.dst.revive"/> : {{item}}
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.kill"/>：">
                                    <el-row>
                                        <el-col :span="15" v-for="item in playerList" style="padding-top: 5px">
                                            <el-button @click="playerOperate(1,item)"
                                                       icon="el-icon-position"><@spring.message code="home.pane1.card2.dst.kill"/> : {{item}}
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.change.roles"/>：">
                                    <el-tooltip class="item" effect="dark" content="<@spring.message code="home.pane1.card2.dst.change.roles.tips"/>" placement="bottom">
                                        <i class="el-icon-info"></i>
                                    </el-tooltip>
                                    <el-row>
                                        <el-col :span="15" v-for="item in playerList" style="padding-top: 5px">
                                            <el-button @click="playerOperate(2,item)"
                                                       icon="el-icon-position"><@spring.message code="home.pane1.card2.dst.change"/> : {{item}}
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.rollback.rules"/>：">
                                    <el-row>
                                        <el-col :span="24">
                                            <el-button icon="el-icon-refresh-left" @click="rollback(1)">
                                                <@spring.message code="home.pane1.card2.dst.rollback"/>1<@spring.message code="home.pane1.card2.dst.rollback.unit"/>
                                            </el-button>
                                            <el-button icon="el-icon-refresh-left" @click="rollback(2)">
                                                <@spring.message code="home.pane1.card2.dst.rollback"/>2<@spring.message code="home.pane1.card2.dst.rollback.unit"/>
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                    <el-row style="margin-top: 10px">
                                        <el-col :span="24">
                                            <el-button icon="el-icon-refresh-left" @click="rollback(3)">
                                                <@spring.message code="home.pane1.card2.dst.rollback"/>3<@spring.message code="home.pane1.card2.dst.rollback.unit"/>
                                            </el-button>
                                            <el-button icon="el-icon-refresh-left" @click="rollback(4)">
                                                <@spring.message code="home.pane1.card2.dst.rollback"/>4<@spring.message code="home.pane1.card2.dst.rollback.unit"/>
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                    <el-row style="margin-top: 10px">
                                        <el-col :span="24">
                                            <el-button icon="el-icon-refresh-left" @click="rollback(5)">
                                                <@spring.message code="home.pane1.card2.dst.rollback"/>5<@spring.message code="home.pane1.card2.dst.rollback.unit"/>
                                            </el-button>
                                            <el-button icon="el-icon-refresh-left" @click="rollback(6)">
                                                <@spring.message code="home.pane1.card2.dst.rollback"/>6<@spring.message code="home.pane1.card2.dst.rollback.unit"/>
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                </el-form-item>

                                <el-form-item label="<@spring.message code="home.pane1.card2.dst.reset.world"/>：">
                                    <el-row>
                                        <el-col :span="10">
                                            <el-popover placement="top" width="200" v-model="visible5">
                                                <p><@spring.message code="home.pane1.card2.dst.reset.confirm"/>?</p>
                                                <div style="text-align: right; margin: 0">
                                                    <el-button size="mini" type="text" @click="visible5 = false"><@spring.message code="home.pane1.card1.dst.cancel"/></el-button>
                                                    <el-button type="primary" size="mini" @click="regenerate()"><@spring.message code="home.pane1.card1.dst.confirm"/></el-button>
                                                </div>
                                                <el-button slot="reference" icon="el-icon-refresh"><@spring.message code="home.pane1.card2.dst.reset"/></el-button>
                                            </el-popover>
                                        </el-col>
                                    </el-row>
                                </el-form-item>

                            </el-form>

                        </el-card>
                    </el-col>
                    <el-col :sm="8" :xs="24">
                        <el-card class="box-card" :style="labelPosition === 'top' ? 'margin-top:10px' : 'margin-left: 10px'">
                            <div slot="header" class="clearfix">
                                <span><@spring.message code="header.server.situation"/></span>
                            </div>
                            <el-row>
                                <el-col>
                                    <el-progress type="dashboard" :stroke-width="10" :percentage="cpuInfo"
                                                 :color="colors"></el-progress>
                                    <div>
                                        <span><@spring.message code="header.server.cpu.cores.num"/>：{{cpuNum}} , <@spring.message code="header.server.usage.rate"/>：{{cpuInfo}}%</span>
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row style="margin-top: 20px">
                                <el-col>
                                    <el-progress type="dashboard" :stroke-width="10" :percentage="menInfo"
                                                 :color="colors"></el-progress>
                                    <div>
                                        <span><@spring.message code="header.server.memory"/>：{{menTotal}}GB , <@spring.message code="header.server.usage.rate"/>：{{menInfo}}%</span>
                                    </div>
                                </el-col>
                            </el-row>
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
            visible6: false,
            visible7: false,
            gameArchive:{},
            cpuInfo: 0,
            cpuNum: 0,
            menInfo: 0,
            menTotal: 0,
            timer: null,
            activeName:'first',
            playerList:[],
            broadcastContent:null,//广播内容
            kickUserId:null,//踢出的玩家
            dayNum:null,//回滚天数
            masterCommand:null,//地面命令
            cavesCommand:null,//洞穴命令
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
            labelPosition:'left',
            size:'medium'
        },
        created() {
            //拉取服务器信息
            this.getSystemInfo();
            this.timer = setInterval(function () {
                vue.getSystemInfo();
            }, 2000);
            this.getLabelPosition();
            this.getPlayerList();
            this.getGameArchive();
        },
        mounted(){
         window.onresize = () => {
                this.getLabelPosition()
            }
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
                    this.successMessage('<@spring.message code="home.js.clear.success"/>');
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
            updateGameMods(){
                this.visible3 = false;//隐藏
                this.loading = true;
                get("/home/updateGameMods").then((data) => {
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
                        message: '<@spring.message code="home.js.select.tar"/>',
                        type: 'warning'
                    });
                }
            },
            //清理洞穴
            delCavesRecord() {
                this.visible6 = false;//隐藏
                this.loading = true;
                get("/home/delCavesRecord").then((data) => {
                    this.loading = false;
                    if (data) {
                        this.warningMessage(data.message);
                    } else {
                        this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                    }
                })
            },
            //清理存档
            delMyDediServer() {
                this.visible7 = false;//隐藏
                this.loading = true;
                get("/home/delMyDediServer").then((data) => {
                    this.loading = false;
                    if (data) {
                        this.warningMessage(data.message);
                    } else {
                        this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                    }
                })
            },
            sendBroadcast(){
                if (this.broadcastContent) {
                    get("/home/sendBroadcast", {message: this.broadcastContent}).then((data) => {
                        if (data) {
                            this.warningMessage(data.message);
                        } else {
                            this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                        }
                    })
                } else {
                    this.$message({
                        message: '<@spring.message code="home.js.input.send.content"/>',
                        type: 'warning'
                    });
                }
            },
            getPlayerList(){
                get("/home/getPlayerList").then((data) => {
                    if (data) {
                        this.playerList = data;
                    }
                })
            },
            kickPlayer(){
                this.visible3 = false;
                if (this.kickUserId) {
                    get("/home/kickPlayer", {userId: this.kickUserId}).then((data) => {
                        if (data) {
                            this.warningMessage(data.message);
                        } else {
                            this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                        }
                    })
                } else {
                    this.$message({
                        message: '<@spring.message code="home.js.input.playerId"/>',
                        type: 'warning'
                    });
                }
            },
            //踢出玩家
            kickPlayer2(player) {
                let split = player.split(" ");
                get("/home/kickPlayer", {userId: split[0]}).then((data) => {
                    if (data) {
                        this.warningMessage(data.message);
                    } else {
                        this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                    }
                })
                this.getPlayerList();
            },
            //针对玩家的高级操作
            playerOperate(type, player) {
                let split = player.split(" ");
                this.loading = true;
                let params = {userId: split[0], type: type};
                get("/home/playerOperate", params).then((data) => {
                    this.loading = false;
                    if (data) {
                        this.warningMessage(data.message);
                    } else {
                        this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                    }
                })
                this.getPlayerList();
            },
            //重置世界
            regenerate(){
                this.visible5 = false;
                get("/home/regenerate").then((data) => {
                    if (data) {
                        this.warningMessage(data.message);
                    } else {
                        this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                    }
                })
            },
            //回滚世界
            rollback(day){
                get("/home/rollback", {dayNum: day}).then((data) => {
                    if (data) {
                        this.warningMessage(data.message);
                    } else {
                        this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                    }
                })
            },
            masterConsole(){
                if (this.masterCommand) {
                    post("/home/masterConsole", {command: this.masterCommand}).then((data) => {
                        if (data) {
                            this.warningMessage(data.message);
                        }else {
                            this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                        }
                    })
                } else {
                    this.$message({
                        message: '<@spring.message code="home.js.tip.input.code"/>',
                        type: 'warning'
                    });
                }
            },
            cavesConsole(){
                if (this.cavesCommand) {
                    post("/home/cavesConsole", {command: this.cavesCommand}).then((data) => {
                        if (data) {
                            this.warningMessage(data.message);
                        }else {
                            this.successMessage('<@spring.message code="home.js.execution.succeed"/>');
                        }
                    })
                } else {
                    this.$message({
                        message: '<@spring.message code="home.js.tip.input.code"/>',
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
            transformGameMode(gameMode) {
                switch (gameMode) {
                    case 'endless':
                        return "<@spring.message code='setting.game.mode.endless'/>";
                    case 'survival':
                        return "<@spring.message code='setting.game.mode.survival'/>";
                    case 'wilderness':
                        return "<@spring.message code='setting.game.mode.wilderness'/>";
                    default:
                        return gameMode;
                }
            },
            //存档信息
            getGameArchive(){
                get("/home/getGameArchive").then((data) => {
                    if (data){
                        this.gameArchive = data;
                    }
                })
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

            }
        }
    });

</script>


</html>
