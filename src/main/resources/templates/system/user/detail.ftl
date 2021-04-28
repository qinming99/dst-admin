<!DOCTYPE html>
<head>
    <#import "spring.ftl" as spring>
    <meta charset="utf-8">
    <title><@spring.message code="user.detail.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/css/plugins/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="/lib/layui-v2.3.0/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/main.css" media="all">
    <script src="/lib/layui-v2.3.0/layui.js" charset="utf-8"></script>
    <script src="/js/main.js" charset="utf-8"></script>
    <#include "../../common/header.ftl"/>
</head>
<style>

</style>
<body>

<div id="user_detail">
    <el-form style="margin-top: 10px;" label-width="80px" :model="model">
        <el-row>
            <el-col :span="15">
                <el-form-item label="<@spring.message code="user.name"/>" >
                    <el-input v-model="model.username" required="true">${user.username!}</el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="15">
                <el-form-item label="<@spring.message code="user.nickname"/>">
                    <el-input v-model="model.nickname" >${user.nickname!}</el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="15">
                <el-form-item label="<@spring.message code="user.picture"/>">
                    <el-input v-model="model.picture">
<#--                        <img class="layui-side-user-avatar open-popup" data-url='system/user/detail' data-size="680,464"-->
<#--                                                           src="/system/user/picture?p=${user.picture!}" alt="头像">-->
                    </el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-button style="margin-left: 12%" @click="closeWindows()"><@spring.message code="home.pane1.card1.dst.active.off"/></el-button>
        &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
        &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
        <el-button  style="margin-bottom: 22%" @click="updateDetail()"><@spring.message code="home.pane1.card1.dst.active.save"/></el-button>

    </el-form>

</div>

</body>

<script>

    new Vue({
        el: '#user_detail',
        data: {
            active: 0,
            model: {
                username: null,
                nickname: null,
                picture: null,
            }
        },
        methods: {
            updateDetail() {
                if (this.checkIsNotNull(this.model.username) &&
                    this.checkIsNotNull(this.model.nickname) &&
                    this.checkIsNotNull(this.model.picture)) {
                        post("/system/user/setNewUserDetail", this.model).then((data) => {
                            if (data) {
                                alert(data.message)
                            }else {
                                alert('修改成功,请使用新的用户名登录:' + this.model.username)
                                this.closeWindows();
                            }
                        })
                } else {
                    alert('信息不能为空')
                }
            },
            closeWindows() {
                let index = parent.layer.getFrameIndex(window.name); //获取当前窗口的name
                parent.layer.close(index);
            },
            checkIsNotNull(val) {
                if (val != null && val != undefined && val != '') {
                    return true;
                } else {
                    return false;
                }
            }
        }
    });


</script>

</html>
