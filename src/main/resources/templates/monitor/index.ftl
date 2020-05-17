

<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>设置</title>
    <#include "../header.ftl"/>


</head>
<body>



<div id="app" >

    <div>
        <h3>CPU:</h3>
        <h3>核心数：{{cpu.cpuNum}}</h3>
        <h3>CPU当前空闲率：{{cpu.free}}%</h3>
    </div>

    <div>
        <h3>内存:</h3>
        <h3>内存总量:{{mem.total}}GB</h3>
        <h3>剩余内存:{{mem.free}}GB</h3>
    </div>

</div>


</body>



<#include '../foot.ftl'/>



<script>
    var ws = null

    var myapp = new Vue({
        el: '#app',
        data: {
            cpu:{},
            mem:{}
        },
        methods: {
            echo: function () {
                if (!this.msg) return
                console.log('WebSocket发送消息: ' + this.msg)
                ws.send(this.msg)
            },
            initWebSocket: function (params) {
                let basePath = "ws://" + window.location.host +"/websocket";
                console.log(basePath);
                ws = new WebSocket(basePath);
                ws.onopen = function (e) {
                    console.log('WebSocket已经打开: ')
                    console.log(e)
                }
                ws.onmessage = function (data) {
                   console.log(data.data)
                    let parse = JSON.parse(data.data);
                    myapp.cpu = parse.cpu;
                   myapp.mem = parse.mem;
                   console.log(myapp.cpu)
                }
                ws.onclose = function (e) {
                    console.log('WebSocket关闭: ')
                    console.log(e)
                }
                ws.onerror = function (e) {
                    console.log('WebSocket发生错误: ')
                    console.log(e)
                }
            }
        },
        created: function () {
            this.initWebSocket()
        }
    })
</script>

</html>