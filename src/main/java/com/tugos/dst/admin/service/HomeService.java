package com.tugos.dst.admin.service;


import cn.hutool.core.date.DateUtil;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.config.I18nResourcesConfig;
import com.tugos.dst.admin.entity.Server;
import com.tugos.dst.admin.enums.StartTypeEnum;
import com.tugos.dst.admin.enums.StopTypeEnum;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.vo.BackupFileVO;
import com.tugos.dst.admin.vo.DstServerInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author qinming
 * @date 2020-05-17
 * <p> 管理游戏服务 </p>
 */
@Service
@Slf4j
public class HomeService {

    private ShellService shellService;

    private BackupService backupService;

    /**
     * 启动服务器进程
     *
     * @param type 0 启动所有 1 启动地面 2 启动洞穴
     */
    public ResultVO<String> start(Integer type) {
        if (!this.checkIsInstallDst()) {
            //未安装dst
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.home.start.error"));
        }
        StartTypeEnum typeEnum = StartTypeEnum.get(type);
        Objects.requireNonNull(typeEnum);
        switch (typeEnum) {
            case START_ALL:
                //启动所有 先停止所有，在启动
                shellService.stopMaster();
                shellService.stopCaves();
                shellService.startMaster();
                shellService.startCaves();
                break;
            case START_MASTER:
                //启动地面
                shellService.stopMaster();
                shellService.startMaster();
                break;
            case START_CAVES:
                //启动洞穴
                shellService.stopCaves();
                shellService.startCaves();
                break;
            default:
        }
        //清理screen的无效作业
        shellService.clearScreen();
        return ResultVO.success();
    }


    /**
     * 停止服务器进程 优雅关闭
     *
     * @param type 0 停止所有 1 停止地面 2 停止洞穴
     */
    public ResultVO<String> stop(Integer type) {
        StopTypeEnum typeEnum = StopTypeEnum.get(type);
        Objects.requireNonNull(typeEnum);
        switch (typeEnum) {
            case STOP_ALL:
                //停止所有,优雅关闭，10秒还未关闭强制关闭
                shellService.elegantShutdownMaster();
                shellService.elegantShutdownCaves();
                break;
            case STOP_MASTER:
                //停止地面 优雅关闭，10秒还未关闭强制关闭
                shellService.elegantShutdownMaster();
                break;
            case STOP_CAVES:
                //停止洞穴 优雅关闭，10秒还未关闭强制关闭
                shellService.elegantShutdownCaves();
                break;
            default:
        }
        return ResultVO.success();
    }

    /**
     * 校验服务器是否已经安装了dst，检查启动程序dontstarve_dedicated_server_nullrenderer 是否存在
     *
     * @return true 安装了
     */
    private boolean checkIsInstallDst() {
        String startProgram = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.START_DST_BIN_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_START_PROGRAM;
        File masterFile = new File(startProgram);
        if (masterFile.exists()) {
            return  true;
        }else {
            log.warn("未找到启动程序：{}",startProgram);
            return false;
        }
    }

    /**
     * 判断token时候存在
     * @return true存在
     */
    private boolean checkTokenIsExists() {
        String tokenPath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH
                + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_CLUSTER_TOKEN;
        File file = new File(tokenPath);
        return file.exists();
    }

    /**
     * 获取服务器的信息
     * 包括饥荒状态和硬件信息
     */
    public DstServerInfoVO getSystemInfo() throws Exception {
        DstServerInfoVO data = new DstServerInfoVO();
        //获取硬件信息
        Server server = new Server();
        server.copyTo();
        data.setCpu(server.getCpu());
        data.setMem(server.getMem());
        //饥荒状态
        data.setMasterStatus(shellService.getMasterStatus());
        data.setCavesStatus(shellService.getCavesStatus());
        //备份的文件
        List<String> backupList = new ArrayList<>();
        List<BackupFileVO> list = backupService.getBackupFileInfo();
        if (CollectionUtils.isNotEmpty(list)){
            list.forEach(e-> backupList.add(e.getFileName()));
        }
        data.setBackupList(backupList);
        return data;
    }


    /**
     * 更新游戏 需要停止地面和洞穴进程
     */
    public ResultVO<String> updateGame() {
        if (!this.checkIsInstallDst()) {
            //未安装dst
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.home.start.error"));
        }
        shellService.updateGame();
        return ResultVO.success();
    }

    /**
     * 备份游戏存档
     * @param name 存档名称
     * @return 信息
     */
    public ResultVO<String> backup(String name) {
        if (!this.checkGameFileIsExists()) {
            //未安装dst
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.home.backup.error"));
        }
        String weekStr = DateUtil.thisDayOfWeekEnum().toString();
        String fileName;
        if (StringUtils.isNotBlank(name)) {
            fileName = name + ".tar";
        } else {
            //未设置名称
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = sdf.format(new Date());
            format += weekStr;
            fileName = format + ".tar";
        }
        shellService.createBackup(fileName);
        return ResultVO.success();
    }

    /**
     * 校验游戏存档文件是否存在
     * @return true 存在
     */
    private boolean checkGameFileIsExists() {
        String path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_GAME_CONFG_PATH;
        File file = new File(path);
        return file.exists();
    }

    /**
     * 恢复存档 需要暂停游戏，清空之前的记录
     * @param name 备份的文件名称全称
     */
    public ResultVO<String> restore(String name) {
        if (!this.checkBackupIsExists(name)) {
            //未安装dst
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.home.backup.error2")+name);
        }
        //清空在恢复
        this.delRecord();
        //释放打包好的存档文件
        shellService.revertBackup(name);
        return ResultVO.success();
    }

    /**
     * 校验存档文件是否存在
     * @param name 文件名称 全称
     * @return true 存在
     */
    private boolean checkBackupIsExists(String name){
        boolean flag = false;
        if (StringUtils.isNotBlank(name)){
            String path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_DOC_PATH;
            File file = new File(path);
            flag = file.exists();
        }
        return flag;
    }

    /**
     * 清理地面和洞穴游戏进度，需要停止服务
     */
    public void delRecord() {
        shellService.delCavesRecord();
        shellService.delMasterRecord();
    }

    @Autowired
    public void setShellService(ShellService shellService) {
        this.shellService = shellService;
    }

    @Autowired
    public void setBackupService(BackupService backupService) {
        this.backupService = backupService;
    }
}
