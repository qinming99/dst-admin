package com.tugos.dst.admin.utils;

import com.google.common.base.Splitter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List<String> list = new ArrayList<>();
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {
            String tmp;
            while ((tmp = br.readLine()) != null) {
                if (tmp.contains("workshop-")) {
                    List<String> split = Splitter.on("=").splitToList(tmp);
                    split.forEach(e -> {
                        if (e.contains("workshop-")) {
                            Matcher matcher = p.matcher(e);
                            list.add(matcher.replaceAll("").trim());
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
