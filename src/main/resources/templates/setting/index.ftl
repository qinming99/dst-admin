<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>游戏配置</title>
    <#include "../header.ftl"/>

    <style>
        .shortselect{
            background:#fafdfe;
            height:28px;
            width:180px;
            line-height:28px;
            border:1px solid #9bc0dd;
            -moz-border-radius:2px;
            -webkit-border-radius:2px;
            border-radius:2px;
        }

    </style>


</head>
<body>

<body>
<div class="opt-wrapper" id="game_config">
    <div class="opt-content">
        <div class="form-split"></div>
        <h4 class="form-title">游戏配置</h4>
        <div class="form-split"></div>
        <form id="form" class="form-horizontal" >
            <div class="form-group">

                <div class="form-group">
                    <label class="col-sm-2 control-label">服务器游戏风格</label>
                    <div class="col-sm-10">
                        <div class="radio  radio-primary col-sm-3">
                            <input value='social' name="clusterIntention" id="clusterIntention1" checked="true" type="radio">
                            <label for="clusterIntention1">社交</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">房间名称</label>
                    <div class="col-sm-5">
                        <input class="form-control" id="clusterName" name="clusterName" maxlength="30" value="${config.clusterName!}" placeholder="房间名称" >
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-5">
                        <input class="form-control" id="clusterDescription" name="clusterDescription" value="${config.clusterDescription!}" maxlength="100"  placeholder="描述"  >
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-sm-2 control-label">游戏模式</label>
                    <div class="col-sm-5">
                        <div class="radio  radio-primary col-sm-3">
                            <input value='survival' name="gameMode"  checked="true" id="models1" type="radio">
                            <label for="models1">生存</label>
                        </div>
                        <div class="radio  radio-primary col-sm-3">
                            <input value='wilderness' name="gameMode" id="models2" type="radio">
                            <label for="models2">荒野</label>
                        </div>
                        <div class="radio  radio-primeary col-sm-3">
                            <input value='endless' name="gameMode" id="models3" type="radio">
                            <label for="models3">无尽</label>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">PVP</label>
                    <div class="col-sm-10">
                        <div class="radio  radio-primary col-sm-3">
                            <input value='false' name="pvp" id="pvp1" checked="true" type="radio">
                            <label for="pvp1">关</label>
                        </div>
                        <div class="radio  radio-primary col-sm-3">
                            <input value='true' name="pvp" id="pvp2" type="radio">
                            <label for="pvp2">开</label>
                        </div>

                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">玩家数量</label>
                    <div class="col-sm-10">
                        <select id="maxPlayers" class="shortselect">
                            <option value="1">   1   </option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option selected="true" value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                            <option value="13">13</option>
                            <option value="14">14</option>
                            <option value="15">15</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">房间密码</label>
                    <div class="col-sm-5">
                        <input class="form-control" value="${config.clusterPassword!}" maxlength="10" name="clusterPassword" id="clusterPassword" placeholder="房间密码" >
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-sm-2 control-label">令牌</label>
                    <div class="col-sm-5">
                        <input class="form-control" maxlength="200" id="token" value="${config.token!}" placeholder="管理员令牌" >

                </div>

            </div>
        </form>
        <div class="form-split"></div>
        <a id="btnSave" class="btn btn-primary" onclick="saveConfig()" style="margin-left: 200px;" href="javascript:;" role="button"><i class="fa fa-save"></i> 生成游戏配置 </a>
    </div>
</div>


</body>
<#include '../foot.ftl'/>

<script>

    function saveConfig() {
        let clusterIntention = $("input[name='clusterIntention']:checked").val();
        let clusterName = $('#clusterName').val();
        let clusterDescription = $('#clusterDescription').val();
        let gameMode = $("input[name='gameMode']:checked").val();
        let pvp = $("input[name='pvp']:checked").val();
        let maxPlayers = $("#maxPlayers").val();
        let clusterPassword = $("#clusterPassword").val();
        let token = $("#token").val();
        if (!notNull(clusterName)){
            alert("房间名称不能为空");
            return;
        }

        if (!notNull(token)){
            alert("令牌不能为空");
            return;
        }
        let obj = {clusterIntention:clusterIntention,clusterName:clusterName,
            clusterDescription:clusterDescription,gameMode:gameMode,pvp:pvp,
            maxPlayers:maxPlayers,clusterPassword:clusterPassword,token:token
        };
        console.log(obj)
        ajax({
            method: 'post',
            url: '/setting/saveConfig',
            data:obj,
            success: function (response) {
                location.reload();
                alert("保存成功，重启游戏生效")
            }
        });
    }

    function notNull(val) {
        if (val == null || val == '' || val == undefined){
            return false;
        } else {
            return true;
        }
    }

</script>

</html>