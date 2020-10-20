package com.tugos.dst.admin.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author qinming
 * @date 2020-05-27
 * <p> 文件工具类 </p>
 */
public class FileUtils {

    /**
     * 读取指定路径下的所有文件
     *
     * @param path 文件路径
     * @return 文件列表
     */
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<>();
        File file = new File(path);
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isFile()) {
                    files.add(child.toString());
                }
            }
        }
        return files;
    }

    /**
     * 获取指定路径下所有文件名称
     * @param path 路径
     * @return 文件名称
     */
    public static List<String> getFileNames(String path) {
        List<String> fileNames = new ArrayList<>();
        File dir = new File(path);
        String[] children = dir.list();
        if (children != null) {
            fileNames.addAll(Arrays.asList(children));
        }
        return fileNames;
    }


    /**
     * 创建目录
     * @param path 如 /home/ubuntu/.klei/DoNotStarveTogether/MyDediServer/Master
     * @return true 创建成功
     */
    public static boolean mkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 读取指定位置文件
     *
     * @param filePath 文件路径
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
        StringBuilder sb = new StringBuilder();
        while ((len = (inputStream.read(buf))) != -1) {
            sb.append(new String(buf, 0, len));
        }
        inputStream.close();
        return sb.toString();
    }


    /**
     * 写入文件到指定位置
     * @param filePath 文件路径
     * @param context 内容
     */
    public static void writeFile(String filePath,String context) throws Exception{
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(context.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
