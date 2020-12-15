<!DOCTYPE html>
<#import "spring.ftl" as spring>
<head xmlns="http://www.w3.org/1999/html">
    <title><@spring.message code='login.title'/></title>
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
<body class="layui-layout-login">
<div class="login-bg">
    <div class="cover"></div>
</div>
<div class="login-content">
    <h1 class="login-box-title"><i class="fa fa-fw fa-user"></i><@spring.message code='login.sign'/></h1>
    <form class="layui-form" action="/login" method="post">
        <div class="layui-form-item">
            <label class="layui-icon layui-icon-username" for="username"></label>
            <input class="layui-input" type="text" name="username" id="username" placeholder="login.username">
        </div>
        <div class="layui-form-item">
            <label class="layui-icon layui-icon-password" for="password"></label>
            <input class="layui-input" type="password" name="password" id="password" placeholder="login.password">
        </div>
<#--        <div class="layui-form-item">-->
<#--            <input type="checkbox" name="rememberMe" title="记住我" lay-skin="primary">-->
<#--        </div>-->
        <button type="submit" class="layui-btn layui-btn-fluid ajax-login"><i class="fa fa-sign-in fa-lg fa-fw"></i> <@spring.message code='login.sign'/>
        </button>
        <br><br><br><br>
        &nbsp;&nbsp;
        <a class="zhCn" href="@{/login.ftl(locale='zh_CN')}">中文</a>&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;<a class="enUs" href="@{/login.ftl(locale='en_US')}">English</a>
    </form>
    <div class="layui-layer-loading login-page-loading">
        <div class="layui-layer-content"></div>
    </div>

</div>
<#--<div>-->
<#--    <a href="https://club.tugos.cn/" target="_blank" >-->
<#--        <img style="position: absolute;bottom: 10px;left: 25%" width="800px" height="100px" src="/images/ad-tencent.jpg">-->
<#--    </a>-->
<#--</div>-->
<script src="/js/login.js" charset="utf-8"></script>
</body>
</html>
