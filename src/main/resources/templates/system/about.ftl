<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>关于</title>
    <#include "../common/header.ftl"/>
</head>
<style>
    .context {
        font-size: 20px;
        color: #2c52bf
    }
    .step{
        font-size: 30px
    }
    strong{
        color: red;
    }
</style>
<body>

<div id="about_page">
    <el-card>
        <div slot="header" class="clearfix">
            <span>关于dst-admin</span>
        </div>
        <h3>当前版本号-V1.3.1</h3>
        <h3>Copyright © 2020-2021 Qinming. All rights reserved.</h3>
        <h3>服务器搭建教程：
            <el-link type="primary" target="_blank"
                     href="https://blog.csdn.net/qq_33505611/article/details/109107216">
                https://blog.csdn.net/qq_33505611/article/details/109107216
            </el-link>
        </h3>
        <h3>GitHub地址：
            <el-link type="primary" target="_blank" href="https://github.com/qinming99/dst-admin">
                https://github.com/qinming99/dst-admin
            </el-link>
        </h3>
        <h3>捐助：</h3>
            <span>让作者持续提供更好的功能与服务。</span>
        <br/>
        <img width="500px" src="/images/code.png"/>
    </el-card>

</div>

</body>

<script>

    new Vue({
        el: '#about_page',
        data: {
            active: 0,
        },
        methods: {}
    });


</script>

</html>
