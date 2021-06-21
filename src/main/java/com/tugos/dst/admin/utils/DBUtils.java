package com.tugos.dst.admin.utils;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.*;

/**
 * @author qinming
 * @date 2020-10-27 22:41:01
 * <p> 无 </p>
 */
@Slf4j
public class DBUtils {

    /**
     * 读取项目的数据
     * @param fileName 文件
     * @return 数据
     */
    public static String readProjectData(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = (inputStream.read(buf))) != -1) {
                sb.append(new String(buf, 0, len));
            }
        } catch (Exception e) {
            log.error("读取项目目录的文件失败:", e);
        }
        return sb.toString();
    }

    /**
     * 写入项目数据，每次都会覆盖之前的
     * @param fileName 文件名称
     * @param data 数据
     */
    private static void writeProjectData(String fileName, String data) {
        File file = new File(fileName);
        try (OutputStream outputStream = new FileOutputStream(file);) {
            outputStream.write(data.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            log.error("写入数据失败：", e);
        }
    }

    /**
     * 读取文件中存储的数据写入到缓存中
     */
    public static void readDataToCache(String data) {
        try {
            if (StringUtils.isNotBlank(data)) {
                DstConfigDataTable table = JSONUtil.toBean(data, DstConfigDataTable.class);
                DstConfigData.clearAllData();
                DstConfigData.SCHEDULE_BACKUP_MAP.putAll(table.getSCHEDULE_BACKUP_MAP());
                DstConfigData.SCHEDULE_UPDATE_MAP.putAll(table.getSCHEDULE_UPDATE_MAP());
                BeanUtils.copyProperties(table.getUSER_INFO(),DstConfigData.USER_INFO);
                DstConfigData.notStartMaster = table.getNotStartMaster();
                DstConfigData.notStartCaves = table.getNotStartCaves();
                DstConfigData.smartUpdate = table.getSmartUpdate();
                DstConfigData.masterPort = table.getMasterPort();
                DstConfigData.groundPort = table.getGroundPort();
                DstConfigData.cavesPort = table.getCavesPort();
                log.info("读取文件中的数据到缓存中成功：{}",data);
            }
        } catch (Exception e) {
            log.error("读取文件中的数据到缓存中失败：", e);
        }
    }

    /**
     * 将缓存中的数据保存到本地文件中
     * @return true 保存成功
     */
    public static void saveDataToFile() {
        try {
            DstConfigDataTable table = new DstConfigDataTable();
            table.setSCHEDULE_BACKUP_MAP(DstConfigData.SCHEDULE_BACKUP_MAP);
            table.setSCHEDULE_UPDATE_MAP(DstConfigData.SCHEDULE_UPDATE_MAP);
            table.setUSER_INFO(DstConfigData.USER_INFO);
            table.setNotStartMaster(DstConfigData.notStartMaster);
            table.setNotStartCaves(DstConfigData.notStartCaves);
            table.setSmartUpdate(DstConfigData.smartUpdate);
            table.setMasterPort(DstConfigData.masterPort);
            table.setGroundPort(DstConfigData.groundPort);
            table.setCavesPort(DstConfigData.cavesPort);
            String data = JSONUtil.toJsonStr(table);
            writeProjectData(DstConstant.DST_ADMIN_JSON, data);
        } catch (Exception e) {
            log.error("将缓存中的数据保存到本地文件中失败：", e);
        }
    }

}
