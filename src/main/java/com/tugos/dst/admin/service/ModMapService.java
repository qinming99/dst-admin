package com.tugos.dst.admin.service;

import com.tugos.dst.admin.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;


@Service
@Slf4j
public class ModMapService {

    @Autowired
    private SettingService settingService;


    public String getModData() throws Exception {
        String filePath = Constant.ROOT_PATH + "/" + Constant.DST_USER_GAME_MOD_PATH;
        return readFile(filePath);
    }


    public String getMasterMapData() throws Exception {
        String filePath = Constant.ROOT_PATH + "/" + Constant.DST_USER_GAME_MASTER_MAP_PATH;
        return readFile(filePath);
    }

    public String getCavesMapData() throws Exception {
        String filePath = Constant.ROOT_PATH + "/" + Constant.DST_USER_GAME_CAVES_MAP_PATH;
        return readFile(filePath);
    }


    public String saveMod(String data) throws Exception {
        //写入基础配置文件
        settingService.writeMasterServer();
        String filePath = Constant.ROOT_PATH + "/" + Constant.DST_USER_GAME_MOD_PATH;
        writeFile(filePath,data);
        return "success";
    }

    public String saveMaster(String data) throws Exception {
        //写入基础配置文件
        settingService.writeMasterServer();
        String filePath = Constant.ROOT_PATH + "/" + Constant.DST_USER_GAME_MASTER_MAP_PATH;
        writeFile(filePath,data);
        return "success";
    }


    public String saveCaves(String data) throws Exception {
        //写入基础配置文件
        settingService.writeCavesServer();
        String filePath = Constant.ROOT_PATH + "/" + Constant.DST_USER_GAME_CAVES_MAP_PATH;
        writeFile(filePath,data);
        return "success";
    }


    /**
     * 写入文件到指定位置
     * @param filePath
     * @param context
     * @throws Exception
     */
    public static void writeFile(String filePath,String context) throws Exception{
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(context.getBytes());
        outputStream.flush();
        outputStream.close();
    }


    /**
     * 读取指定位置文件
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String readFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
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

}
