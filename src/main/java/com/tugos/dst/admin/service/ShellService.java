package com.tugos.dst.admin.service;

import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.enums.DstLogTypeEnum;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.utils.FileUtils;
import com.tugos.dst.admin.utils.ModFileUtil;
import com.tugos.dst.admin.utils.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
     * 最大睡眠时间 10秒
     */
    public static final int MAX_SLEEP_SECOND = 10;

    private SystemService systemService;

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
        List<String> result = ShellUtil.runShell(DstConstant.FIND_MASTER_CMD);
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
        List<String> result = ShellUtil.runShell(DstConstant.FIND_CAVES_CMD);
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
        //使用tar -xvf 来解压
        command.append("tar -xvf ").append(fileName);
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
     * 关闭地面服务 将执行保存保证
     */
    public void shutdownMaster(){
        String shell = "screen -S \""+DstConstant.SCREEN_WORK_MASTER_NAME+"\" -p 0 -X stuff \"c_shutdown(true)\\n\"";
        ShellUtil.execShellBin(shell);
    }

    /**
     * 关闭洞穴服务 将执行保存保证
     */
    public void shutdownCaves(){
        String shell = "screen -S \""+DstConstant.SCREEN_WORK_CAVES_NAME+"\" -p 0 -X stuff \"c_shutdown(true)\\n\"";
        ShellUtil.execShellBin(shell);
    }

    /**
     * 更新游戏 需要停止地面和洞穴进程
     *
     * @return 执行信息
     */
    public List<String> updateGame() {
        //优雅关闭
        this.shutdownMaster();
        this.shutdownCaves();
        //检查地面与洞穴是否已经关闭，如果10秒之内还没有关闭就强制关闭
        if (this.getMasterStatus()) {
            //运行中 睡眠
            for (int i = 0; this.getMasterStatus(); i++) {
                if (i == MAX_SLEEP_SECOND) {
                    break;
                } else {
                    this.sleep(1);
                }
            }
        }
        if (this.getCavesStatus()) {
            //运行中 睡眠
            for (int i = 0; this.getCavesStatus(); i++) {
                if (i == MAX_SLEEP_SECOND) {
                    break;
                } else {
                    this.sleep(1);
                }
            }
        }
        this.stopMaster();
        this.stopCaves();
        return ShellUtil.runShell(DstConstant.UPDATE_GAME_CMD);
    }

    /**
     * 睡眠
     * @param seconds 秒
     */
    public void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error("睡眠异常：",e);
        }
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
        masterBroadcast.append("screen -S \""+ DstConstant.SCREEN_WORK_MASTER_NAME +"\" -p 0 -X stuff \"c_announce(\\\"");
        masterBroadcast.append(message);
        masterBroadcast.append("\\\")\\n\"");
        //发送地面广播
        ShellUtil.execShellBin(masterBroadcast.toString());
       /* StringBuilder CavesBroadcast = new StringBuilder();
        CavesBroadcast.append("screen -S \"DST_CAVES\" -p 0 -X stuff \"c_announce(\\\"");
        CavesBroadcast.append(message);
        CavesBroadcast.append("\\\")\\n\"");
        //发送洞穴广播
        ShellUtil.execShellBin(CavesBroadcast.toString());*/
    }

    /**
     * 踢出玩家
     * @param userId klei的userId
     */
    public void kickPlayer(String userId) {
        String masterCMD = "screen -S \"" + DstConstant.SCREEN_WORK_MASTER_NAME + "\" -p 0 -X stuff \"TheNet:Kick(\\\"" + userId + "\\\")\\n\"";
        String cavesCMD = "screen -S \"" + DstConstant.SCREEN_WORK_CAVES_NAME + "\" -p 0 -X stuff \"TheNet:Kick(\\\"" + userId + "\\\")\\n\"";
        ShellUtil.execShellBin(masterCMD);
        ShellUtil.execShellBin(cavesCMD);
    }


    /**
     * 回滚指定的天数
     * @param dayNum 1-5天
     */
    public void rollback(int dayNum){
        String masterCMD = "screen -S \"" + DstConstant.SCREEN_WORK_MASTER_NAME + "\" -p 0 -X stuff \"c_rollback(" + dayNum + ")\\n\"";
        String cavesCMD = "screen -S \"" + DstConstant.SCREEN_WORK_CAVES_NAME + "\" -p 0 -X stuff \"c_rollback(" + dayNum + ")\\n\"";
        ShellUtil.execShellBin(masterCMD);
        ShellUtil.execShellBin(cavesCMD);
    }


    /**
     * 重置世界
     */
    public void regenerate(){
        String masterCMD = "screen -S \"" + DstConstant.SCREEN_WORK_MASTER_NAME + "\" -p 0 -X stuff \"c_regenerateworld()\\n\"";
        String cavesCMD = "screen -S \"" + DstConstant.SCREEN_WORK_CAVES_NAME + "\" -p 0 -X stuff \"c_regenerateworld()\\n\"";
        ShellUtil.execShellBin(masterCMD);
        ShellUtil.execShellBin(cavesCMD);
    }


    /**
     * 解析日志获取玩家信息
     * [14:18:00]: playerlist 1621253438444 [0] KU_c1gvcIl4 [Host]
     * [14:18:00]: playerlist 1621253438444 [1] KU_***** nickname wendy
     * @return ku_** 昵称 角色
     */
    public List<String> getPlayerList() throws Exception{
        String playerPrefix = "KU_";
        String host = "[Host]";
        String timeMillis = System.currentTimeMillis()+"";
        String cmd = DstConstant.MASTER_PLAYLIST_CMD.replace("99999999",timeMillis);
        ShellUtil.runShell(cmd);
        //睡眠一秒
        TimeUnit.SECONDS.sleep(1);
        List<String> dstLog = systemService.getDstLog(DstLogTypeEnum.MASTER_LOG.type, 100);
        List<String> playList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dstLog)){
            dstLog.forEach(e->{
                if (e.contains(timeMillis)){
                    if (e.contains(playerPrefix)){
                        String tmp = e.substring(e.indexOf(playerPrefix));
                        if (!tmp.contains(host)){
                            playList.add(tmp);
                        }
                    }
                }
            });
        }
        return playList;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
}
