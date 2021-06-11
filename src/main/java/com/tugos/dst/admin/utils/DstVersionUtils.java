package com.tugos.dst.admin.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author qinming
 * @date 2021-01-09 12:14:53
 * <p> 获取游戏版本号工具 </p>
 */
@Slf4j
public class DstVersionUtils {

    /**
     * 获取steam中dst的最新版本号
     *
     * @return 版本号
     */
    public static String getSteamVersion() {
        String version = null;
        try {
            String url = "https://steamcommunity-a.akamaihd.net/news/newsforapp/v0002/?appid=322330&count=3&maxlength=300&format=vdf";
            HttpRequest get = HttpUtil.createGet(url);
            get.timeout(30000);
            HashMap<String, String> headers = new HashMap<>();
            headers.put("user-agent", "Valve/Steam HTTP Client 1.0 (0)");
            headers.put("Host", "steamcommunity-a.akamaihd.net");
            headers.put("Accept", "text/html,*/*;q=0.9");
            headers.put("accept-encoding", "gzip,identity,*;q=0");
            headers.put("accept-charset", "ISO-8859-1,utf-8,*;q=0.7");
            get.headerMap(headers, true);
            String body = get.execute().body();
            String verStr = StringUtils.substringBefore(StringUtils.substringAfter(body, "[Game Hotfix]"), "\"");
            String doubleVerStr = StringUtils.deleteWhitespace(verStr).replace("-", "");
            String[] split = doubleVerStr.split("&");
            if (split.length >= 2) {
                version = split[1];
            } else {
                version = split[0];
            }
        } catch (Exception e) {
            log.error("从steam获取最新的饥荒版本号失败：{}", e.getMessage());
        }
        return version;
    }

    /**
     * 获取本地的版本号
     * 同时根据地面日志和洞穴日志获取版本号，返回最新版本号
     *
     * @return 版本号
     */
    public static String getLocalVersion() {
        String version = null;
        try {
            String path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + "dst/version.txt";
            List<String> list = FileUtils.readLineFile(path);
            if (CollectionUtils.isNotEmpty(list)) {
                version = list.get(0);
            }
        } catch (Exception e) {
            log.error("获取本地游戏版本号失败：", e);
        }
        return version;
    }


    @Deprecated
    public static String getVersion(String path) {
        String version = null;
        List<String> list = readAppointedLineNumber(new File(path), 10);
        if (CollectionUtils.isNotEmpty(list)) {
            for (String str : list) {
                if (str.contains("Version:")) {
                    String substringAfterLast = StringUtils.substringAfterLast(str, "Version:");
                    version = StringUtils.deleteWhitespace(substringAfterLast);
                    break;
                }
            }
        }
        return version;
    }


    /**
     * 读取指定行数
     *
     * @param file      文件路径
     * @param maxNumber 最大行数
     * @return 内容
     */
    public static List<String> readAppointedLineNumber(File file, int maxNumber) {
        List<String> result = new ArrayList<>();
        if (!file.exists()) {
            return result;
        }
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String str;
            int count = 0;
            while ((str = br.readLine()) != null) {
                result.add(str);
                count++;
                if (count >= maxNumber) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
