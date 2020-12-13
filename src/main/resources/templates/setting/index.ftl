<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>配置页</title>
    <#include "../common/header.ftl"/>
</head>
<body>

<div id="setting_index_app">
    <div style="width: 20%;float:left;">
        <el-card>
            <div style="height: 500px">
                <el-steps direction="vertical" :active="active">
                    <el-step title="房间基本信息"></el-step>
                    <el-step title="地面世界设置"></el-step>
                    <el-step title="洞穴世界设置"></el-step>
                    <el-step title="mod设置"></el-step>
                    <el-step title="完成"></el-step>
                </el-steps>
            </div>
        </el-card>
    </div>
    <div style="width: 78%;float:left;">
        <el-row>
            <el-col style="margin-left: 10px">
                <el-card v-if="active ===0">
                    <div slot="header" class="clearfix">
                        <span>房间基本信息</span>
                    </div>
                    <el-form :model="model" ref="form1" label-width="100px" label-position="left">
                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="region" label="服务器风格">
                                    <el-radio-group v-model="model.clusterIntention">
                                        <el-radio-button label="social">社交</el-radio-button>
                                    </el-radio-group>
                                    <el-radio-group v-model="model.clusterIntention">
                                        <el-radio-button label="cooperative">合作</el-radio-button>
                                    </el-radio-group>
                                    <el-radio-group v-model="model.clusterIntention">
                                        <el-radio-button label="competitive">竞争</el-radio-button>
                                    </el-radio-group>
                                    <el-radio-group v-model="model.clusterIntention">
                                        <el-radio-button label="madness">疯狂</el-radio-button>
                                    </el-radio-group>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="clusterName" label="房间名称"
                                              :rules="[{ required: true, message: '请输入房间名称', trigger: 'blur' }]">
                                    <el-input v-model="model.clusterName" placeholder="请输入房间名称" clearable
                                              maxlength="100"
                                              show-word-limit></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="email" label="房间描述">
                                    <el-input v-model="model.clusterDescription" clearable maxlength="200" show-word-limit
                                              type="textarea" :rows="4"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="region" label="游戏模式">
                                    <el-radio-group v-model="model.gameMode">
                                        <el-radio-button label="survival">生存</el-radio-button>
                                        <el-radio-button label="wilderness">荒野</el-radio-button>
                                        <el-radio-button label="endless">无尽</el-radio-button>
                                    </el-radio-group>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="ss" label="PVP">
                                    <el-switch v-model="model.pvp" active-text="启动" inactive-text="关闭"></el-switch>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="slider" label="最大玩家数量">
                                    <el-slider v-model="model.maxPlayers" :min="1" :max="max" show-input></el-slider>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="email" label="房间密码">
                                    <el-input v-model="model.clusterPassword" clearable maxlength="20"
                                              show-word-limit></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="token" label="令牌Token"
                                              :rules="[{ required: true, message: '请输入令牌Token', trigger: 'blur' }]">
                                    <el-input v-model="model.token" placeholder="请输入饥荒账户的令牌" :rows="3" clearable
                                              maxlength="100" show-word-limit
                                              type="textarea"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>

                    <el-button @click="drawer = true" type="primary" style="margin-left: 16px;">
                        示例
                    </el-button>
                </el-card>

                <el-card v-if="active ===1" style="height: 500px;">
                    <div slot="header" class="clearfix">
                        <span>地面世界设置</span>
                    </div>
                    <el-form :model="model" ref="form2" label-width="100px" label-position="left">
                        <el-form-item label="地面设置">
                            <el-input type="textarea" placeholder="请输入地面设置" :rows="15"
                                      v-model="model.masterMapData"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button @click="drawer = true" type="primary" style="margin-left: 16px;">
                        示例
                    </el-button>
                </el-card>

                <el-card v-if="active ===2" style="height: 500px;">
                    <div slot="header" class="clearfix">
                        <span>洞穴世界设置</span>
                    </div>
                    <el-form :model="model" ref="form3" label-width="100px" label-position="left">
                        <el-form-item label="洞穴设置">
                            <el-input type="textarea" :rows="15" v-model="model.cavesMapData"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button @click="drawer = true" type="primary" style="margin-left: 16px;">
                        示例
                    </el-button>
                </el-card>

                <el-card v-if="active ===3" style="height: 500px;">
                    <div slot="header" class="clearfix">
                        <span>mod设置</span>
                    </div>
                    <el-form :model="model" ref="form4" label-width="100px" label-position="left">
                        <el-form-item prop="ss" label="mod设置">
                            <el-input type="textarea" :rows="15" v-model="model.modData"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button @click="drawer = true" type="primary" style="margin-left: 16px;">
                        示例
                    </el-button>
                </el-card>

                <el-drawer title="示例" :visible.sync="drawer" :with-header="false" size="50%">
                    <el-card>


                        <el-row>
                            <el-col>
                                <span>我的Token：{{myToken}}</span>
                                <br/>
                                <el-button style="margin: 10px" @click="copy(0)">复制</el-button>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col>
                                <span>我的地面设置：return {["desc"]="标准《饥荒》体验......</span>
                                <br/>
                                <el-button style="margin: 10px" @click="copy(1)">复制</el-button>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col>
                                <span>我的洞穴设置：return {["desc"]="探查洞穴…… 一起......</span>
                                <br/>
                                <el-button style="margin: 10px" @click="copy(2)">复制</el-button>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col>
                                <span>我的MOD设置：return {["workshop-1651623054"]......</span>
                                <br/>
                                <el-button style="margin: 10px" @click="copy(3)">复制</el-button>
                            </el-col>
                        </el-row>
                    </el-card>

                </el-drawer>

                <el-card style="margin-top: 10px; position: sticky; bottom: 0;  z-index: 10;">
                    <el-button v-show="active != 0" @click="previous()">上一步</el-button>
                    <el-button icon="el-icon-refresh-left" v-show="active === 0" @click="clearSetting()" type="warning">
                        重置
                    </el-button>
                    <el-button v-show="active != 3" type="primary" @click="next(active)">下一步</el-button>
                    <el-button v-show="active == 3" type="primary" @click="save(1)">仅保存设置</el-button>
                    <el-button v-show="active == 3" type="primary" @click="save(2)">生成新游戏</el-button>
                </el-card>
            </el-col>
        </el-row>
    </div>


</div>

</body>

<script>

    new Vue({
        el: '#setting_index_app',
        data: {
            active: 0,
            max: 32,
            drawer: false,
            myToken: 'pds-g^KU_qE7e8rv1^VVrVXd/01kBDicd7UO5LeL+uYZH1+geZlrutzItvOaw=',
            myMaster: '    return {\n' +
                '  ["desc"]="标准《饥荒》体验。",\n' +
                '  ["hideminimap"]=false,\n' +
                '  id="SURVIVAL_TOGETHER",\n' +
                '  ["location"]="forest",\n' +
                '  ["max_playlist_position"]=999,\n' +
                '  ["min_playlist_position"]=0,\n' +
                '  ["name"]="默认",\n' +
                '  ["numrandom_set_pieces"]=4,\n' +
                '  ["override_level_string"]=false,\n' +
                '  ["overrides"]={\n' +
                '    ["alternatehunt"]="often",\n' +
                '    ["angrybees"]="rare",\n' +
                '    ["antliontribute"]="default",\n' +
                '    ["autumn"]="default",\n' +
                '    ["bearger"]="default",\n' +
                '    ["beefalo"]="default",\n' +
                '    ["beefaloheat"]="default",\n' +
                '    ["bees"]="default",\n' +
                '    ["berrybush"]="default",\n' +
                '    ["birds"]="default",\n' +
                '    ["boons"]="often",\n' +
                '    ["branching"]="default",\n' +
                '    ["butterfly"]="default",\n' +
                '    ["buzzard"]="default",\n' +
                '    ["cactus"]="default",\n' +
                '    ["carrot"]="default",\n' +
                '    ["catcoon"]="default",\n' +
                '    ["chess"]="default",\n' +
                '    ["day"]="default",\n' +
                '    ["deciduousmonster"]="default",\n' +
                '    ["deerclops"]="default",\n' +
                '    ["disease_delay"]="none",\n' +
                '    ["dragonfly"]="default",\n' +
                '    ["flint"]="default",\n' +
                '    ["flowers"]="default",\n' +
                '    ["frograin"]="never",\n' +
                '    ["goosemoose"]="default",\n' +
                '    ["grass"]="default",\n' +
                '    ["has_ocean"]=true,\n' +
                '    ["houndmound"]="default",\n' +
                '    ["hounds"]="default",\n' +
                '    ["hunt"]="default",\n' +
                '    ["keep_disconnected_tiles"]=true,\n' +
                '    ["krampus"]="often",\n' +
                '    ["layout_mode"]="LinkNodesByKeys",\n' +
                '    ["liefs"]="default",\n' +
                '    ["lightning"]="default",\n' +
                '    ["lightninggoat"]="default",\n' +
                '    ["loop"]="default",\n' +
                '    ["lureplants"]="default",\n' +
                '    ["marshbush"]="default",\n' +
                '    ["merm"]="default",\n' +
                '    ["meteorshowers"]="default",\n' +
                '    ["meteorspawner"]="default",\n' +
                '    ["moles"]="default",\n' +
                '    ["mushroom"]="default",\n' +
                '    ["no_joining_islands"]=true,\n' +
                '    ["no_wormholes_to_disconnected_tiles"]=true,\n' +
                '    ["penguins"]="default",\n' +
                '    ["perd"]="default",\n' +
                '    ["petrification"]="default",\n' +
                '    ["pigs"]="default",\n' +
                '    ["ponds"]="default",\n' +
                '    ["prefabswaps_start"]="default",\n' +
                '    ["rabbits"]="default",\n' +
                '    ["reeds"]="default",\n' +
                '    ["regrowth"]="default",\n' +
                '    ["roads"]="default",\n' +
                '    ["rock"]="default",\n' +
                '    ["rock_ice"]="default",\n' +
                '    ["sapling"]="default",\n' +
                '    ["season_start"]="default",\n' +
                '    ["specialevent"]="winters_feast",\n' +
                '    ["spiders"]="default",\n' +
                '    ["spring"]="default",\n' +
                '    ["start_location"]="default",\n' +
                '    ["summer"]="default",\n' +
                '    ["tallbirds"]="default",\n' +
                '    ["task_set"]="default",\n' +
                '    ["tentacles"]="default",\n' +
                '    ["touchstone"]="default",\n' +
                '    ["trees"]="default",\n' +
                '    ["tumbleweed"]="default",\n' +
                '    ["walrus"]="default",\n' +
                '    ["weather"]="default",\n' +
                '    ["wildfires"]="rare",\n' +
                '    ["winter"]="default",\n' +
                '    ["world_size"]="default",\n' +
                '    ["wormhole_prefab"]="wormhole" \n' +
                '  },\n' +
                '  ["random_set_pieces"]={\n' +
                '    "Sculptures_2",\n' +
                '    "Sculptures_3",\n' +
                '    "Sculptures_4",\n' +
                '    "Sculptures_5",\n' +
                '    "Chessy_1",\n' +
                '    "Chessy_2",\n' +
                '    "Chessy_3",\n' +
                '    "Chessy_4",\n' +
                '    "Chessy_5",\n' +
                '    "Chessy_6",\n' +
                '    "Maxwell1",\n' +
                '    "Maxwell2",\n' +
                '    "Maxwell3",\n' +
                '    "Maxwell4",\n' +
                '    "Maxwell6",\n' +
                '    "Maxwell7",\n' +
                '    "Warzone_1",\n' +
                '    "Warzone_2",\n' +
                '    "Warzone_3" \n' +
                '  },\n' +
                '  ["required_prefabs"]={ "multiplayer_portal" },\n' +
                '  ["required_setpieces"]={ "Sculptures_1", "Maxwell5" },\n' +
                '  ["substitutes"]={  },\n' +
                '  ["version"]=4 \n' +
                '}\n' +
                '    ',
            myCaves: '    return {\n' +
                '  ["background_node_range"]={ 0, 1 },\n' +
                '  ["desc"]="探查洞穴…… 一起！",\n' +
                '  ["hideminimap"]=false,\n' +
                '  id="DST_CAVE",\n' +
                '  ["location"]="cave",\n' +
                '  ["max_playlist_position"]=999,\n' +
                '  ["min_playlist_position"]=0,\n' +
                '  ["name"]="洞穴",\n' +
                '  ["numrandom_set_pieces"]=0,\n' +
                '  ["override_level_string"]=false,\n' +
                '  ["overrides"]={\n' +
                '    ["banana"]="default",\n' +
                '    ["bats"]="default",\n' +
                '    ["berrybush"]="default",\n' +
                '    ["boons"]="often",\n' +
                '    ["branching"]="default",\n' +
                '    ["bunnymen"]="default",\n' +
                '    ["cave_ponds"]="default",\n' +
                '    ["cave_spiders"]="default",\n' +
                '    ["cavelight"]="default",\n' +
                '    ["chess"]="default",\n' +
                '    ["disease_delay"]="none",\n' +
                '    ["earthquakes"]="rare",\n' +
                '    ["fern"]="default",\n' +
                '    ["fissure"]="default",\n' +
                '    ["flint"]="default",\n' +
                '    ["flower_cave"]="often",\n' +
                '    ["grass"]="default",\n' +
                '    ["layout_mode"]="RestrictNodesByKey",\n' +
                '    ["lichen"]="default",\n' +
                '    ["liefs"]="rare",\n' +
                '    ["loop"]="default",\n' +
                '    ["marshbush"]="default",\n' +
                '    ["monkey"]="default",\n' +
                '    ["mushroom"]="default",\n' +
                '    ["mushtree"]="default",\n' +
                '    ["prefabswaps_start"]="default",\n' +
                '    ["reeds"]="default",\n' +
                '    ["regrowth"]="default",\n' +
                '    ["roads"]="never",\n' +
                '    ["rock"]="default",\n' +
                '    ["rocky"]="default",\n' +
                '    ["sapling"]="default",\n' +
                '    ["season_start"]="default",\n' +
                '    ["slurper"]="default",\n' +
                '    ["slurtles"]="default",\n' +
                '    ["specialevent"]="winters_feast",\n' +
                '    ["start_location"]="caves",\n' +
                '    ["task_set"]="cave_default",\n' +
                '    ["tentacles"]="default",\n' +
                '    ["touchstone"]="default",\n' +
                '    ["trees"]="default",\n' +
                '    ["weather"]="default",\n' +
                '    ["world_size"]="small",\n' +
                '    ["wormattacks"]="default",\n' +
                '    ["wormhole_prefab"]="tentacle_pillar",\n' +
                '    ["wormlights"]="default",\n' +
                '    ["worms"]="default" \n' +
                '  },\n' +
                '  ["required_prefabs"]={ "multiplayer_portal" },\n' +
                '  ["substitutes"]={  },\n' +
                '  ["version"]=4 \n' +
                '}\n' +
                '    ',
            myMod: '                return {\n' +
                '  ["workshop-1651623054"]={\n' +
                '    ["configuration_options"]={\n' +
                '      ["ddon"]=true,\n' +
                '      ["hbcolor"]="dynamic",\n' +
                '      ["hblength"]=10,\n' +
                '      ["hbpos"]=1,\n' +
                '      ["hbstyle"]="heart",\n' +
                '      ["value"]=true \n' +
                '    },\n' +
                '    ["enabled"]=true \n' +
                '  },\n' +
                '  ["workshop-375850593"]={ ["configuration_options"]={  }, ["enabled"]=true },\n' +
                '  ["workshop-378160973"]={\n' +
                '    ["configuration_options"]={\n' +
                '      ["ENABLEPINGS"]=true,\n' +
                '      ["FIREOPTIONS"]=2,\n' +
                '      ["OVERRIDEMODE"]=false,\n' +
                '      ["SHAREMINIMAPPROGRESS"]=true,\n' +
                '      ["SHOWFIREICONS"]=true,\n' +
                '      ["SHOWPLAYERICONS"]=true,\n' +
                '      ["SHOWPLAYERSOPTIONS"]=2 \n' +
                '    },\n' +
                '    ["enabled"]=true \n' +
                '  },\n' +
                '  ["workshop-501385076"]={ ["configuration_options"]={ ["quick_harvest"]=true }, ["enabled"]=true },\n' +
                '  ["workshop-666155465"]={\n' +
                '    ["configuration_options"]={\n' +
                '      ["chestB"]=-1,\n' +
                '      ["chestG"]=-1,\n' +
                '      ["chestR"]=-1,\n' +
                '      ["food_estimation"]=-1,\n' +
                '      ["food_order"]=0,\n' +
                '      ["food_style"]=0,\n' +
                '      ["lang"]="auto",\n' +
                '      ["show_food_units"]=-1,\n' +
                '      ["show_uses"]=-1 \n' +
                '    },\n' +
                '    ["enabled"]=true \n' +
                '  },\n' +
                '["workshop-1216718131"]={\n' +
                '    ["configuration_options"]={ ["clean"]=true, ["lang"]=true, ["stack"]=true },\n' +
                '    ["enabled"]=true \n' +
                '  } \n' +
                '}\n' +
                '    \n' +
                '    \n' +
                '    \n' +
                '    ',
            model: {
                clusterIntention: 'social',
                clusterName: undefined,
                clusterDescription: undefined,
                gameMode: 'survival',
                pvp: false,
                maxPlayers: 6,
                clusterPassword: undefined,
                token: undefined,
                masterMapData: undefined,
                cavesMapData: undefined,
                modData: undefined,
                type: 1,
            },
        },
        created() {
            //拉取服务器信息
            this.getConfig();
        },
        methods: {
            copy(val) {
                let tmpInput = document.createElement('textarea');
                if (val === 0) {
                    tmpInput.value = this.myToken;
                }
                if (val === 1) {
                    tmpInput.value = this.myMaster;
                }
                if (val === 2) {
                    tmpInput.value = this.myCaves;
                }
                if (val === 3) {
                    tmpInput.value = this.myMod;
                }
                document.body.appendChild(tmpInput);
                tmpInput.select();
                document.execCommand("Copy");
                tmpInput.className = 'tmpInput';
                tmpInput.style.display = 'none';
                this.successMessage('复制成功');
            },
            save(type) {
                this.model.type = type;
                post("/setting/saveConfig", this.model).then((data) => {
                    if (data != undefined && data.code != 0) {
                        this.warningMessage(data.message);
                    } else {
                        this.successMessage("成功");
                        this.active = 0;
                        this.getConfig();
                    }
                })
            },
            getConfig() {
                get("/setting/getConfig").then((data) => {
                    if (data) {
                        this.model = data;
                    }
                })
            },
            clearSetting() {
                let tmp = {
                    clusterIntention: 'social',
                    clusterName: undefined,
                    clusterDescription: undefined,
                    gameMode: 'survival',
                    pvp: false,
                    maxPlayers: 6,
                    clusterPassword: undefined,
                    token: undefined,
                    masterMapData: undefined,
                    cavesMapData: undefined,
                    modData: undefined,
                    type: 1,
                };
                this.model = tmp;
            },
            next(val) {
                if (val === 0) {
                    //校验基础信息
                    this.$refs['form1'].validate((valid) => {
                        if (valid) {
                            this.active++;
                        } else {
                            return false;
                        }
                    });
                } else {
                    this.active++;
                }

            },
            previous() {
                this.active--;
            },
            successMessage(message) {
                this.$message({
                    message: message,
                    type: 'success'
                });
            },
            warningMessage(message) {
                this.$message({
                    message: message,
                    type: 'warning'
                });
            },
        }
    });


</script>

</html>
