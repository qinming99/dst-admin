<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>修改密码</title>
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
    <el-form style="margin-top: 10px;" label-width="80px" :model="model">
        <el-row>
            <el-col :span="18">
                <el-form-item label="旧密码">
                    <el-input v-model="model.oldPwd" show-password placeholder="请输入密码"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="18">
                <el-form-item label="新密码">
                    <el-input v-model="model.newPwd" show-password placeholder="请输入密码"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="18">
                <el-form-item label="确认密码">
                    <el-input v-model="model.confirmPwd" show-password placeholder="请输入密码"></el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-button style="margin-left: 50%" class="close-popup">关闭</el-button>
        <el-button @click="updatePwd()">保存</el-button>

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
                            if (data != undefined && data.code != 0) {
                                alert(data.message)
                            }else {
                                alert('修改成功，请重新登录')
                                this.closeWindows();
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
