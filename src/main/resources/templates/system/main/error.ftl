<!DOCTYPE html>
<head>
    <title>饥荒后台</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="description" content="饥荒联机版服务器管理系统"/>
    <meta name="keywords" content="饥荒联机版服务器管理系统"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/css/plugins/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="/lib/layui-v2.3.0/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/main.css" media="all">
    <style>
        .page-error {
            display: -webkit-box;
            display: -ms-flexbox;
            display: flex;
            -webkit-box-align: center;
            -ms-flex-align: center;
            align-items: center;
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            justify-content: center;
            -webkit-box-orient: vertical;
            -webkit-box-direction: normal;
            -ms-flex-direction: column;
            flex-direction: column;
            min-height: calc(100vh - 110px);
            margin-bottom: 0;
        }
    </style>
</head>
<body>
<div class="page-error" style="color: #3c8dbc">
    <div style="font-size: 120px" >${statusCode!'500'}</div>
    <div style="font-size: 24px" >${msg!'SYSTEM ERROR'}</div>
</div>
</body>
