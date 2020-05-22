package com.tugos.dst.admin.service;


import com.tugos.dst.admin.utils.Constant;
import com.tugos.dst.admin.vo.GameConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class SettingService {


    public void saveConfig(GameConfigVO vo){
        try {
            if (StringUtils.isNotBlank(vo.getClusterPassword())){
                vo.setClusterPassword(vo.getClusterPassword().trim());
            }
            writeMasterServer();
            writeCavesServer();
            writeCluster(vo);
            writeToken(vo.getToken().trim());
        } catch (Exception e) {
            log.info("生成配置文件失败：",e);
        }
    }


    /**
     * 读取游戏配置
     * @return
     * @throws Exception
     */
    public GameConfigVO getConfig() throws Exception {
        GameConfigVO vo = new GameConfigVO();
        vo.setToken(readToken());
        List<String> clusterData = getClusterData();
        if (CollectionUtils.isNotEmpty(clusterData)) {
            for (String e : clusterData) {
                if (StringUtils.isBlank(e)) {
                    continue;
                }
                if (e.contains("game_mode")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        vo.setGameMode(split[1]);
                    }
                }
                if (e.contains("max_players")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        vo.setMaxPlayers(split[1]);
                    }
                }
                if (e.contains("pvp")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        vo.setPvp(split[1]);
                    }
                }
                if (e.contains("cluster_intention")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        vo.setClusterIntention(split[1]);
                    }
                }
                if (e.contains("cluster_password")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        vo.setClusterPassword(split[1].trim());
                    }
                }
                if (e.contains("cluster_description")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        vo.setClusterDescription(split[1]);
                    }
                }
                if (e.contains("cluster_name")) {
                    String[] split = e.split("=");
                    if (StringUtils.isNotBlank(split[1])) {
                        vo.setClusterName(split[1]);
                    }
                }
            }
        }
        return vo;
    }




    public static List<String> getClusterData() throws Exception {
        String filePath = Constant.ROOT_PATH + "/" + Constant.DST_USER_GAME_CONFIG_PATH;
        List<String> configList = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            //不存在不管它
            return null;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String tmp;
        //使用readLine方法，一次读一行
        while ((tmp = br.readLine()) != null) {
            configList.add(tmp);
        }
        br.close();
        return configList;
    }


    public static String readToken() throws Exception{
        String filePath = Constant.ROOT_PATH + Constant.DST_USER_GAME_CONFG_PATH +"/"+"cluster_token.txt";
        File file = new File(filePath);
        if (!file.exists()){
            //不存在不管它
            return null;
        }
        InputStream inputStream = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int len;
        StringBuffer sb = new StringBuffer();
        while ((len = (inputStream.read(buf))) != -1) {
            sb.append(new String(buf, 0, len));
        }
        inputStream.close();
        return sb.toString();
    }


    /**
     * 生成地面 server.ini
     */
    public void writeMasterServer() throws Exception{
        String fileName = "/"+"server.ini";
        String filePath = Constant.ROOT_PATH + Constant.DST_USER_GAME_CONFG_PATH +"/"+Constant.DST_MASTER;
        //创建目录
        mkdirs(filePath);
        log.info("生成地面 server.ini文件,{}",filePath + fileName);
        File file = new File(filePath + fileName);
        OutputStream outputStream = new FileOutputStream(file);
        String context = "[NETWORK]\nserver_port = 10999\n\n\n[SHARD]\nis_master = true\n\n\n[ACCOUNT]\nencode_user_path = true\n";
        outputStream.write(context.getBytes());
        outputStream.flush();
        outputStream.close();
    }


    /**
     * 生成洞穴 server.ini
     * @throws Exception
     */
    public void writeCavesServer() throws Exception{
        String fileName = "/"+"server.ini";
        String filePath = Constant.ROOT_PATH + Constant.DST_USER_GAME_CONFG_PATH +"/"+Constant.DST_CAVES;
        //创建目录
        mkdirs(filePath);
        log.info("生成洞穴 server.ini文件,{}",filePath + fileName);
        File file = new File(filePath + fileName);
        OutputStream outputStream = new FileOutputStream(file);

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

        outputStream.write(context.getBytes());
        outputStream.flush();
        outputStream.close();

    }


    /**
     * 生成cluster_token.txt 文件
     *
     */
    private void writeToken(String token) throws Exception{
        String filePath = Constant.ROOT_PATH + Constant.DST_USER_GAME_CONFG_PATH +"/"+"cluster_token.txt";
        log.info("生成cluster_token.txt 文件,{}",filePath);
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(token.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 生成游戏配置文件 cluster.ini
     * 位置：/home/ubuntu/.klei/DoNotStarveTogether/MyDediServer
     * @param vo
     */
    private void writeCluster(GameConfigVO vo) throws Exception{
        String filePath = Constant.ROOT_PATH + Constant.DST_USER_GAME_CONFG_PATH +"/"+"cluster.ini";
        log.info("生成游戏配置文件 cluster.ini文件,{}",filePath);
        LinkedList<String> list = new LinkedList<>();
        list.add("[GAMEPLAY]");
        list.add("game_mode = " +vo.getGameMode());
        list.add("max_players = " + vo.getMaxPlayers());
        list.add("pvp = "+vo.getPvp());
        list.add("pause_when_empty = true");
        list.add("");
        list.add("");
        list.add("[NETWORK]");
        list.add("lan_only_cluster = false");
        list.add("cluster_intention = " +vo.getClusterIntention());
        list.add("cluster_password = " + vo.getClusterPassword());
        list.add("cluster_description = " + vo.getClusterDescription());
        list.add("cluster_name = " +vo.getClusterName());
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

        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        StringBuffer sb = new StringBuffer();
        list.forEach(e->{
            sb.append(e).append("\n");
        });
        outputStream.write(sb.toString().getBytes());
        outputStream.flush();
        outputStream.close();
    }



    /**
     * 创建目录地址 如 /home/ubuntu/.klei/DoNotStarveTogether/MyDediServer/Master
     * @param path
     */
    public static void mkdirs(String path) {
        //变量不需赋初始值，赋值后永远不会读取变量，在下一个变量读取之前，该值总是被另一个赋值覆盖
        File f;
        try {
            f = new File(path);
            if (!f.exists()) {
                boolean i = f.mkdirs();
                if (i) {
                    log.info("层级文件夹创建成功！");
                } else {
                    log.error("层级文件夹创建失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
