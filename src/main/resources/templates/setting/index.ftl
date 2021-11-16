<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <#import "../system/user/spring.ftl" as spring>
    <title><@spring.message code="setting.config.title"/></title>
    <#include "../common/header.ftl"/>
</head>
<body>

<div id="setting_index_app">
    <div style="width: 20%;float:left;" v-if="labelPosition === 'left'">
        <el-card>
            <div style="height: 500px">
                <el-steps direction="vertical" :active="active">
                    <el-step title="<@spring.message code="setting.room.basic.title"/>"></el-step>
                    <el-step title="<@spring.message code="setting.ground.world.title"/>"></el-step>
                    <el-step title="<@spring.message code="setting.cave.world.title"/>"></el-step>
                    <el-step title="MOD <@spring.message code="setting.word"/>"></el-step>
                    <el-step title="<@spring.message code="setting.success"/>"></el-step>
                </el-steps>
            </div>
        </el-card>
    </div>
    <div  :style="labelPosition === 'left'? 'width: 78%;float:left;':'width:100%'">
        <el-row>
            <el-col :style="labelPosition === 'left'? 'margin-left: 10px':''" >
                <el-card v-if="active ===0">
                    <div slot="header" class="clearfix">
                        <span><@spring.message code="setting.room.basic.title"/></span>
                    </div>
                    <el-form :size="size" :model="model" ref="form1" label-width="<#if lang == 'zh'>100px<#else >150px</#if>" :label-position="labelPosition">
                        <el-row>
                            <el-col :span="<#if lang == 'zh'>15<#else >18</#if>">
                                <el-form-item prop="region" label="<@spring.message code="setting.room.server.style"/>">
                                    <el-radio-group v-model="model.clusterIntention">
                                        <el-radio-button label="social"><@spring.message code="setting.room.server.style.social"/></el-radio-button>
                                    </el-radio-group>
                                    <el-radio-group v-model="model.clusterIntention">
                                        <el-radio-button label="cooperative"><@spring.message code="setting.room.server.style.cooperative"/></el-radio-button>
                                    </el-radio-group>
                                    <el-radio-group v-model="model.clusterIntention">
                                        <el-radio-button label="competitive"><@spring.message code="setting.room.server.style.competitive"/></el-radio-button>
                                    </el-radio-group>
                                    <el-radio-group v-model="model.clusterIntention">
                                        <el-radio-button label="madness"><@spring.message code="setting.room.server.style.madness"/></el-radio-button>
                                    </el-radio-group>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="clusterName" label="<@spring.message code="setting.room.name"/>"
                                              :rules="[{ required: true, message: '<@spring.message code="tips.setting.room"/>', trigger: 'blur' }]">
                                    <el-input v-model="model.clusterName" placeholder="<@spring.message code="tips.setting.room"/>" clearable
                                              maxlength="100"
                                              show-word-limit></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="email" label="<@spring.message code="setting.room.description"/>">
                                    <el-input v-model="model.clusterDescription" clearable maxlength="200" show-word-limit
                                              type="textarea" :rows="4"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="region" label="<@spring.message code="setting.game.mode"/>">
                                    <el-radio-group v-model="model.gameMode">
                                        <el-radio-button label="survival"><@spring.message code="setting.game.mode.survival"/></el-radio-button>
                                        <el-radio-button label="wilderness"><@spring.message code="setting.game.mode.wilderness"/></el-radio-button>
                                        <el-radio-button label="endless"><@spring.message code="setting.game.mode.endless"/></el-radio-button>
                                    </el-radio-group>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="ss" label="PVP">
                                    <el-switch v-model="model.pvp" active-text="<@spring.message code="home.pane1.card1.dst.active.on"/>" inactive-text="<@spring.message code="home.pane1.card1.dst.active.off"/>"></el-switch>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="slider" label="<@spring.message code="setting.game.max.players"/>">
                                    <el-slider v-model="model.maxPlayers" :min="1" :max="max" :show-input="labelPosition === 'left'"></el-slider>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="email"  label="<@spring.message code="setting.game.password"/>">
                                    <el-input v-model="model.clusterPassword" clearable maxlength="20"
                                              show-word-limit></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="token" label="<@spring.message code="setting.token.name"/>"
                                              :rules="[{ required: true, message: '<@spring.message code="tips.setting.input"/> <@spring.message code="setting.token.name"/>', trigger: 'blur' }]">
                                    <el-input v-model="model.token" placeholder="<@spring.message code="tips.setting.input"/> <@spring.message code="setting.account.token"/>" :rows="3" clearable
                                              maxlength="100" show-word-limit
                                              type="textarea"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>

                    <el-button :size="size" @click="drawer = true" type="primary" style="margin-left: 16px;">
                        <@spring.message code="setting.example"/>
                    </el-button>
                </el-card>

                <el-card v-if="active ===1" style="height: 500px;">
                    <div slot="header" class="clearfix">
                        <span><@spring.message code="setting.ground.world.title"/></span>
                    </div>
                    <el-form :size="size" :model="model" ref="form2" label-width="130px" :label-position="labelPosition">
                        <el-form-item label="<@spring.message code="setting.ground.title"/>">
                            <el-input type="textarea" placeholder="<@spring.message code="tips.setting.input"/> <@spring.message code="setting.ground.title"/>" :rows="15"
                                      v-model="model.masterMapData"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button :size="size" @click="drawer = true" type="primary" style="margin-left: 16px;">
                        <@spring.message code="setting.example"/>
                    </el-button>
                </el-card>

                <el-card v-if="active ===2" style="height: 500px;">
                    <div slot="header" class="clearfix">
                        <span><@spring.message code="setting.cave.world.title"/></span>
                    </div>
                    <el-form :size="size" :model="model" ref="form3" label-width="130px" :label-position="labelPosition">
                        <el-form-item label="<@spring.message code="setting.cave.title"/>">
                            <el-input type="textarea" :rows="15" v-model="model.cavesMapData"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button :size="size" @click="drawer = true" type="primary" style="margin-left: 16px;">
                        <@spring.message code="setting.example"/>
                    </el-button>
                </el-card>

                <el-card v-if="active ===3" style="height: 500px;">
                    <div slot="header" class="clearfix">
                        <span>MOD <@spring.message code="setting.word"/></span>
                    </div>
                    <el-form :size="size" :model="model" ref="form4" label-width="130px" :label-position="labelPosition">
                        <el-form-item prop="ss" label="MOD <@spring.message code="setting.word"/>">
                            <el-input type="textarea" :rows="15" v-model="model.modData"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button :size="size" @click="drawer = true" type="primary" style="margin-left: 16px;">
                        <@spring.message code="setting.example"/>
                    </el-button>
                </el-card>

                <el-drawer title="<@spring.message code="setting.example"/>" :visible.sync="drawer" :with-header="false" size="50%">
                    <el-card>
                        <el-row>
                            <el-col>
                                <span><@spring.message code="setting.my"/> Token：{{myToken}}</span>
                                <br/>
                                <el-button :size="size" style="margin: 10px" @click="copy(0)"><@spring.message code="setting.copy"/></el-button>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col>
                                <span><@spring.message code="setting.my"/> <@spring.message code="setting.ground.title"/>：return {["desc"]="<@spring.message code="setting.standard.experience"/>......</span>
                                <br/>
                                <el-button :size="size" style="margin: 10px" @click="copy(1)"><@spring.message code="setting.copy"/></el-button>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col>
                                <span><@spring.message code="setting.my"/> <@spring.message code="setting.cave.title"/>：return {["desc"]="<@spring.message code="setting.cave.desc"/>......</span>
                                <br/>
                                <el-button :size="size" style="margin: 10px" @click="copy(2)"><@spring.message code="setting.copy"/></el-button>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col>
                                <span><@spring.message code="setting.my"/> MOD <@spring.message code="setting.word"/>：return {["workshop-1651623054"]......</span>
                                <br/>
                                <el-button :size="size" style="margin: 10px" @click="copy(3)"><@spring.message code="setting.copy"/></el-button>
                            </el-col>
                        </el-row>
                    </el-card>

                </el-drawer>

                <el-card style="margin-top: 10px; position: sticky; bottom: 0;  z-index: 10;">
                    <el-button :size="size" v-show="active != 0" @click="previous()"><@spring.message code="setting.previous"/></el-button>
                    <el-button :size="size" icon="el-icon-refresh-left" v-show="active === 0" @click="clearSetting()" type="warning">
                        <@spring.message code="setting.reset"/>
                    </el-button>
                    <el-button :size="size" v-show="active != 3" type="primary" @click="next(active)"><@spring.message code="setting.next.step"/></el-button>
                    <el-button :size="size" v-show="active == 3" type="primary" @click="save(1)"><@spring.message code="setting.save.settings.only"/></el-button>
                    <el-button :size="size" v-show="active == 3" type="primary" @click="save(3)"><@spring.message code="setting.save.settings.restart"/></el-button>
                    <el-button :size="size" v-show="active == 3" type="primary" @click="save(2)"><@spring.message code="setting.generate.a.new.game"/></el-button>
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
            labelPosition:'left',
            size:'medium'
        },
        created() {
            //拉取服务器信息
            this.getConfig();
            this.getLabelPosition()
        },
         mounted(){
         window.onresize = () => {
                this.getLabelPosition()
            }
          },
        methods: {
            getLabelPosition(){
                            let windowWidth = window.innerWidth

            console.log('getLabelPosition',windowWidth)
                if(windowWidth < 768){
                this.labelPosition = 'top'
                this.size= 'mini'
                }
                else {
                this.labelPosition = 'left'
                this.size = 'medium'
                }

            },
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
                    if (data) {
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
