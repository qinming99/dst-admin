package com.tugos.dst.admin.service;


import cn.hutool.core.io.FileUtil;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.enums.SettingTypeEnum;
import com.tugos.dst.admin.enums.StartTypeEnum;
import com.tugos.dst.admin.utils.DstConfigData;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.utils.FileUtils;
import com.tugos.dst.admin.utils.StrUtils;
import com.tugos.dst.admin.utils.filter.SensitiveFilter;
import com.tugos.dst.admin.vo.GameConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author qinming
 * @date 2020-05-16
 * <p> 房间设置服务 </p>
 */
@Service
@Slf4j
public class SettingService {

    private HomeService homeService;

    @Value("${dst.filter.sensitive:true}")
    private Boolean filterFlag;

    @Value("${dst.max.snapshots:6}")
    private String maxSnapshots;

    @Value("${dst.master.port:10888}")
    private String masterPort;

    @Value("${dst.ground.port:10999}")
    private String groundPort;

    @Value("${dst.caves.port:10998}")
    private String cavesPort;

    /**
     * 保存戏设置 如果type为2 会启动新游戏
     *
     * @param vo 信息
     */
    public ResultVO<String> saveConfig(GameConfigVO vo) throws Exception {
        this.filterSensitiveWords(vo);
        //创建存档目录
        FileUtil.mkdir(DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH);
        //创建地面和洞穴的ini配置文件
        //this.createMasterServerIni();
        this.createMasterServerIniV2();
        //this.createCavesServerIni();
        this.createCavesServerIniV2();
        //创建房间配置
        //this.createCluster(vo);
        this.createClusterV2(vo);
        //创建token配置
        this.createToken(vo.getToken().trim());
        //创建地面世界设置
        this.createMasterMap(vo.getMasterMapData());
        //创建洞穴世界设置
        this.createCavesMap(vo.getCavesMapData());
        //创建mod设置
        this.createMod(vo.getModData());
        if (SettingTypeEnum.START_GAME.type.equals(vo.getType())) {
            //启动新游戏
            homeService.delRecord();
            homeService.start(StartTypeEnum.START_ALL.type);
        }
        if (SettingTypeEnum.SAVE_RESTART.type.equals(vo.getType())){
            homeService.start(StartTypeEnum.START_ALL.type);
        }
        return ResultVO.success();
    }

    /**
     * 监测配置文件文件夹是否存在
     *
     * @return true存在
     */
    private boolean checkConfigIsExists() {
        String filePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH;
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 读取游戏配置
     *
     * @return token配置不存在是返回null
     */
    public GameConfigVO getConfig() throws Exception {
        GameConfigVO gameConfigVO = new GameConfigVO();
        String token = this.getToken();
        gameConfigVO.setToken(token);
        List<String> clusterData = this.getClusterData();
        if (CollectionUtils.isNotEmpty(clusterData)) {
            for (String e : clusterData) {
                if (StringUtils.isBlank(e)) {
                    continue;
                }
                if (e.contains("game_mode")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        gameConfigVO.setGameMode(split[1].trim());
                    }
                }
                if (e.contains("max_players")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        gameConfigVO.setMaxPlayers(Integer.valueOf(split[1].trim()));
                    }
                }
                if (e.contains("pvp")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        gameConfigVO.setPvp(Boolean.valueOf(split[1].trim()));
                    }
                }
                if (e.contains("cluster_intention")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        gameConfigVO.setClusterIntention(split[1].trim());
                    }
                }
                if (e.contains("cluster_password")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        gameConfigVO.setClusterPassword(split[1].trim());
                    }
                }
                if (e.contains("cluster_description")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        gameConfigVO.setClusterDescription(split[1].trim());
                    }
                }
                if (e.contains("cluster_name")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        gameConfigVO.setClusterName(split[1].trim());
                    }
                }
            }
        }
        String masterMapData = this.getMasterMapData();
        gameConfigVO.setMasterMapData(masterMapData);
        String cavesMapData = this.getCavesMapData();
        gameConfigVO.setCavesMapData(cavesMapData);
        String modData = this.getModData();
        gameConfigVO.setModData(modData);
        return gameConfigVO;
    }

    /**
     * 校验token文件是否已经存在
     *
     * @return true 存在
     */
    @Deprecated
    private boolean checkTokenIsExists() {
        String filePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH +
                DstConstant.SINGLE_SLASH + DstConstant.DST_USER_CLUSTER_TOKEN;
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 读取房间设置
     *
     * @return 房间信息
     */
    public List<String> getClusterData() throws Exception {
        String filePath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_USER_GAME_CONFIG_PATH;
        List<String> configList = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            //不存在不管它
            return configList;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String tmp;
        //使用readLine方法，一次读一行
        while ((tmp = br.readLine()) != null) {
            configList.add(tmp);
        }
        br.close();
        return configList;
    }


    /**
     * 读取token设置
     *
     * @return token
     */
    public String getToken() throws Exception {
        String filePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH +
                DstConstant.SINGLE_SLASH + DstConstant.DST_USER_CLUSTER_TOKEN;
        return FileUtils.readFile(filePath);
    }


    /**
     * 生成地面 server.ini 端口号10998
     * [NETWORK]
     * server_port = 10999
     *
     *
     * [SHARD]
     * is_master = true
     *
     *
     * [ACCOUNT]
     * encode_user_path = true
     */
    public void createMasterServerIni() throws Exception {
        String basePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH +
                DstConstant.SINGLE_SLASH + DstConstant.DST_MASTER;
        //创建地面设置的文件夹
        FileUtils.mkdirs(basePath);
        String finalPath = basePath + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_SERVER_INI_NAME;
        log.info("生成地面 server.ini文件,{}", finalPath);
        List<String> ini = new ArrayList<>();
        ini.add("[NETWORK]");
        String groundPort = StringUtils.isNotBlank(DstConfigData.groundPort) ?
                DstConfigData.groundPort : this.groundPort;
        ini.add("server_port = " + groundPort);
        ini.add("");
        ini.add("");
        ini.add("[SHARD]");
        ini.add("is_master = true");
        ini.add("name = Master");
        ini.add("id = 10000");
        ini.add("");
        ini.add("");
        ini.add("[ACCOUNT]");
        ini.add("encode_user_path = true");
        FileUtils.writeLineFile(finalPath, ini);
    }

    /**
     * 生成地面 server.ini
     */
    public void createMasterServerIniV2() throws Exception {
        String basePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH +
                DstConstant.SINGLE_SLASH + DstConstant.DST_MASTER;
        //创建地面设置的文件夹
        FileUtils.mkdirs(basePath);
        String finalPath = basePath + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_SERVER_INI_NAME;
        log.info("生成地面 server.ini文件,{}", finalPath);
        if (!FileUtil.exist(finalPath)) {
            FileUtil.writeString("", new File(finalPath), "utf-8");
        }
        Wini ini = new Wini(new File(finalPath));

        Map<String, String> network = ini.get("NETWORK");
        if (network == null) {
            ini.add("NETWORK", "server_port", StrUtils.ofNULL(DstConfigData.groundPort, this.groundPort));
        } else {
            network.put("server_port", StrUtils.ofNULL(DstConfigData.groundPort, this.groundPort));
        }

        Map<String, String> shard = ini.get("SHARD");
        if (shard == null) {
            ini.add("SHARD", "is_master", "true");
            ini.add("SHARD", "name", "Master");
            ini.add("SHARD", "id", "10000");
        } else {
            //shard.put("is_master", "true");
            //shard.put("name", "Master");
            //shard.put("id", "10000");
        }

        Map<String, String> account = ini.get("ACCOUNT");
        if (account == null) {
            ini.add("ACCOUNT", "encode_user_path", "true");
        } else {
            //account.put("encode_user_path", "true");
        }
        ini.store();
    }

    /**
     * 生成洞穴 server.ini 端口号：10999
     * [SHARD]
     * is_master = true /false      # 是否是 master 服务器，只能存在一个 true，其他全是 false
     * name = caves                 # 针对非 master 服务器的名称
     * id = ???                     # 随机生成，不用加入该属性
     * <p>
     * [STEAM]
     * authentication_port = 8766   # Steam 用的端口，确保每个实例都不相同
     * master_server_port = 27016   # Steam 用的端口，确保每个实例都不相同
     * <p>
     * [NETWORK]
     * server_port = 10998          # 监听的 UDP 端口，只能介于 10998 - 11018 之间，确保每个实例都不相同
     */
    public void createCavesServerIni() throws Exception {
        String basePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH + "/" + DstConstant.DST_CAVES;
        //创建洞穴设置的文件夹
        FileUtils.mkdirs(basePath);
        String finalPath = basePath + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_SERVER_INI_NAME;
        log.info("生成洞穴 server.ini文件,{}", finalPath);
        List<String> ini = new ArrayList<>();
        ini.add("[NETWORK]");
        String cavesPort = StringUtils.isNotBlank(DstConfigData.cavesPort) ?
                DstConfigData.cavesPort : this.cavesPort;
        ini.add("server_port = " + cavesPort);
        ini.add("");
        ini.add("");
        ini.add("[SHARD]");
        ini.add("is_master = false");
        ini.add("name = Caves");
        ini.add("id = 10010");
        ini.add("");
        ini.add("");
        ini.add("[ACCOUNT]");
        ini.add("encode_user_path = true");
        ini.add("");
        ini.add("");
        ini.add("[STEAM]");
        ini.add("authentication_port = 8766");
        ini.add("master_server_port = 27016");
        FileUtils.writeLineFile(finalPath, ini);
    }



    /**
     * 生成洞穴 server.ini
     */
    public void createCavesServerIniV2() throws Exception {
        String basePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH + "/" + DstConstant.DST_CAVES;
        //创建洞穴设置的文件夹
        FileUtils.mkdirs(basePath);
        String finalPath = basePath + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_SERVER_INI_NAME;
        log.info("生成洞穴 server.ini文件,{}", finalPath);

        if (!FileUtil.exist(finalPath)) {
            FileUtil.writeString("", new File(finalPath), "utf-8");
        }
        Wini ini = new Wini(new File(finalPath));

        Map<String, String> network = ini.get("NETWORK");
        if (network == null) {
            ini.add("NETWORK", "server_port", StrUtils.ofNULL(DstConfigData.cavesPort, this.cavesPort));
        } else {
            network.put("server_port", StrUtils.ofNULL(DstConfigData.cavesPort, this.cavesPort));
        }

        Map<String, String> shard = ini.get("SHARD");
        if (shard == null) {
            ini.add("SHARD", "is_master", "false");
            ini.add("SHARD", "name", "Caves");
            ini.add("SHARD", "id", "10010");
        } else {
            //shard.put("is_master", "false");
            //shard.put("name", "Caves");
            //shard.put("id", "10010");
        }

        Map<String, String> account = ini.get("ACCOUNT");
        if (account == null) {
            ini.add("ACCOUNT", "encode_user_path", "true");
        } else {
            //account.put("encode_user_path", "true");
        }

        Map<String, String> steam = ini.get("STEAM");
        if (steam == null) {
            ini.add("STEAM", "authentication_port", "8766");
            ini.add("STEAM", "master_server_port", "27016");
        } else {
            //steam.put("authentication_port", "8766");
            //steam.put("master_server_port", "27016");
        }
        ini.store();
    }


    /**
     * 生成cluster_token.txt 文件
     */
    private void createToken(String token) throws Exception {
        String filePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH
                + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_CLUSTER_TOKEN;
        log.info("生成cluster_token.txt 文件,{}", filePath);
        FileUtils.writeFile(filePath, token);
    }

    /**
     * 生成游戏配置文件 cluster.ini
     * 位置：/home/ubuntu/.klei/DoNotStarveTogether/MyDediServer
     * [MISC]
     * max_snapshots = 6                  # 最大快照数，决定了可回滚的天数
     * console_enabled = true             # 是否开启控制台
     * <p>
     * [SHARD]
     * shard_enabled = true               # 服务器共享，要开启洞穴服务器的必须启用
     * bind_ip = 127.0.0.1                # 服务器监听的地址，当所有实例都运行在同一台机器时，可填写 127.0.0.1，会被 server .ini 覆盖
     * master_ip = 127.0.0.1              # master 服务器的 IP，针对非 master 服务器，若与 master 服务器运行在同一台机器时，可填写 127.0.0.1，会被 server.ini 覆盖
     * master_port = 10888                # 监听 master 服务器的 UDP 端口，所有连接至 master 服务器的非 master 服务器必须相同
     * cluster_key = dst                  # 连接密码，每台服务器必须相同，会被 server.ini 覆盖
     * <p>
     * [STEAM]
     * steam_group_only = false           # 只允许某 Steam 组的成员加入
     * steam_group_id = 0                 # 指定某个 Steam 组，填写组 ID
     * steam_group_admins = false         # 开启后，Steam 组的管理员拥有服务器的管理权限
     * <p>
     * [NETWORK]
     * offline_server = false             # 离线服务器，只有局域网用户能加入，并且所有依赖于 Steam 的任何功能都无效，比如说饰品掉落
     * tick_rate = 15                     # 每秒通信次数，越高游戏体验越好，但是会加大服务器负担
     * whitelist_slots = 0                # 为白名单用户保留的游戏位
     * cluster_password =                 # 游戏密码，不设置表示无密码
     * cluster_name = ttionya test        # 游戏房间名称
     * cluster_description = description  # 游戏房间描述
     * lan_only_cluster = false           # 局域网游戏
     * cluster_intention = madness        # 游戏偏好，可选 cooperative, competitive, social, or madness，随便设置
     * autosaver_enabled = true           # 自动保存
     * <p>
     * [GAMEPLAY]
     * max_players = 16                   # 最大游戏人数
     * pvp = true                         # 能不能攻击其他玩家，能不能给其他玩家喂屎
     * game_mode = survival               # 游戏模式，可选 survival, endless or wilderness，与玩家死亡后的负面影响有关
     * pause_when_empty = false           # 没人服务器暂停，刷天数必备
     * vote_kick_enabled = false          # 投票踢人
     */
    private void createCluster(GameConfigVO vo) throws Exception {
        String filePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH +
                DstConstant.SINGLE_SLASH + DstConstant.DST_USER_CLUSTER_INI_NAME;
        log.info("生成游戏配置文件 cluster.ini文件,{}", filePath);
        LinkedList<String> list = new LinkedList<>();
        list.add("[GAMEPLAY]");
        list.add("game_mode = " + vo.getGameMode());
        list.add("max_players = " + vo.getMaxPlayers());
        list.add("pvp = " + vo.getPvp());
        list.add("pause_when_empty = true");
        list.add("");
        list.add("");
        list.add("[NETWORK]");
        list.add("lan_only_cluster = false");
        //list.add("cluster_intention = " + vo.getClusterIntention());
        String clusterPassword = vo.getClusterPassword();
        if (StringUtils.isNotBlank(clusterPassword)) {
            //密码存在
            clusterPassword = clusterPassword.trim();
            list.add("cluster_password = " + clusterPassword);
        } else {
            list.add("cluster_password = ");
        }
        String clusterDescription = vo.getClusterDescription();
        if (StringUtils.isNotBlank(clusterDescription)) {
            //密码存在
            clusterDescription = clusterDescription.trim();
            list.add("cluster_description = " + clusterDescription);
        } else {
            list.add("cluster_description = ");
        }
        list.add("cluster_name = " + vo.getClusterName());
        list.add("offline_cluster = false");
        //根据环境设置语言
        Locale locale = LocaleContextHolder.getLocale();
        list.add("cluster_language = " + locale.getLanguage());
        list.add("");
        list.add("[MISC]");
        list.add("console_enabled = true");
        list.add("max_snapshots = " + maxSnapshots);
        list.add("");
        list.add("");
        list.add("[SHARD]");
        list.add("shard_enabled = true");
        list.add("bind_ip = 127.0.0.1");
        list.add("master_ip = 127.0.0.1");
        String masterPort = StringUtils.isNotBlank(DstConfigData.masterPort) ?
                DstConfigData.masterPort : this.masterPort;
        list.add("master_port = " + masterPort);
        list.add("cluster_key = defaultPass");

        StringBuffer sb = new StringBuffer();
        list.forEach(e -> sb.append(e).append("\n"));
        FileUtils.writeFile(filePath, sb.toString());
    }

    /**
     * 新的生成游戏配置，不会覆盖不支持编辑的配置
     * @param vo 配置
     * @throws Exception 异常
     */
    public void createClusterV2(GameConfigVO vo) throws Exception {
        String filePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH +
                DstConstant.SINGLE_SLASH + DstConstant.DST_USER_CLUSTER_INI_NAME;
        log.info("生成游戏配置文件 cluster.ini文件,{}", filePath);

        if (!FileUtil.exist(filePath)) {
            FileUtil.writeString("", new File(filePath), "utf-8");
        }
        Wini ini = new Wini(new File(filePath));
        Map<String, String> gameplay = ini.get("GAMEPLAY");
        if (gameplay == null) {
            ini.add("GAMEPLAY", "game_mode", StrUtils.ofNULL(vo.getGameMode()));
            ini.add("GAMEPLAY", "max_players", StrUtils.ofNULL(vo.getMaxPlayers(), "6"));
            ini.add("GAMEPLAY", "pvp", StrUtils.ofNULL(vo.getPvp(), "false"));
            ini.add("GAMEPLAY", "pause_when_empty", "true");
        } else {
            gameplay.put("game_mode", StrUtils.ofNULL(vo.getGameMode()));
            gameplay.put("max_players", StrUtils.ofNULL(vo.getMaxPlayers(), "6"));
            gameplay.put("pvp", StrUtils.ofNULL(vo.getPvp(), "false"));
            //gameplay.put("pause_when_empty", "true");
        }

        Map<String, String> network = ini.get("NETWORK");
        if (network == null) {
            ini.add("NETWORK", "lan_only_cluster", "false");
            ini.add("NETWORK", "cluster_password", StrUtils.ofNULL(vo.getClusterPassword()).trim());
            ini.add("NETWORK", "cluster_description", StrUtils.ofNULL(vo.getClusterDescription()));
            ini.add("NETWORK", "cluster_name", StrUtils.ofNULL(vo.getClusterName()));
            ini.add("NETWORK", "offline_cluster", "false");
            ini.add("NETWORK", "cluster_language", LocaleContextHolder.getLocale().getLanguage());
        } else {
            //network.put("lan_only_cluster", "false");
            network.put("cluster_password", StrUtils.ofNULL(vo.getClusterPassword()).trim());
            network.put("cluster_description", StrUtils.ofNULL(vo.getClusterDescription()));
            network.put("cluster_name", StrUtils.ofNULL(vo.getClusterName()));
            //network.put("offline_cluster", "false");
            network.put("cluster_language", LocaleContextHolder.getLocale().getLanguage());
        }

        Map<String, String> misc = ini.get("MISC");
        if (misc == null) {
            ini.add("MISC", "console_enabled", "true");
            ini.add("MISC", "max_snapshots", maxSnapshots);
        } else {
            //misc.put("console_enabled", "true");
            misc.put("max_snapshots", maxSnapshots);
        }

        Map<String, String> shard = ini.get("SHARD");
        if (shard == null) {
            ini.add("SHARD", "shard_enabled", "true");
            ini.add("SHARD", "bind_ip", "127.0.0.1");
            ini.add("SHARD", "master_ip", "127.0.0.1");
            ini.add("SHARD", "master_port", StrUtils.ofNULL(DstConfigData.masterPort, this.masterPort));
            ini.add("SHARD", "cluster_key", "defaultPass");
        } else {
            //shard.put("shard_enabled",  "127.0.0.1");
            //shard.put("bind_ip",  "127.0.0.1");
            //shard.put("master_ip",  "127.0.0.1");
            shard.put("master_port", StrUtils.ofNULL(DstConfigData.masterPort, this.masterPort));
            //shard.put("cluster_key", "defaultPass");
        }
        ini.store();
    }

    /**
     * 过滤敏感词
     */
    private GameConfigVO filterSensitiveWords(GameConfigVO vo) {
        if (!filterFlag) {
            return vo;
        }
        try {
            SensitiveFilter sensitiveFilter = SensitiveFilter.DEFAULT;
            if (StringUtils.isNotBlank(vo.getClusterName())) {
                String filter = sensitiveFilter.filter(vo.getClusterName(), '*');
                vo.setClusterName(filter);
            }
            if (StringUtils.isNotBlank(vo.getClusterDescription())) {
                String filter = sensitiveFilter.filter(vo.getClusterDescription(), '*');
                vo.setClusterDescription(filter);
            }
        } catch (Exception e) {
            log.error("过滤敏感词错误：", e);
            vo.setClusterName("饥荒世界");
            vo.setClusterDescription("");
        }
        return vo;
    }


    /**
     * 读取mod设置
     */
    public String getModData() throws Exception {
        String filePath = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_GAME_MASTER_MOD_PATH;
        return FileUtils.readFile(filePath);
    }

    /**
     * 读取地面世界设置
     */
    public String getMasterMapData() throws Exception {
        String filePath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_USER_GAME_MASTER_MAP_PATH;
        return FileUtils.readFile(filePath);
    }

    /**
     * 读取洞穴世界设置
     */
    public String getCavesMapData() throws Exception {
        String filePath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_USER_GAME_CAVES_MAP_PATH;
        return FileUtils.readFile(filePath);
    }


    /**
     * 将mod添加到地面和洞穴中
     *
     * @param data mod设置
     */
    public void createMod(String data) throws Exception {
        String masterModeFile = DstConstant.ROOT_PATH + "/" + DstConstant.DST_USER_GAME_MASTER_MOD_PATH;
        String cavesModeFile = DstConstant.ROOT_PATH + "/" + DstConstant.DST_USER_GAME_CAVES_MOD_PATH;
        if (StringUtils.isNotBlank(data)) {
            FileUtils.writeFile(masterModeFile, data);
            FileUtils.writeFile(cavesModeFile, data);
        } else {
            //置空
            FileUtils.writeFile(masterModeFile, "");
            FileUtils.writeFile(cavesModeFile, "");
        }
    }

    /**
     * 将地面世界设置添加到指定位置
     *
     * @param data 地面设置
     */
    public void createMasterMap(String data) throws Exception {
        String filePath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_USER_GAME_MASTER_MAP_PATH;
        if (StringUtils.isNotBlank(data)) {
            FileUtils.writeFile(filePath, data);
        } else {
            //置空
            FileUtils.writeFile(filePath, "");
        }
    }

    /**
     * 将洞穴设置添加到指定位置
     *
     * @param data 洞穴设置
     */
    public void createCavesMap(String data) throws Exception {
        String filePath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_USER_GAME_CAVES_MAP_PATH;
        if (StringUtils.isNotBlank(data)) {
            FileUtils.writeFile(filePath, data);
        } else {
            //置空
            FileUtils.writeFile(filePath, "");
        }
    }

    @Autowired
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }
}
