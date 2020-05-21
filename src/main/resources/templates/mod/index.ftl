<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>mod和地图配置</title>
    <#include "../header.ftl"/>


</head>
<body >
<div id="mod_index">


<div >
    <h3> mod配置</h3>
    <textarea style="width: 90%;height: 400px" id="modData">
    ${modData!}
    </textarea>
    <button type="button" @click="saveMod" class="btn btn-primary btn-circle btn-lg">保存mod配置</button>
</div>

<div>
    <h3> 地面配置</h3>
    <textarea style="width: 90%;height: 400px" id="masterMapData">
    ${masterMapData!}
    </textarea>
    <button type="button" @click="saveMaster" class="btn btn-primary btn-circle btn-lg">保存地面配置</button>
</div>


<div>
    <h3> 洞穴配置</h3>
    <textarea style="width: 90%;height: 400px" id="cavesMapData">
    ${cavesMapData!}
    </textarea>
    <button type="button" @click="saveCaves" class="btn btn-primary btn-circle btn-lg">保存洞穴配置</button>
</div>
</div>

<body>



</body>
<#include '../foot.ftl'/>



<script>
    new Vue({
        el: '#mod_index',
        data: {
            message: 'Hello !'
        },
        methods: {
            saveMod: function () {
                let data = $("#modData").val();
                console.log(data)
                ajax({
                    method: 'post',
                    url: '/mod/saveMod',
                    data: {
                        data: data,
                    },
                    success: function (response) {
                        location.reload();
                    }
                });

            },
            saveMaster:function () {
                let data = $("#masterMapData").val();
                console.log(data)
                ajax({
                    method: 'post',
                    url: '/mod/saveMaster',
                    data: {
                        data: data,
                    },
                    success: function (response) {
                        location.reload();
                    }
                });
            },
            saveCaves:function () {
                let data = $("#cavesMapData").val();
                console.log(data)
                ajax({
                    method: 'post',
                    url: '/mod/saveCaves',
                    data: {
                        data: data,
                    },
                    success: function (response) {
                        location.reload();
                    }
                });
            }
        }
    })
</script>

</html>