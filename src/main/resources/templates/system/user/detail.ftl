<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>用户详情</title>
    <#include "../../common/header.ftl"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
</head>
<style>

</style>
<body>

<div id="user_detail">
    <el-form style="margin-top: 10px;" label-width="100px" :model="model">
        <el-row>
            <el-col :span="12">
                <el-form-item label="昵称">
                    <el-input v-model="model.nickName"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="用户名">
                    <el-input v-model="model.userName" ></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="头像">
                    <img width="68" height="50" class="layui-side-user-avatar open-popup" data-url='system/user/detail' data-size="68,46.4"
                                                            src="/system/user/picture?p=${user.picture!}" alt="头像">

                </el-form-item>

            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="邮箱">
                    <el-input v-model="model.email" ></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="手机">
                    <el-input v-model="model.phone" ></el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-button style="margin-left: 50%" class="close-popup" @click="closeWindows()">关闭</el-button>
        <el-button @click="updateUserDetail()">保存</el-button>

    </el-form>

</div>

</body>

<script>
    new Vue({
        el: '#user_detail',
        data: {
            active: 0,
            model: {
                nickName: 11,
                userName: 1,
                headImg: 11,
                email: 11,
                phone:11,
            }
        },
        methods: {
            updateUserDetail() {
                if (this.checkIsNotNull(this.model.userName)) {
                    if (this.model.newPwd === this.model.confirmPwd) {
                        post("/system/user/setNewPwd", this.model).then((data) => {
                            if (data != undefined && data.code != 0) {
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
                    alert('用户名不能为空')
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
