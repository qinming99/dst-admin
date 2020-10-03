<!DOCTYPE html>
<head>
    <title>饥荒后台主页</title>
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/mo-favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/css/plugins/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="/lib/layui-v2.3.0/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/main.css" media="all">
    <script src="/lib/layui-v2.3.0/layui.js" charset="utf-8"></script>
    <script src="/js/main.js" charset="utf-8"></script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <!-- 导航栏 -->
    <div class="layui-header">
        <a href="#" class="layui-logo">
            <span class="layui-logo-mini">饥荒</span>
            <span class="layui-logo-lg">饥荒 后台</span>
        </a>
        <a class="side-toggle layui-layout-left" href="#">
            <i class="layui-icon layui-icon-shrink-right"></i>
            <i class="layui-icon layui-icon-spread-left"></i>
        </a>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="#">
                    <i class="fa fa-bell-o fa-lg"></i>
                </a>
            </li>
            <li class="layui-nav-item">
                <a class="timo-screen-full" href="#">
                    <i class="fa layui-icon layui-icon-screen-full"></i>
                </a>
            </li>
            <li class="layui-nav-item timo-nav-user">
                <a class="timo-header-nickname">${user.nickname!}</a>
                <div class="layui-nav-child">
                    <div class="timo-nav-child-box">
                        <div><a class="open-popup" data-title="个人信息"
                                attr="data-url=/userInfo" data-size="680,464">
                                <i class="fa fa-user-o"></i>个人信息</a>
                        </div>
                        <div><a class="open-popup" data-title="修改密码"
                                attr="data-url=/editPwd" data-size="456,296">
                                <i class="fa fa-lock" style="font-size:17px;width:12px;"></i>修改密码</a>
                        </div>
                        <div><a href="/logout"><i class="fa fa-power-off"></i>退出登录</a>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>

    <!-- 侧边栏 -->
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <div class="layui-side-user">
                <img class="layui-side-user-avatar open-popup" attr="data-url=/userInfo" data-size="680,464"
                     src="/system/user/picture?p=${user.picture!}" alt="头像">
                <div>
                    <p class="layui-side-user-name">${user.nickname!}</p>
                    <p class="layui-side-user-designation">在线</p>
                </div>
            </div>
            <!-- 导航区域 -->
            <ul class="layui-nav layui-nav-tree" lay-filter="layui-nav-side">
                <#if treeMenu?exists>
                    <#list treeMenu?keys as key>
                        <li class="layui-nav-item">
                            <a href="javascript:;" lay-url="${treeMenu[key].url}">
                                <i class="${treeMenu[key].icon}"></i>
                                <span class="layui-nav-title">${treeMenu[key].title}</span>
                            </a>
                            <#if treeMenu[key].children?exists>
                                <#list treeMenu[key].children?keys as child>
                                    <dl class="layui-nav-child">
                                        <dd>
                                            <a href="javascript:;" lay-url="${treeMenu[key].children[child].url}">
                                                <span class="layui-nav-title">${treeMenu[key].children[child].title}</span></a>
                                        </dd>
                                    </dl>
                                </#list>
                            </#if>
                        </li>
                    </#list>
                </#if>
            </ul>
        </div>
    </div>
    <!-- 主体区域 -->
    <div class="layui-body layui-tab layui-tab-brief" lay-allowclose="true" lay-filter="iframe-tabs">
        <!-- 标签栏 -->
        <ul class="layui-tab-title"></ul>
        <!-- 内容区域 -->
        <div class="layui-tab-content"></div>
    </div>
</div>
<!--引入vue-->
<script src="/js/vue.js" charset="utf-8"></script>
<script>
    // window.main = 111;
</script>
</body>
</html>
