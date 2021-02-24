<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <#import "spring.ftl" as spring>
    <title><@spring.message code="user.detail.title"/></title>
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <meta charset="utf-8">
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

<div id="update_pwd">
    <el-form style="margin-top: 10px;" label-width="150px" :model="model">
        <el-row>
            <el-col :span="18">
                <el-form-item label="<@spring.message code="user.update.password.old"/>">
                    <el-input v-model="model.oldPwd" show-password placeholder="<@spring.message code="tips.user.update.password"/>"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="18">
                <el-form-item label="<@spring.message code="user.update.password.new"/>">
                    <el-input v-model="model.newPwd" show-password placeholder="<@spring.message code="tips.user.update.password"/>"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="18">
                <el-form-item label="<@spring.message code="user.update.password.confirm"/>">
                    <el-input v-model="model.confirmPwd" show-password placeholder="<@spring.message code="tips.user.update.password"/>"></el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-button style="margin-left: 50%" class="close-popup"><@spring.message code="home.pane1.card1.dst.active.off"/></el-button>
        <el-button @click="updatePwd()"><@spring.message code="home.pane1.card1.dst.active.save"/></el-button>

    </el-form>

</div>


</body>

<script>

    new Vue({
        el: '#update_pwd',
        data: {
            active: 0,
            model: {
                oldPwd: null,
                newPwd: null,
                confirmPwd: null,
            }
        },
        methods: {
            updatePwd() {
                if (this.checkIsNotNull(this.model.oldPwd) &&
                    this.checkIsNotNull(this.model.newPwd) &&
                    this.checkIsNotNull(this.model.confirmPwd)) {
                    if (this.model.newPwd === this.model.confirmPwd) {
                        post("/system/user/setNewPwd", this.model).then((data) => {
                            if (data) {
                                alert(data.message)
                            }else {
                                alert('修改成功，请重新登录')
                                this.closeWindows();
                                window.location.href="/logout"
                            }
                        })
                    } else {
                        alert('两次密码不一致')
                    }
                } else {
                    alert('密码不能为空')
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
