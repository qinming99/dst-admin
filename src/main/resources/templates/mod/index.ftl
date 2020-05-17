<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>mod和地图配置</title>
    <#include "../header.ftl"/>


</head>
<body >

<h3> mod和地图配置</h3>


<body>
<div id="app">
    <p>{{ message }}</p>
</div>



</body>
<#include '../foot.ftl'/>



<script>
    new Vue({
        el: '#app',
        data: {
            message: 'Hello !'
        }
    })
</script>

</html>