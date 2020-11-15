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
                                    <el-input v-model="model.clusterName" clearable maxlength="100"
                                              show-word-limit></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="15">
                                <el-form-item prop="email" label="房间描述">
                                    <el-input v-model="model.clusterDescription" clearable maxlength="200" show-word-limit
                                              type="textarea" :rows="2"></el-input>
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
                                    <el-input v-model="model.token" :rows="3" clearable maxlength="100" show-word-limit
                                              type="textarea"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>
                </el-card>

                <el-card v-if="active ===1" style="height: 500px;">
                    <div slot="header" class="clearfix">
                        <span>地面世界设置</span>
                    </div>
                    <el-form :model="model" ref="form2" label-width="100px" label-position="left">
                        <el-form-item label="地面设置">
                            <el-input type="textarea" :rows="15" v-model="model.masterMapData"></el-input>
                        </el-form-item>
                    </el-form>
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
                </el-card>

                <el-card style="margin-top: 10px; position: sticky; bottom: 0;  z-index: 10;">
                    <el-button v-show="active != 0" @click="previous()">上一步</el-button>
                    <el-button icon="el-icon-refresh-left" v-show="active === 0" @click="clearSetting()" type="warning">重置</el-button>
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
                type:1,
            },
        },
        created() {
            //拉取服务器信息
            this.getConfig();
        },
        methods: {
            save(type) {
                this.model.type = type;
                post("/setting/saveConfig", this.model).then((data) => {
                    if (data != undefined && data.code != 0) {
                        this.warningMessage(data.message);
                    }else {
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
            clearSetting(){
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
                        type:1,
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
