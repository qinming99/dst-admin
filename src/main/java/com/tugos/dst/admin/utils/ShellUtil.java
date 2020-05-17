package com.tugos.dst.admin.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ShellUtil {

    public static void main2(String[] args) {
//        File file = new File("/User/qinming");
//        file.
//        System.out.println(getFiles("/User/qinming/"));

        List<String> strings = runShell("ps -ef | grep java");


    }

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

    public static void main(String[] args) {
        execShell("/Users/qinming/Documents/shell/test.sh");
    }



    /**
     * 运行shell脚本
     * @param shell 需要运行的shell脚本
     */
    public static void execShell(String shell){
        List<String> strList = new ArrayList<String>();
        List<String> errList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(shell);
            process.waitFor();
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            InputStreamReader err = new InputStreamReader(process.getErrorStream());
            LineNumberReader input = new LineNumberReader(ir);
            LineNumberReader errInput = new LineNumberReader(err);
            String line;

            while ((line = input.readLine()) != null){
                strList.add(line);
            }
            while ((line = errInput.readLine()) != null){
                errList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.error("执行 {} ",shell);
        log.error("执行结果：{}  ",strList);
        log.error("脚本错误信息：{}",errList);
    }

    /**
     * 运行shell脚本 new String[]方式
     * @param shell 需要运行的shell脚本
     */
    public static void execShellBin(String shell){
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shell},null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * @param shStr
     *            需要执行的shell
     * @return
     */
    public static List<String> runShell(String shStr) {
        List<String> strList = new ArrayList<String>();
        List<String> errList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shStr},null,null);
            int i = process.waitFor();
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            InputStreamReader err = new InputStreamReader(process.getErrorStream());
            LineNumberReader input = new LineNumberReader(ir);
            LineNumberReader errInput = new LineNumberReader(err);
            String line;

            while ((line = input.readLine()) != null){
                strList.add(line);
            }
            while ((line = errInput.readLine()) != null){
                errList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.error("执行 {} ",shStr);
        log.error("执行结果：{}  ",strList);
        log.error("脚本错误信息：{}",errList);
        return strList;
    }

//    public static void main(String[] args) {
//        List<String> strings = ShellUtil.runShell("ps -ef | grep -v grep |grep 'shell'|sed -n '1P'|awk '{print $2}'");
//        System.out.println(strings);
////        System.out.println(runShell("ls "));
//    }



}
