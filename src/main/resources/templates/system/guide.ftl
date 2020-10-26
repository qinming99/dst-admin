<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>饥荒管理平台向导</title>
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

<div id="guide_page">
    <el-card>
        <div slot="header" class="clearfix">
            <span>饥荒联机版管理平台使用向导</span>
        </div>
        <el-collapse>
            <el-collapse-item title="如何获取Token令牌？" name="1">
                <div class="context">饥荒联机版服务器必须设置token，
                    是房主管理员标识，设置了token就可以对房间进行各种操作
                </div>
                <ul class="step">
                    <li>打开游戏点击账户</li>
                    <img src="/images/step1-1.png"/>
                    <li>打开账户信息页，点击游戏选项,点击饥荒联机版服务器</li>
                    <img src="/images/step1-2.png"/>
                    <li>点击添加新的服务器按钮，输入名称</li>
                    <img src="/images/step1-3.png"/>
                    <li>复制生成的token</li>
                    <img src="/images/step1-4.png"/>
                </ul>
                <div style="font-size: 20px">我的token:<span style="margin-left: 10px">pds-g^KU_qE7e8rv1^rMv54Ftk2Ur8OLSDh3KrbMU3TMi2T7BnfPOOl7OwHeQ=</span>
                </div>
            </el-collapse-item>
            <el-collapse-item title="如何获取地面设置？" name="2">
                <div class="context">该需要从现成的本地游戏服务器设置中获取</div>
                <ul class="step">
                    <li>打开游戏，创建一个本地服务器，设置需要的地面、洞穴、MOD</li>
                    <li>退出游戏，打开我的文档，进入到/Klei/DoNotStarveTogether/ 下的一个数字文件夹，
                        里面保存了刚刚创建的本地游戏服务器，Cluster_1为第一个本地服，下面有Master地面设置，Caves地面设置</li>
                    <img src="/images/step2-1.png"/>
                    <li>进入<strong>Master</strong>文件夹，下面有一个 <strong>leveldataoverride.lua</strong> 文件，用记事本打开，全选复制到饥荒管理后台的
                        房间设置的地面设置中</li>
                    <img src="/images/step2-2.png"/>
                </ul>
            </el-collapse-item>
            <el-collapse-item title="如何获取洞穴设置？" name="3">
                <div class="context">该需要从现成的本地游戏服务器设置中获取</div>
                <ul class="step">
                    <li>打开游戏，创建一个本地服务器，设置需要的地面、洞穴、MOD</li>
                    <li>退出游戏，打开我的文档，进入到/Klei/DoNotStarveTogether/ 下的一个数字文件夹，
                        里面保存了刚刚创建的本地游戏服务器，Cluster_1为第一个本地服，下面有Master地面设置，Caves地面设置</li>
                    <img src="/images/step2-1.png"/>
                    <li>进入<strong>Caves</strong>文件夹，下面有一个 <strong>leveldataoverride.lua</strong> 文件，用记事本打开，全选复制到饥荒管理后台的
                        房间设置的洞穴设置中</li>
                    <img src="/images/step2-2.png"/>
                </ul>
            </el-collapse-item>
            <el-collapse-item title="如何获取MOD设置？" name="4">
                <div class="context">该需要从现成的本地游戏服务器设置中获取</div>
                <ul class="step">
                    <li>打开游戏，创建一个本地服务器，设置需要的地面、洞穴、MOD</li>
                    <li>退出游戏，打开我的文档，进入到/Klei/DoNotStarveTogether/ 下的一个数字文件夹，
                        里面保存了刚刚创建的本地游戏服务器，Cluster_1为第一个本地服，下面有Master地面设置，Caves地面设置</li>
                    <img src="/images/step2-1.png"/>
                    <li>进入<strong>Master</strong>文件夹，下面有一个 <strong>modoverrides.lua</strong> 文件，用记事本打开，全选复制到饥荒管理后台的
                        房间设置的MOD设置中</li>
                    <img src="/images/step2-2.png"/>
                </ul>
            </el-collapse-item>
        </el-collapse>
    </el-card>

</div>

</body>

<script>

    new Vue({
        el: '#guide_page',
        data: {
            active: 0,
        },
        methods: {}
    });


</script>

</html>
