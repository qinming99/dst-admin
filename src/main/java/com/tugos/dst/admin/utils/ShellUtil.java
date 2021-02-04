package com.tugos.dst.admin.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
/**
 * @author qinming
 * @date 2020-05-17
 * <p> shell脚本执行工具类 </p>
 */
@Slf4j
public class ShellUtil {


    /**
     * 运行shell脚本
     *
     * @param shell 需要运行的shell脚本
     */
    public static void execShell(String shell) {
        List<String> strList = new ArrayList<>();
        List<String> errList = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(shell);
            process.waitFor();
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            InputStreamReader err = new InputStreamReader(process.getErrorStream());
            LineNumberReader input = new LineNumberReader(ir);
            LineNumberReader errInput = new LineNumberReader(err);
            String line;

            while ((line = input.readLine()) != null) {
                strList.add(line);
            }
            while ((line = errInput.readLine()) != null) {
                errList.add(line);
            }
            process.destroy();
            ir.close();
            err.close();
            input.close();
            errInput.close();
        } catch (Exception e) {
            log.error("运行shell脚本失败:",e);
        }
        log.debug("执行 {} ", shell);
        log.debug("执行结果：{}  ", strList);
        log.debug("脚本错误信息：{}", errList);
    }

    /**
     * 运行shell脚本 new String[]方式
     *
     * @param shell 需要运行的shell脚本文件
     */
    public static void execShellBin(String shell) {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shell}, null, null);
        } catch (Exception e) {
            log.error("运行shell脚本失败:",e);
        }
    }


    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * @param shStr 需要执行的shell命令
     */
    public static List<String> runShell(String shStr) {
        List<String> strList = new ArrayList<>();
        List<String> errList = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shStr}, null, null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            InputStreamReader err = new InputStreamReader(process.getErrorStream());
            LineNumberReader input = new LineNumberReader(ir);
            LineNumberReader errInput = new LineNumberReader(err);
            String line;

            while ((line = input.readLine()) != null) {
                strList.add(line);
            }
            while ((line = errInput.readLine()) != null) {
                errList.add(line);
            }
            process.destroy();
            ir.close();
            err.close();
            input.close();
            errInput.close();
        } catch (Exception e) {
            //log.error("运行shell脚本失败:",e);
        }
        log.debug("执行 {} ", shStr);
        log.debug("执行结果：{}  ", strList);
        log.debug("脚本错误信息：{}", errList);
        return strList;
    }


}
