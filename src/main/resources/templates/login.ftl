<!DOCTYPE html>
<head>
    <title>饥荒管理后台登录</title>
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
<body class="layui-layout-login">
<div class="login-bg">
    <div class="cover"></div>
</div>
<div class="login-content">
    <h1 class="login-box-title"><i class="fa fa-fw fa-user"></i>登录</h1>
    <form class="layui-form" action="/login" method="post">
        <div class="layui-form-item">
            <label class="layui-icon layui-icon-username" for="username"></label>
            <input class="layui-input" type="text" name="username" id="username" placeholder="用户名">
        </div>
        <div class="layui-form-item">
            <label class="layui-icon layui-icon-password" for="password"></label>
            <input class="layui-input" type="password" name="password" id="password" placeholder="密码">
        </div>
        <div class="layui-form-item">
            <input type="checkbox" name="rememberMe" title="记住我" lay-skin="primary">
        </div>
        <button type="submit" class="layui-btn layui-btn-fluid ajax-login"><i class="fa fa-sign-in fa-lg fa-fw"></i> 登录
        </button>
    </form>
    <div class="layui-layer-loading login-page-loading">
        <div class="layui-layer-content"></div>
    </div>
</div>
<script src="/js/login.js" charset="utf-8"></script>
</body>
</html>
