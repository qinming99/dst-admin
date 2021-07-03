<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <#import "../system/user/spring.ftl" as spring>
    <title><@spring.message code="setting.player.title"/></title>
    <#include "../common/header.ftl"/>
</head>
<style>
    .card {
        margin: 10px;
    }
</style>
<body>

<div id="player_index">
    <el-tabs v-model="activeName">
            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span><@spring.message code="setting.player.admin.desc"/></span>
                </div>
                <el-row style="margin: 5px">
                    <el-col :span="5">
                        <el-button type="primary" @click="addAdmin()"><@spring.message code="setting.player.admin.add"/></el-button>
                    </el-col>
                </el-row>

                <tempate v-for="(item,key) in adminList">
                    <el-row style="margin: 5px">
                        <el-col :span="5">
                            <el-input placeholder="<@spring.message code="setting.player.admin.input.id"/> ID" v-model="adminList[key]" clearable></el-input>
                        </el-col>
                        <el-button type="warning" style="margin-left: 5px" @click="delAdmin(key)"><@spring.message code="setting.player.admin.delete"/></el-button>
                    </el-row>
                </tempate>

                <ul>
                    <li><@spring.message code="setting.player.admin.tips"/></li>
                    <li v-for="item in playerList" >{{item}}</li>
                </ul>

            </el-card>


            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span><@spring.message code="setting.player.admin.blacklist.desc"/></span>
                </div>
                <el-row style="margin: 5px">
                    <el-col :span="5">
                        <el-button type="primary" @click="addBlackList()"><@spring.message code="setting.player.admin.add"/></el-button>
                    </el-col>
                </el-row>

                <tempate v-for="(item,key) in blackList">
                    <el-row style="margin: 5px">
                        <el-col :span="5">
                            <el-input placeholder="<@spring.message code="setting.player.admin.input.id"/> ID" v-model="blackList[key]" clearable></el-input>
                        </el-col>
                        <el-button type="warning" style="margin-left: 5px" @click="delBlackList(key)"><@spring.message code="setting.player.admin.delete"/></el-button>
                    </el-row>
                </tempate>

                <ul>
                    <li><@spring.message code="setting.player.admin.tips"/></li>
                    <li v-for="item in playerList" >{{item}}</li>
                </ul>

            </el-card>


            <el-card style="margin: 10px; position: sticky; bottom: 0;  z-index: 10;">
                <el-button type="primary" @click="saveAdminAndBlackList()"><@spring.message code="home.pane1.card1.dst.active.save"/></el-button>
                <el-button type="primary" @click="saveAndRestart()"><@spring.message code="home.pane1.card1.save.restart"/></el-button>
            </el-card>

    </el-tabs>
</div>

</body>

<script>

    new Vue({
        el: '#player_index',
        data: {
            activeName: 'first',
            adminList:[],
            blackList:[],
            playerList:[],
        },
        created() {
            this.init();
        },
        methods: {
            init(){
                get("/player/getDstAdminList").then((data) => {
                    if (data) {
                        this.adminList = data;
                    }
                })
                get("/player/getDstBlacklist").then((data) => {
                    if (data) {
                        this.blackList = data;
                    }
                })
                this.getPlayerList();
            },
            addAdmin(){
                this.adminList.push("");
            },
            delAdmin(index){
                this.adminList.splice(index,1);
            },
            //保存并重启
            saveAndRestart() {
                this.saveAdminAndBlackList();
                this.restart();
            },
            saveAdminAndBlackList() {
                let param = {};
                param.adminList = this.adminList;
                param.blackList = this.blackList;
                post("/player/saveAdminAndBlackList", param).then((data) => {
                    if (data) {
                        this.$message({message: data.message, type: 'warning'});
                    } else {
                        this.$message({message: '<@spring.message code="player.save.success"/>', type: 'success'});
                        this.init();
                    }
                })
            },
            restart(){
                get("/home/stop", {type: 0}).then((data) => {
                })
                get("/home/start", {type: 0}).then((data) => {
                })
            },
            getPlayerList(){
                get("/home/getPlayerList").then((data) => {
                    if (data) {
                        this.playerList = data;
                    }
                })
            },
            addBlackList(){
              this.blackList.push("");
            },
            delBlackList(index){
                this.blackList.splice(index,1);
            },
        }
    });


</script>

</html>
