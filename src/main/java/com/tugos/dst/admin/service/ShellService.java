package com.tugos.dst.admin.service;

import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.utils.FileUtils;
import com.tugos.dst.admin.utils.ModFileUtil;
import com.tugos.dst.admin.utils.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qinming
 * @date 2020-05-17
 * <p> dst控制脚本服务</p>
 */
@Service
@Slf4j
public class ShellService {

    /**
     * 获取地面状态
     *
     * @return true 启动了
     */
    public boolean getMasterStatus() {
        String masterProcessNum = this.getMasterProcessNum();
        return StringUtils.isNotBlank(masterProcessNum);
    }

    /**
     * 获取洞穴状态
     *
     * @return true 启动了
     */
    public boolean getCavesStatus() {
        String cavesProcessNum = this.getCavesProcessNum();
        return StringUtils.isNotBlank(cavesProcessNum);
    }

    /**
     * 获取地面程序进程号
     */
    public String getMasterProcessNum() {
        List<String> result = ShellUtil.runShell(" ps -ef | grep -v grep |grep 'Master'|sed -n '1P'|awk '{print $2}' ");
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取洞穴程序进程号
     */
    public String getCavesProcessNum() {
        List<String> result = ShellUtil.runShell(" ps -ef | grep -v grep |grep 'Caves'|sed -n '1P'|awk '{print $2}' ");
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        } else {
            return null;
        }
    }


    /**
     * 备份游戏存档
     */
    public void createBackup(String fileName) {
        StringBuilder command = new StringBuilder();
        command.append("cd $HOME/.klei/DoNotStarveTogether ").append(" ; ");
        command.append("tar zcvf ").append(fileName).append(" MyDediServer/");
        ShellUtil.runShell(command.toString());
    }

    /**
     * 恢复游戏存档
     *
     * @param fileName 备份的游戏名称
     */
    public void revertBackup(String fileName) {
        StringBuilder command = new StringBuilder();
        command.append("cd ~/.klei/DoNotStarveTogether ").append(" ; ");
        command.append("rm -rf MyDediServer/").append(" ;");
        command.append("tar -zxvf ").append(fileName);
        ShellUtil.runShell(command.toString());
    }

    /**
     * 获取备份路径下的备份文件名称
     */
    public List<String> getBackupList() {
        String backupPath = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_DOC_PATH;
        File file = new File(backupPath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<String> files = FileUtils.getFileNames(backupPath);
        if (CollectionUtils.isNotEmpty(files)) {
            return files.stream()
                    .filter(e -> e.contains(DstConstant.BACKUP_FILE_EXTENSION)).collect(Collectors.toList());

        }
        return new ArrayList<>();
    }

    /**
     * 启动地面进程
     *
     * @return 执行信息
     */
    public List<String> startMaster() {
        //开始游戏是安装mod
        ResultVO<String> stringResultVO = this.installModToServer();
        log.info("安装mod：{}",stringResultVO);
        return ShellUtil.runShell(DstConstant.START_MASTER_CMD);
    }


    /**
     * 启动洞穴进程
     *
     * @return 执行信息
     */
    public List<String> startCaves() {
        return ShellUtil.runShell(DstConstant.START_CAVES_CMD);
    }

    /**
     * 停止地面进程
     *
     * @return 执行信息
     */
    public List<String> stopMaster() {
        return ShellUtil.runShell(DstConstant.STOP_MASTER_CMD);
    }


    /**
     * 停止洞穴进程
     *
     * @return 执行信息
     */
    public List<String> stopCaves() {
        return ShellUtil.runShell(DstConstant.STOP_CAVES_CMD);
    }

    /**
     * 更新游戏 需要停止地面和洞穴进程
     *
     * @return 执行信息
     */
    public List<String> updateGame() {
        this.stopMaster();
        this.stopCaves();
        return ShellUtil.runShell(DstConstant.UPDATE_GAME_CMD);
    }

    /**
     * 检查目前所有的screen作业，并删除已经无法使用的screen作业
     */
    public void clearScreen(){
        ShellUtil.runShell(DstConstant.CLEAR_SCREEN_CMD);
    }

    /**
     * 清理地面游戏进度 需要停止服务
     *
     * @return 信息
     */
    public List<String> delMasterRecord() {
        this.stopMaster();
        return ShellUtil.runShell(DstConstant.DEL_RECORD_MASTER_CMD);
    }

    /**
     * 清理洞穴游戏进度 需要停止服务
     *
     * @return 信息
     */
    public List<String> delCavesRecord() {
        this.stopCaves();
        return ShellUtil.runShell(DstConstant.DEL_RECORD_CAVES_CMD);
    }

    /**
     * 将用户配置的mod安装到游戏中
     * 先读取配置，在写入游戏安装目录的dedicated_server_mods_setup文件中
     */
    public ResultVO<String> installModToServer() {
        log.info("安装mod到服务器.....");
        String myGameModPath = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_GAME_MASTER_MOD_PATH;
        File file = new File(myGameModPath);
        if (!file.exists()) {
            return ResultVO.fail("mod文件不存在");
        }
        List<String> modConfig = ModFileUtil.readModConfigFile(myGameModPath);
        String serverModPath = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_MOD_SETTING_PATH;
        boolean flag = ModFileUtil.writeModConfigFile(modConfig, serverModPath);
        if (flag) {
            return ResultVO.success();
        } else {
            return ResultVO.fail("安装mod失败");
        }
    }

    /**
     * 发送广播
     *
     * @param message 内容
     */
    public void sendBroadcast(String message) {
        StringBuilder masterBroadcast = new StringBuilder();
        masterBroadcast.append("screen -S \"DST_MASTER\" -p 0 -X stuff \"c_announce(\\\"");
        masterBroadcast.append(message);
        masterBroadcast.append("\\\")\\n\"");
        //发送地面广播
        ShellUtil.execShellBin(masterBroadcast.toString());
        StringBuilder CavesBroadcast = new StringBuilder();
        CavesBroadcast.append("screen -S \"DST_CAVES\" -p 0 -X stuff \"c_announce(\\\"");
        CavesBroadcast.append(message);
        CavesBroadcast.append("\\\")\\n\"");
        //发送洞穴广播
        ShellUtil.execShellBin(CavesBroadcast.toString());
    }

}
