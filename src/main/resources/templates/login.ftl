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
    <link rel="stylesheet" href="/css/ele/index.css"/>
    <script src="/js/vue.js" defer="defer"></script>
    <script src="/css/ele/index.js" defer="defer"></script>
</head>
<body class="layui-layout-login">
<div class="login-bg">
    <div class="cover"></div>
</div>
<div class="login-content">
    <h1 class="login-box-title"><i class="fa fa-fw fa-user"></i><@spring.message code="login.login.name"/></h1>
    <form class="layui-form" action="/login" method="post">
        <div class="layui-form-item">
            <label class="layui-icon layui-icon-username" for="username"></label>
            <input class="layui-input" type="text" name="username" id="username"
                   placeholder="<@spring.message code="login.login.userName"/>">
        </div>
        <div class="layui-form-item">
            <label class="layui-icon layui-icon-password" for="password"></label>
            <input class="layui-input" type="password" name="password" id="password"
                   placeholder="<@spring.message code="login.login.password"/>">
        </div>
        <button type="submit" class="layui-btn layui-btn-fluid ajax-login"><i
                    class="fa fa-sign-in fa-lg fa-fw"></i> <@spring.message code="login.login.name"/>
        </button>
    </form>
    <div style="margin-top: 20px;text-align: center">
        <a style="font-size: 1.2em;color: #1E9FFF" class="changeLang" href="javascript:void(0);">中文</a>
        <a style="font-size: 1.2em;color: #1E9FFF;margin-left: 10px" class="changeLang" href="javascript:void(0);">English</a><br/>
    </div>
    <div class="layui-layer-loading login-page-loading">
        <div class="layui-layer-content"></div>
    </div>
</div>
<script src="/js/plugins/jquery-3.3.1.min.js"></script>
<script>
    $(".changeLang").on("click", function () {
        switch ($(this).text()) {
            case "中文": {
                window.location.href = "login?_lang=zh_CN";
                break;
            }
            case "English": {
                window.location.href = "login?_lang=en_US";
                break;
            }
        }
    })

    if (window.top !== window.self) {
        window.top.location = window.location
    }
    ;
    layui.use(['element'], function () {
        let $ = layui.jquery;
        $(document).on('click', '.captcha-img', function () {
            let src = this.src.split("?")[0];
            this.src = src + "?" + Math.random();
        });
        $(document).on('click', '.ajax-login', function (e) {
            e.preventDefault();
            let form = $(this).parents("form");
            let url = form.attr("action");
            let serializeArray = form.serializeArray();
            $.post(url, serializeArray, function (result) {
                if (result.code !== 0) {
                    $('.captcha-img').click();
                }
                loginMain(result);
            });
        });
        $('.layui-layer-loading').hide();
    });

    function loginMain(result) {
        if (result.code === 0) {
            layer.msg(result.message, {offset: '15px', time: 3000, icon: 1});
            setTimeout(function () {
                if (result.data === 'submit[refresh]') {
                    parent.location.reload();
                    return;
                }
                if (result.data != null && result.data.url != null) {
                    window.location.href = result.data.url;
                } else {
                    window.location.reload();
                }
            }, 2000);
        } else {
            layer.msg(result.message, {offset: '15px', time: 3000, icon: 2});
        }
    };


</script>
</body>
</html>
