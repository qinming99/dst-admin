package com.tugos.dst.admin.config;


import com.tugos.dst.admin.vo.Constant;
import com.tugos.dst.admin.utils.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;



@Component
@Slf4j
public class ShellConfig {

    @PostConstruct
    public void initShell() throws Exception {
        File file = new File(Constant.SHELL_PROJECT_PATH);
        if (!file.exists()) {
            //创建存放脚本的目录
            file.mkdir();
        }
        //释放脚本并授权
        copyAndChmod(Constant.START_MASTER);
        copyAndChmod(Constant.START_CAVES);
        copyAndChmod(Constant.STOP_MASTER);
        copyAndChmod(Constant.STOP_CAVES);
    }

    /**
     * 释放脚本并授权
     * @param fileName
     * @throws Exception
     */
    private void copyAndChmod(String fileName) throws Exception{
        boolean copy = fileShellCopy(fileName);
        if (copy){
            chmod(fileName);
        }
    }

    /**
     * 给脚本授权
     *
     * @param fileName
     */
    public static void chmod(String fileName) {
        StringBuffer sb = new StringBuffer();
        String shellFilePath = Constant.SHELL_FILE_PATH;
        sb.append("cd ").append(shellFilePath).append(" ;");
        sb.append("chmod +x ./").append(fileName);
        ShellUtil.runShell(sb.toString());
        log.info("给{}目录下的{}文件授权成功", Constant.SHELL_FILE_PATH, fileName);
    }

    /**
     * 将项目资源下的脚本拷贝到磁盘
     *
     * @param fileName
     * @throws Exception
     */
    public static boolean fileShellCopy(String fileName) throws Exception {
        String filePath = Constant.SHELL_PROJECT_PATH + fileName;
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        InputStream inputStream = classPathResource.getInputStream();
        //创建新的脚本文件
        File file = new File(filePath);
        if (file.exists()){
            //存在不管它
            log.info("脚本已经存在,{}",fileName);
            return false;
        }
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

}
