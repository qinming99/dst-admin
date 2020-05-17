

<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>设置</title>
    <#include "../header.ftl"/>


</head>
<body>


<div>

    服务器状态：
    <button type="button" class="btn btn-primary">原始按钮</button>

    <button type="button" class="btn btn-warning">警告按钮</button>



</div>




<div id="app">
    <input type="text" v-model.trim="msg" @keyup.enter="echo">
    <button @click="echo">发送</button>

    <div>
        <ol>
            <li v-for="s in record">
                {{ s }}
            </li>
        </ol>
    </div>

</div>


</body>



<#include '../foot.ftl'/>



<script>
    /* global Vue, WebSocket */
    var ws = null

    var myapp = new Vue({
        el: '#app',
        data: {
            msg: '',
            record:[]
        },
        methods: {
            echo: function () {
                if (!this.msg) return
                console.log('WebSocket发送消息: ' + this.msg)
                ws.send(this.msg)
            },
            initWebSocket: function (params) {
                // ws = new WebSocket('wss://echo.websocket.org/')
                ws = new WebSocket("ws://127.0.0.1:8081/websocket");
                ws.onopen = function (e) {
                    console.log('WebSocket已经打开: ')
                    console.log(e)
                }
                ws.onmessage = function (e) {
                    // console.log('WebSocket收到消息: ' + e.data)
                    // let that = this
                    // debugger
                    // console.log(that)
                    // that.record.push(e.data);
                    // console.log(myapp.record)
                    myapp.record.push(e.data)
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