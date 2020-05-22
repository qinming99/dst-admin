package com.tugos.dst.admin.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuminghui
 * @date 2020-04-27 18:12
 * <p> 无 </p>
 */
public class ModFileUtil {

    public static void main(String[] args) throws Exception {
        String path = "/Users/qinming/Documents/Klei/DoNotStarveTogether/474624371/Cluster_1/Master/modoverrides.lua";
        List<String> strings = readModConfigFile(path);
        System.out.println(strings);
        String path2 = "/Users/qinming/Documents/dedicated_server_mods_setup.lua";
        writeModConfigFile(strings, path2);

    }

    public static List<String> readModConfigFile(String path) throws Exception {
        List<String> list = new ArrayList<>();
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        File file = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String tmp;
        //使用readLine方法，一次读一行
        while ((tmp = br.readLine()) != null) {
            if (tmp != null && tmp.contains("workshop-")) {
                String[] split = tmp.split("=");
                String x = split[0];
                Matcher m = p.matcher(x);
                list.add(m.replaceAll("").trim());
            }
        }
        br.close();
        return list;
    }

    public static void writeModConfigFile(List<String> list, String path) throws Exception {
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file);
        for (String e : list) {
            StringBuffer sb = new StringBuffer();
//            ServerModSetup("666155465")
            sb.append("ServerModSetup(\"").append(e).append("\")").append("\n");
            fos.write(sb.toString().getBytes());
        }
        fos.close();//关闭文件
    }


}
