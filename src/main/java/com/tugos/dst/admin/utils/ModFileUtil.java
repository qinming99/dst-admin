package com.tugos.dst.admin.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qinming
 * @date 2020-04-27 18:12
 * <p> 无 </p>
 */
public class ModFileUtil {

    /**
     * 读取mod的编号
     *
     * @param path mod文件地址
     * @return 编号列表
     */
    public static List<String> readModConfigFile(String path) {
        File file = new File(path);
        if (file.exists()){
            String content = FileUtil.readString(path, "utf-8");
            return findModNo(content);
        }else {
            return new ArrayList<>();
        }
    }


    /**
     * mod匹配正则
     */
    public static final String MOD_REGEX = "\"workshop-\\w[-\\w+]*\"";

    /**
     * 获取modNo
     *
     * @param content mod信息
     */
    public static List<String> findModNo(String content) {
        List<String> result = new ArrayList<>();
        List<String> resultFindAll = ReUtil.findAll(MOD_REGEX, content, 0, new ArrayList<>());
        //匹配出来的结果："workshop-1651623054"
        if (CollectionUtils.isNotEmpty(resultFindAll)) {
            resultFindAll.forEach(e -> {
                String modNo = e.replace("\"", "").split("-")[1];
                result.add(modNo);
            });
        }
        return result;
    }


    /**
     * 写入mod
     *
     * @param list 编号
     * @param path 地址
     */
    public static boolean writeModConfigFile(List<String> list, String path) {
        File file = new File(path);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (String str : list) {
                fos.write(("ServerModSetup(\"" + str + "\")" + "\n").getBytes());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
