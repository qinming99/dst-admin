<!DOCTYPE html>
<head>
    <#import "spring.ftl" as spring>
    <title><@spring.message code="dst.admin.title"/></title>
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
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <!-- 导航栏 -->
    <div class="layui-header">
        <a href="#" class="layui-logo">
            <span class="layui-logo-mini"><@spring.message code="main.logo.mini"/></span>
            <span class="layui-logo-lg"><@spring.message code="main.logo.lg"/></span>
        </a>
        <a class="side-toggle layui-layout-left" href="#">
            <i class="layui-icon layui-icon-shrink-right"></i>
            <i class="layui-icon layui-icon-spread-left"></i>
        </a>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a class="timo-screen-full" href="#">
                    <i class="fa layui-icon layui-icon-screen-full"></i>
                </a>
            </li>
            <li class="layui-nav-item">
                <a class="timo-header-lang"><@spring.message code="main.lang"/></a>
                <div class="layui-nav-child">
                    <div class="timo-nav-child-box">
                        <div style="margin-top: 20px;text-align: center">
                            <a style="font-size: 1em;color: #444444" class="changeLang" href="javascript:void(0);">中文</a>
                            <a style="font-size: 1em;color: #444444" class="changeLang" href="javascript:void(0);">English</a><br/>
                        </div>
                    </div>
                </div>
            </li>

            <li class="layui-nav-item timo-nav-user">
                <a class="timo-header-nickname">${user.nickname!}</a>
                <div class="layui-nav-child">
                    <div class="timo-nav-child-box">
<#--                        <div><a class="open-popup" data-title="<@spring.message code="main.logo.user.info"/>"-->
<#--                                data-url='system/user/detail' data-size="680,464">-->
<#--                                <i class="fa fa-user-o"></i><@spring.message code="main.logo.user.info"/></a>-->
<#--                        </div>-->
                        <div><a class="open-popup" data-title="<@spring.message code="main.logo.editPwd"/>"
                                data-url='system/user/updatePwd' data-size="456,296">
                                <i class="fa fa-lock" style="font-size:17px;width:12px;"></i><@spring.message code="main.logo.editPwd"/></a>
                        </div>
                        <div><a href="/logout"><i class="fa fa-power-off"></i><@spring.message code="main.logo.logout"/></a>
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
                <img class="layui-side-user-avatar" data-size="680,464"
                     src="/system/user/picture?p=${user.picture!}" alt="头像">
                <div>
                    <p class="layui-side-user-name">${user.nickname!}</p>
                    <p class="layui-side-user-designation"><@spring.message code="main.logo.status"/></p>
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
<script src="/js/plugins/jquery-3.3.1.min.js"></script>
<script>

    $(".changeLang").on("click", function () {
        switch ($(this).text()) {
            case "中文": {
                window.location.href = "/?_lang=zh_CN";
                break;
            }
            case "English": {
                window.location.href = "/?_lang=en_US";
                break;
            }
        }
    })

</script>
</body>
</html>
