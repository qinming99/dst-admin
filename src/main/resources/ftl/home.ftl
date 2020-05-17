<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
    <#include "header.ftl"/>

    <style>

        .mt-15 {margin-top: 15px;}
        .mb-15 {margin-bottom: 15px;}
    </style>

</head>
<body>

<h3></h3>


<body>




<div class="container" id="home">
    <div class="row">

        <span class="col-sm-2">
             饥荒状态：
        </span>

        <label class="col-sm-2">
            地面
        <#if masterStatus == true >
            <button type="button" class="btn btn-success btn-circle btn-lg">运行中</button>
        <#else >
            <button type="button" class="btn btn-danger btn-circle btn-lg">未运行</button>
        </#if>
        </label>

        <label class="col-sm-2">

            洞穴
        <#if cavesStatus == true >
                <button type="button" class="btn btn-success btn-circle btn-lg">运行中</button>
        <#else >
                <button type="button" class="btn btn-danger btn-circle btn-lg">未运行</button>
        </#if>
        </label>

    </div>

    <div class="row mt-15 mb-15">
        <span>启动：
            <button type="button" @click="start('0')" class="btn btn-success btn-circle btn-lg">启动地面和洞穴</button>
            <button type="button" @click="start('1')" class="btn btn-success btn-circle btn-lg">启动地面</button>
            <button type="button" @click="start('2')" class="btn btn-success btn-circle btn-lg">启动洞穴</button>
        </span>

    </div>

    <div class="row mt-15 mb-15">
           <span>停止：
            <button type="button" @click="stop('0')" class="btn btn-success btn-circle btn-lg">停止地面和洞穴</button>
            <button type="button" @click="stop('1')" class="btn btn-success btn-circle btn-lg">停止地面</button>
            <button type="button" @click="stop('2')" class="btn btn-success btn-circle btn-lg">停止洞穴</button>
        </span>

    </div>







<#--<button type="button" class="btn btn-primary">原始按钮</button>-->

<#--<button type="button" class="btn btn-warning">警告按钮</button>-->


<#--<button type="button" class="btn btn-default btn-circle btn-lg"><i class="glyphicon glyphicon-ok"></i></button>-->
<#--<button type="button" class="btn btn-primary btn-circle btn-lg"><i class="glyphicon glyphicon-list"></i></button>-->
<#--<button type="button" class="btn btn-success btn-circle btn-lg"><i class="glyphicon glyphicon-link"></i></button>-->
<#--<button type="button" class="btn btn-info btn-circle btn-lg"><i class="glyphicon glyphicon-ok"></i></button>-->
<#--<button type="button" class="btn btn-warning btn-circle btn-lg"><i class="glyphicon glyphicon-remove"></i></button>-->
<#--<button type="button" class="btn btn-danger btn-circle btn-lg"><i class="glyphicon glyphicon-heart"></i></button>-->


</div>


</body>
<#include 'foot.ftl'/>

<script>

    let home = new Vue({
        el: '#home',
        data: {
            msg: '',
        },
        methods: {
            start: function (type) {
                console.log(type)
                ajax({
                    method: 'GET',
                    url: '/start',
                    data: {
                        type: type,
                    },
                    success: function (response) {
                        location.reload();
                        console.log(response);
                    }
                });

            },
            stop: function (type) {
                console.log(type)
                ajax({
                    method: 'GET',
                    url: '/stop',
                    data: {
                        type: type,
                    },
                    success: function (response) {
                        location.reload();
                        console.log(response);
                    }
                });

            }
        }
    });

</script>


</html>