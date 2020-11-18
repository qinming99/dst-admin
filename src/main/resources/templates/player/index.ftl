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

<div id="player_index">
    <el-tabs v-model="activeName">
        <el-tab-pane label="管理员设置" name="first">
            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span>游戏管理员（可以在游戏中管理玩家和重置世界）</span>
                </div>
                <el-row style="margin: 5px">
                    <el-col :span="5">
                        <el-button type="primary" @click="addAdmin()">添加</el-button>
                    </el-col>
                </el-row>

                <tempate v-for="(item,key) in adminList">
                    <el-row style="margin: 5px">
                        <el-col :span="5">
                            <el-input placeholder="输入玩家ID" v-model="adminList[key]" clearable></el-input>
                        </el-col>
                        <el-button type="warning" style="margin-left: 5px" @click="delAdmin(key)">删除</el-button>
                    </el-row>
                </tempate>

            </el-card>


            <el-card style="margin: 10px; position: sticky; bottom: 0;  z-index: 10;">
                <el-button type="primary" @click="saveAdminList()">保存</el-button>
            </el-card>

        </el-tab-pane>
        <el-tab-pane label="黑名单设置" name="third">

            <el-card class="card">
                <div slot="header" class="clearfix">
                    <span>黑名单（该列表的玩家禁止进入房间）</span>
                </div>
                <el-row style="margin: 5px">
                    <el-col :span="5">
                        <el-button type="primary" @click="addBlackList()">添加</el-button>
                    </el-col>
                </el-row>

                <tempate v-for="(item,key) in blackList">
                    <el-row style="margin: 5px">
                        <el-col :span="5">
                            <el-input placeholder="输入玩家ID" v-model="blackList[key]" clearable></el-input>
                        </el-col>
                        <el-button type="warning" style="margin-left: 5px" @click="delBlackList(key)">删除</el-button>
                    </el-row>
                </tempate>

            </el-card>


            <el-card style="margin: 10px; position: sticky; bottom: 0;  z-index: 10;">
                <el-button type="primary" @click="saveBlackList()">保存</el-button>
            </el-card>
        </el-tab-pane>

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
            },
            addAdmin(){
                this.adminList.push("");
            },
            delAdmin(index){
                this.adminList.splice(index,1);
            },
            saveAdminList(){
                console.log(this.adminList)
                post("/player/saveAdminList", this.adminList).then((data) => {
                    if (data) {
                        this.$message({message: data.message, type: 'success'});
                    } else {
                        this.$message({message: '保存成功！', type: 'success'});
                        this.init();
                    }
                })
            },
            addBlackList(){
              this.blackList.push("");
            },
            delBlackList(index){
                this.blackList.splice(index,1);
            },
            saveBlackList(){
                console.log(this.blackList)
                post("/player/saveBlackList", this.blackList).then((data) => {
                    if (data) {
                        this.$message({message: data.message, type: 'success'});
                    } else {
                        this.$message({message: '保存成功！', type: 'success'});
                        this.init();
                    }
                })
            }
        }
    });


</script>

</html>
