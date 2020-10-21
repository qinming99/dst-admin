package com.tugos.dst.admin.service;


import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.enums.SettingTypeEnum;
import com.tugos.dst.admin.enums.StartTypeEnum;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.utils.FileUtils;
import com.tugos.dst.admin.vo.GameConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author qinming
 * @date 2020-05-16
 * <p> 房间设置服务 </p>
 */
@Service
@Slf4j
public class SettingService {

    private HomeService homeService;

    /**
     * 保存戏设置 如果type为2 会启动新游戏
     *
     * @param vo 信息
     */
    public ResultVO<String> saveConfig(GameConfigVO vo) throws Exception {
        if (!this.checkConfigIsExists()) {
            return ResultVO.fail("游戏配置文件夹不存在");
        }
        //创建地面和洞穴的ini配置文件
        this.createMasterServerIni();
        this.createCavesServerIni();
        //创建房间配置
        this.createCluster(vo);
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
        if (!this.checkTokenIsExists()) {
            return null;
        }
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
     * 生成地面 server.ini
     */
    public void createMasterServerIni() throws Exception {
        String basePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH +
                DstConstant.SINGLE_SLASH + DstConstant.DST_MASTER;
        //创建地面设置的文件夹
        FileUtils.mkdirs(basePath);
        String finalPath = basePath + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_SERVER_INI_NAME;
        log.info("生成地面 server.ini文件,{}", finalPath);
        String context = "[NETWORK]\nserver_port = 10999\n\n\n[SHARD]\nis_master = true\n\n\n[ACCOUNT]\nencode_user_path = true\n";
        FileUtils.writeFile(finalPath, context);
    }


    /**
     * 生成洞穴 server.ini
     */
    public void createCavesServerIni() throws Exception {
        String basePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH + "/" + DstConstant.DST_CAVES;
        //创建洞穴设置的文件夹
        FileUtils.mkdirs(basePath);
        String finalPath = basePath + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_SERVER_INI_NAME;
        log.info("生成洞穴 server.ini文件,{}", finalPath);
        String context = "[NETWORK]\n" +
                "server_port = 10998\n" +
                "\n" +
                "\n" +
                "[SHARD]\n" +
                "is_master = false\n" +
                "name = Caves\n" +
                "id = 1310214455\n" +
                "\n" +
                "\n" +
                "[ACCOUNT]\n" +
                "encode_user_path = true\n" +
                "\n" +
                "\n" +
                "[STEAM]\n" +
                "master_server_port = 27017\n" +
                "authentication_port = 8767\n";

        FileUtils.writeFile(finalPath, context);
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
        list.add("cluster_intention = " + vo.getClusterIntention());
        String clusterPassword = vo.getClusterPassword();
        if (StringUtils.isNotBlank(clusterPassword)) {
            //密码存在
            clusterPassword = clusterPassword.trim();
            list.add("cluster_password = " + clusterPassword);
        }else {
            list.add("cluster_password = ");
        }
        String clusterDescription = vo.getClusterDescription();
        if (StringUtils.isNotBlank(clusterDescription)) {
            //密码存在
            clusterDescription = clusterDescription.trim();
            list.add("cluster_description = " + clusterDescription);
        }else {
            list.add("cluster_description = ");
        }
        list.add("cluster_name = " + vo.getClusterName());
        list.add("offline_cluster = false");
        list.add("cluster_language = zh");
        list.add("");
        list.add("[MISC]");
        list.add("console_enabled = true");
        list.add("");
        list.add("");
        list.add("[SHARD]");
        list.add("shard_enabled = true");
        list.add("bind_ip = 127.0.0.1");
        list.add("master_ip = 127.0.0.1");
        list.add("master_port = 10888");
        list.add("cluster_key = defaultPass");

        StringBuffer sb = new StringBuffer();
        list.forEach(e -> sb.append(e).append("\n"));
        FileUtils.writeFile(filePath, sb.toString());
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
