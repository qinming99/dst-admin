<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>存档管理</title>
    <#include "../header.ftl"/>
    <style>
        th {
            text-align: center;
            color: white;
            font-size: 30px;
        }

        td {
            text-align: center;
            font-size: 25px;
        }
    </style>


</head>
<body>
<div id="backup_table">
    <table class="table table-striped" style="width: 95%;text-align: center">
        <thead style="background: #1b6d85;">
        <tr>
            <th>文件名称</th>
            <th>大小（MB）</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
                    <#if fileList??>
                       <#list fileList as file>
                           <tr>
                               <td>${file.fileName}</td>
                               <td>${file.fileSize} MB</td>
                               <td>${file.createTime}</td>
                               <td>
                                   <button @click="del('${file.fileName}')" class="btn btn-danger btn-circle btn-lg">
                                       删除
                                   </button>
                               </td>
                           </tr>
                       </#list>
                    <#else >
                        <tr>
                            <td colspan="4">暂无数据</td>
                        </tr>
                    </#if>
        </tbody>
    </table>
</div>
</body>


<#include '../foot.ftl'/>

<script>
    new Vue({
        el: '#backup_table',
        data: {
            message: 'Hello '
        },
        methods: {
            del: function (fileName) {
                console.log(fileName)
                ajax({
                    method: 'GET',
                    url: '/backup/del',
                    data: {
                        fileName: fileName,
                    },
                    success: function (response) {
                        location.reload();
                        console.log(response);
                    }
                });

            },
        }
    })
</script>

</html>