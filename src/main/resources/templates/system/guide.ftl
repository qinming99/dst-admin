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
            <el-collapse-item title="9折腾讯云服务器出租!!!!" name="0">
                <ul class="step">
                    <li><strong>9折</strong>腾讯云服务器租赁，是在官网定价的折扣上<strong>折上折</strong>，
                        <br/>例如11期间官方1核2GB需要88元/年，我这只需要80元/年，每人可以注册三个新用户，<strong>续费也是9折</strong>
                        需要可以联系Q：2339732369<br/>你的支持是我前进的最好动力</li>
                    <img width="800px" height="100px" src="/images/ad-tencent.jpg"/>
                </ul>
            </el-collapse-item>
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
            <el-collapse-item title="如何获取玩家ID？" name="5">
                <div class="context">玩家ID可以用于设置<strong>管理员、黑名单</strong>
                </div>
                <ul class="step">
                    <li>点击<strong>系统设置</strong>菜单</li>
                    <img src="/images/step5-1.png"/>
                    <li>查看聊天日志，这里可以拿到玩家ID，也可以从运行日志中查找</li>
                    <img src="/images/step5-2.png"/>
                    <li>玩家ID是以<strong>KU</strong>打头的一串字符串</li>
                </ul>
            </el-collapse-item>
            <el-collapse-item title="定时任务的作用？" name="6">
                <div class="context">可以设置两种定时任务：自动更新、自动备份
                </div>
                <ul class="step">
                    <li><strong>自动更新</strong>可以达到无人守候时自动更新游戏，不影响游戏更新之后未及时更新服务器，玩家找不到房间
                        （为什么要更新？服务器不更新搜索不到房间）</li>
                    <li><strong>自动备份</strong>可以在留存跟多的存档，必要时恢复存档</li>
                </ul>
            </el-collapse-item>
            <el-collapse-item title="如何将本地存档制作成服务器能用的存档？" name="7">
                <div class="context">当前平台只支持tar格式的存档文件</div>
                <ul class="step">
                    <li>内容：
                        <img src="/images/step7-1.png"/>
                    </li>
                    <li>可以下载一个饥荒管理平台生成的存档备份对照来制作</li>
                    <li>找到本地的存档，本地的存档存储位置请查看"如何获取地面设置"向导 </li>
                    <li>将地面master文件夹和洞穴caves文件夹复制到新的文件夹中</li>
                    <li>将文件夹名称改为<strong>MyDediServer</strong></li>
                    <li>在将<strong>MyDediServer</strong>文件夹压缩为<strong>tar格式</strong>(如何制作tar格式的压缩包请百度)</li>
                    <li>通过备份管理上传到服务器</li>
                    <li>在控制台恢复刚刚上传的存档</li>
                    <li>在房间设置中重新设置房间信息（世界设置，mod设置最好照旧，避免崩档）</li>
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
