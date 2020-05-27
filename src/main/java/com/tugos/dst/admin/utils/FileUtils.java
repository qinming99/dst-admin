package com.tugos.dst.admin.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    /**
     * 读取指定路径下的所有文件
     * @param path 文件路径
     * @return 文件列表
     */
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
                //文件名，不包含路径
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        }
        return files;
    }


}
