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
            <h5>定时任务</h5>
        </el-tab-pane>
        <el-tab-pane label="地面程序运行日志" name="second">
            <el-card class="card">
                <el-row>
                    <el-col :span="5">
                        <el-input placeholder="请输入需要拉取的日志行数" v-model="num1" type="number" clearable></el-input>
                    </el-col>
                    <el-button @click="getDstLog(0,num1)">拉取</el-button>
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
                    <el-button @click="getDstLog(1,num2)">拉取</el-button>
                </el-row>
            </el-card>
            <el-card class="card">
                <ul>
                    <li v-for="log in cavesLog">{{ log }}</li>
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
            masterLog: [],
            cavesLog: []
        },
        methods: {
            getDstLog(type, rowNum) {
                let params = {type: type, rowNum: rowNum}
                get("/system/getDstLog", params).then((data) => {
                    if (type === 1) {
                        this.cavesLog = data;
                    } else {
                        this.masterLog = data;
                    }
                })
            },

        }
    });


</script>

</html>
