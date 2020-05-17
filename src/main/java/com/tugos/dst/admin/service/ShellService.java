package com.tugos.dst.admin.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.tugos.dst.admin.vo.Constant;
import com.tugos.dst.admin.vo.ModFileUtil;
import com.tugos.dst.admin.utils.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ShellService {


    /**
     * 获取地面程序进程号
     *
     * @return
     */
    public String getMasterProcessNum() {
        List<String> strings = ShellUtil.runShell(" ps -ef | grep -v grep |grep 'Master'|sed -n '1P'|awk '{print $2}' ");
        if (strings != null && strings.size() > 0) {
            return strings.get(0);
        } else {
            return "";
        }
    }

    /**
     * 获取洞穴程序进程号
     *
     * @return
     */
    public String getCavesProcessNum() {
        List<String> strings = ShellUtil.runShell(" ps -ef | grep -v grep |grep 'Caves'|sed -n '1P'|awk '{print $2}' ");
        if (strings != null && strings.size() > 0) {
            return strings.get(0);
        } else {
            return "";
        }
    }



    /**
     * 备份游戏存档
     *
     * @return
     */
    public String backup(String name) {
        String format;
        if (StringUtils.isNoneBlank(name)){
            format = name;
        }else {
            format = DateUtil.format(new Date(), "yyyy_MM_dd_HH_mm_ss");
        }

        String fileName = format + "_bak.tar";
        StringBuilder command = new StringBuilder();
        command.append("cd $HOME/.klei/DoNotStarveTogether ").append(" ; ");
        command.append("tar zcvf ").append(fileName).append(" MyDediServer/");
        List<String> result = ShellUtil.runShell(command.toString());
        return fileName;
    }

    /**
     * 还原游戏存档
     *
     * @param fileName
     * @return
     */
    public String revertBackup(String fileName) {
        StringBuffer command = new StringBuffer();
        command.append("cd $HOME/.klei/DoNotStarveTogether ").append(" ; ");
        command.append("rm -rf MyDediServer/").append(" ;");
        command.append("tar -zxvf ").append(fileName);
        List<String> result = ShellUtil.runShell(command.toString());
        String json = JSONUtil.toJsonStr(result);
        return json;
    }

    /**
     * 显示备份记录
     *
     * @return
     */
    public List<String> showBackup() {
        String backupPath = Constant.ROOT_PATH + "/" +Constant.DST_DOC_PATH;
        File file = new File(backupPath);
        if (!file.exists()){
            return new ArrayList<>();
        }
        StringBuffer command = new StringBuffer();
        command.append("cd $HOME/.klei/DoNotStarveTogether ").append(" ; ");
        command.append("ls");
        List<String> result = ShellUtil.runShell(command.toString());
        return result;
    }


    /**
     * 重启地面,需要按照mod，先停止在启动
     *
     * @return
     */
    public String startMaster() {
        installModToServer();
        execShell(Constant.START_MASTER);
        log.info("重启地面.....");
        return "success";
    }


    /**
     * 启动地下,先停止在启动
     *
     * @return
     */
    public String startCaves() {
        execShell(Constant.START_CAVES);
        log.info("启动地下.....");
        return "success";
    }

    /**
     * 停止地面
     * @return
     */
    public String stopMaster(){
        execShell(Constant.STOP_MASTER);
        log.info("停止地面.....");
        return "success";
    }


    /**
     * 停止洞穴
     * @return
     */
    public String stopCaves(){
        execShell(Constant.STOP_CAVES);
        log.info("停止洞穴.....");
        return "success";
    }

    /**
     * 更新游戏
     * @return
     */
    public String updateGame() {
        execShell(Constant.UPDATE_GAME);
        log.info("更新游戏.....");
        return "success";
    }


    /**
     * 清空游戏记录
     * @return
     */
    public String delRecord() {
        execShell(Constant.DEL_RECORD);
        log.info("清空游戏记录.....");
        return "success";
    }



    /**
     * 执行shell脚本
     *
     * @param fileName
     */
    private void execShell(String fileName) {
        String filePath = Constant.SHELL_FILE_PATH + "/" + fileName;
        ShellUtil.execShell(filePath);
    }


    /**
     * 将配置的mod按照到游戏中
     * @return
     * @throws Exception
     */
    public String installModToServer() {
        try {
            log.info("安装mod到服务器.....");
            String myGameModPath = Constant.ROOT_PATH + "/" + ".klei/DoNotStarveTogether/MyDediServer/Master/modoverrides.lua";
            File file = new File(myGameModPath);
            if (!file.exists()){
                //不存在
                return "mod文件不存在";
            }
            List<String> modConfig = ModFileUtil.readModConfigFile(myGameModPath);
            String serverModPath = Constant.ROOT_PATH + "/" + "dst/mods/dedicated_server_mods_setup.lua";
            ModFileUtil.writeModConfigFile(modConfig, serverModPath);
            return "success";
        } catch (Exception e){
            log.error("将配置的mod按照到游戏中失败：",e);
        }
        return "错误";
    }



}
