package com.tugos.dst.admin.service;


import cn.hutool.core.date.DateUtil;
import com.tugos.dst.admin.enums.StartTypeEnum;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.utils.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class ScheduleService {

    private HomeService homeService;

    private ShellService shellService;



    @Scheduled(cron = "0 0 06 * * ?")
    public void updateGame(){
        log.info("定时更新并重启游戏");
        homeService.updateGame();
        homeService.start(StartTypeEnum.START_ALL.type);
    }


    @Scheduled(cron = "0 0 05,18 * * ?")
    public void backupGame(){
        log.info("定时备份游戏");
        String weekStr = DateUtil.thisDayOfWeekEnum().toChinese();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());
        format += weekStr;
        String fileName = format + "_sys.tar";
        shellService.createBackup(fileName);
    }


    @PostConstruct
    public void initShell() throws Exception {
        //释放脚本并授权
        copyAndChmod(DstConstant.INSTALL_DST);
        copyAndChmod(DstConstant.DST_START);
    }

    /**
     * 释放脚本并授权
     */
    private void copyAndChmod(String fileName) throws Exception {
        boolean copy = fileShellCopy(fileName);
        if (copy) {
            chmod(fileName);
        }
    }

    /**
     * 给脚本授权
     */
    public static void chmod(String fileName) {
        StringBuffer sb = new StringBuffer();
        String shellFilePath = DstConstant.SHELL_FILE_PATH;
        sb.append("cd ").append(shellFilePath).append(" ;");
        sb.append("chmod +x ./").append(fileName);
        ShellUtil.runShell(sb.toString());
        log.info("给{}目录下的{}文件授权成功", DstConstant.SHELL_FILE_PATH, fileName);
    }

    /**
     * 将项目资源下的脚本拷贝到磁盘
     */
    public static boolean fileShellCopy(String fileName) throws Exception {
        //创建新的脚本文件
        File file = new File(fileName);
        if (file.exists()) {
            //存在不管它
            log.info("脚本已经存在,{}", fileName);
            return false;
        }
        ClassPathResource classPathResource = new ClassPathResource(DstConstant.SHELL_PROJECT_PATH + fileName);
        InputStream inputStream = classPathResource.getInputStream();
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        StringBuffer sb = new StringBuffer();
        while ((len = (inputStream.read(buf))) != -1) {
            sb.append(new String(buf, 0, len));
        }
        outputStream.write(sb.toString().getBytes());
        outputStream.flush();
        inputStream.close();
        outputStream.close();
        log.info("拷贝脚本{}成功.....", fileName);
        return true;
    }

    @Autowired
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    @Autowired
    public void setShellService(ShellService shellService) {
        this.shellService = shellService;
    }
}
