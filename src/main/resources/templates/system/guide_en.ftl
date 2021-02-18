<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>DST Admin Guide</title>
    <#include "../common/header.ftl"/>
</head>
<style>
    .context {
        font-size: 20px;
        color: #2c52bf
    }
    .step{
        font-size: 30px
    }
    strong{
        color: red;
    }
</style>
<body>

<div id="guide_page">
    <el-card>
        <div slot="header" class="clearfix">
            <span>Don't Starve Online Management Platform User Guide</span>
        </div>
        <el-collapse>
            <el-collapse-item title="How to get token？" name="1">
                <div class="context">To set up a Dont't Starve Together online server u have to get ur token，which is the symbol of the host. With token set up ,u'll  have all operations to ur host

                </div>
                <ul class="step">
                    <li>Open Don't Starve Together and click "account"</li>
                    <img src="/images/step1-1.png"/>
                    <li>Click "game" then "Don't Starve Together server"</li>
                    <img src="/images/step1-2.png"/>
                    <li>Click "add new server"，input nickname</li>
                    <img src="/images/step1-3.png"/>
                    <li>Copy the token</li>
                    <img src="/images/step1-4.png"/>
                </ul>
                <div style="font-size: 20px">my token:<span style="margin-left: 10px">pds-g^KU_qE7e8rv1^rMv54Ftk2Ur8OLSDh3KrbMU3TMi2T7BnfPOOl7OwHeQ=</span>
                </div>
            </el-collapse-item>
            <el-collapse-item title="How to get ground setting？" name="2">
                <div class="context">get it from existing local game server setting</div>
                <ul class="step">
                    <li>open Don't Starve Together，found a local game ，set up ground cave and MOD as u need</li>
                    <li>exit Don't Starve Together，open "My document"，enter /Klei/DoNotStarveTogether/  find a file named all numbers，
                        which saves the local game server u have just founded，Cluster_1 is the first local server，open the file and find:Master, the ground setting and Caves, the cave setting  </li>
                    <img src="/images/step2-1.png"/>
                    <li>enter <strong> Master </strong>，there is a  <strong> leveldataoverride.lua </strong> ，open it using the form "notepad" ，copy them all to Don't starve together backstage management -room setting-ground setting
                    </li>
                    <img src="/images/step2-2.png"/>
                </ul>
            </el-collapse-item>
            <el-collapse-item title="How to get cave setting？" name="3">
                <div class="context">same as above</div>
                <ul class="step">
                    <li>open Don't starve together，found a local game ，set up ground、cave and MOD as u need</li>
                    <li>exit Don't Starve Together，open "My document"，enter /Klei/DoNotStarveTogether/  find a file named all numbers，
                        which saves the local game server u have just founded，Cluster_1 is the first local server，open the file and find:Master, the ground setting and Caves, the cave setting  </li>
                    <img src="/images/step2-1.png"/>
                    <li>enter<strong>Caves</strong> ，there is a  <strong>leveldataoverride.lua </strong> ，open it using the form "notepad" ，copy them all to Don't starve together backstage management -room setting-ground setting</li>
                    <img src="/images/step2-2.png"/>
                </ul>
            </el-collapse-item>
            <el-collapse-item title="How to get mod setting ？" name="4">
                <div class="context">save as above</div>
                <ul class="step">
                    <li>open Don't Starve Together，found a local game ，set up ground、cave and MOD as u need</li>
                    <li>exit Don't Starve Together，open "My document"，enter /Klei/DoNotStarveTogether/  find a file named all numbers，
                        which saves the local game server u have just founded，Cluster_1 is the first local server，open the file and find:Master, the ground setting and Caves, the cave setting  </li>
                    <img src="/images/step2-1.png"/>
                    <li>enter<strong>Master</strong>，there is a  <strong>leveldataoverride.lua</strong> ，open it using the form "notepad" ，copy them all to Don't starve together backstage management -room setting-ground setting</li>
                    <img src="/images/step2-2.png"/>
                </ul>
            </el-collapse-item>
            <el-collapse-item title="How to get player ID？" name="5">
            <div class="context">player"s ID can be used to set <strong>intendants 、blacklist</strong>
            </div>
            <ul class="step">
                <li>click <strong>system setting</strong> menu</li>
                <img src="/images/step5-1.png"/>
                <li>check Chat log ，and u can get player ID，u can also check in Run log</li>
                <img src="/images/step5-2.png"/>
                <li>player ID  is a string of characters which starts with <strong>KU</strong></li>
            </ul>
            </el-collapse-item>
            <el-collapse-item title="What's the function of timed task?" name="6">
                <div class="context">u can set two kinds of timed task：auto update、auto back up
                </div>
                <ul class="step">
                    <li><strong>auto update </strong> can auto update game when nobady in the room ，and do not affect any operation
                        （why should update？if not, players will not find the gameroom ）</li>
                    <li><strong>auto back up </strong> can save more save files，and can  regain them when necessary </li>
                </ul>
            </el-collapse-item>
            <el-collapse-item title="How to make a local save file which can be used in game server？" name="7">
                <div class="context">only "tar." save files support currently </div>
                <ul class="step">
                    <li>contents：
                        <img src="/images/step7-1.png"/>
                    </li>
                    <li>u can copy a save file made in Don't starve together management platform t</li>
                    <li>find local savd file . pls check"how to get ground setting" </li>
                    <li>copy master and caves to a new file</li>
                    <li>change the name of the file as <strong> MyDediServer </strong></li>
                    <li>condence <strong>MyDediServer </strong> to "tar."(how to make "tar" file ,please Google)</li>
                    <li>update it to sever by backing up management </li>
                    <li>regain the save file u've just updated at console desk</li>
                    <li>reset room information in room setting（It's better to set world,mod as before to avoid a game breakdown）</li>
                </ul>
            </el-collapse-item>
            </el-collapse-item>
            <el-collapse-item title="How to judge if  the server starts successfully ？" name="8">
                <div class="context">check system menu log </div>
                if it shows as the followings ,the server is okay：
                <div>
                    [00:00:23]: Telling Client our new session identifier: 7F05AE7B95B7DE17
                    <br/>
                    [00:00:23]: [Steam] SteamGameServer_Init(8766, 10998, 27016)
                    <br/>
                    [00:00:23]: [Steam] SteamGameServer_Init success
                    <br/>
                    [00:00:23]: Sim paused
                    <br/>
                    [00:00:23]: Best lobby region is aws/EU (ping 194)
                    <br/>
                    [00:00:23]: Registering master server in EU lobby
                    <br/>
                    [00:00:25]: Gameserver logged on to Steam, assigned identity steamid:90142648372606983
                    <br/>
                    [00:00:33]: Registering master server in EU lobby
                </div>
            </el-collapse-item>

            <el-collapse-item title="How to update server or Mod？" name="9">
                <div class="context">click "update game"at the console and it will restart.</div>
            </el-collapse-item>
        </el-collapse>
        </ul>
    </el-card>

</div>

</body>

<script>

    new Vue({
        el: '#guide_page',
        data: {
            active: 0,
        },
        methods: {}
    });


</script>

</html>
