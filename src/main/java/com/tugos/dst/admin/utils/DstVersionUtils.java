package com.tugos.dst.admin.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author qinming
 * @date 2021-01-09 12:14:53
 * <p> 获取游戏版本号工具 </p>
 */
@Slf4j
public class DstVersionUtils {

    public static LocalDateTime gameVersionTime;

    public static LocalDateTime lastUpdateTime;

    public static String getSteamVersionByCdn() {
        String info = HttpUtil.get("https://steamcommunity-a.akamaihd.net/news/newsforapp/v0002/?appid=322330&count=1&maxlength=1&format=json");
        JSONObject appNews = JSONUtil.parseObj(info);
        JSONArray newsItems = appNews.getJSONArray("newsitems");
        JSONObject news = newsItems.getJSONObject(0);
        String eventName = news.getStr("title");
        LocalDateTime localDateTime = LocalDateTimeUtil.of(news.getLong("date") * 1000);
        gameVersionTime = localDateTime;
        String postTime = LocalDateTimeUtil.format(localDateTime, DatePattern.NORM_DATETIME_PATTERN);
        return postTime + "||" + eventName;
    }

    /**
     * 获取steam中dst的最新版本号
     *
     * @return 版本号
     */
    public static String getSteamVersionWithTitle() {
        String versionWithTitle = null;
        try {
            String info = HttpUtil.get("https://store.steampowered.com/events/ajaxgetadjacentpartnerevents/?appid=322330&count_before=0&count_after=3&lang_list=6_0");
            JSONObject gameNewsInfo = JSONUtil.parseObj(info);
            JSONArray events = gameNewsInfo.getJSONArray("events");
            JSONObject event = events.getJSONObject(0);
            //活动名称
            String eventName = event.getStr("event_name");
            //活动类型
            Integer eventType = event.getInt("event_type");
            //获取发布时间
            LocalDateTime localDateTime = LocalDateTimeUtil.of(event.getJSONObject("announcement_body").getLong("posttime") * 1000);
            if (eventType == 14 || eventType == 12) {
                gameVersionTime = localDateTime;
            }
            String postTime = LocalDateTimeUtil.format(localDateTime, DatePattern.NORM_DATETIME_PATTERN);
            versionWithTitle = postTime + "||" + eventName;
        } catch (Exception e) {
            log.error("从steam官方接口获取最新的饥荒版本号失败：{}", e.getMessage());
            //如果获取失败就去cdn获取英文更新日志
            versionWithTitle = getSteamVersionByCdn();
        }
        return versionWithTitle;
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
        if (lastUpdateTime == null){
            lastUpdateTime = LocalDateTimeUtil.of(new Date());
        }
        return LocalDateTimeUtil.format(lastUpdateTime, DatePattern.NORM_DATETIME_PATTERN) + "||" + "版本:" + version;
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
