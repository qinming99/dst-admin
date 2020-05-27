package com.tugos.dst.admin.service;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.tugos.dst.admin.utils.Constant;
import com.tugos.dst.admin.utils.FileUtils;
import com.tugos.dst.admin.vo.BackupFileVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BackupService {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static DecimalFormat decimalFormat = new DecimalFormat(".00");



    /**
     * 获取备份文件信息
     *
     * @return 信息列表
     */
    public static List<BackupFileVO> getBackupFileInfo() throws Exception{
        List<BackupFileVO> result = new ArrayList<>();
        String backupPath = Constant.ROOT_PATH + "/" +Constant.DST_DOC_PATH;
        List<String> allFileList = FileUtils.getFiles(backupPath);
        //过滤出所有备份文件压缩包
        List<String> backupFileList = allFileList.stream()
                .filter(e -> e.contains(".tar")).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(backupFileList)) {
            for (String e : backupFileList) {
                BackupFileVO vo = new BackupFileVO();
                File file = new File(e);
                String name = file.getName();
                //文件大小 MB
                float fileSize = file.length() / 1024F / 1024F;
                long lastModified = file.lastModified();
                String time = dateFormat.format(lastModified);
                vo.setCreateTime(time);
                vo.setFileSize(decimalFormat.format(fileSize));
                vo.setFileName(name);
                vo.setTime(DateUtil.parse(vo.getCreateTime(), DatePattern.NORM_DATETIME_FORMAT));
                result.add(vo);
            }
        }
        if (CollectionUtils.isNotEmpty(result)){
            //排序，降序
            result.sort((o1,o2)-> DateUtil.compare(o1.getTime(),o2.getTime()));
            Collections.reverse(result);

        }
        return result;
    }

    /**
     * 删除备份
     * @param fileName 文件名字
     */
    public boolean del(String fileName) {
        String basePath = Constant.ROOT_PATH + "/" +Constant.DST_DOC_PATH;
        String filePath = basePath + "/" + fileName;
        File file = new File(filePath);
        if (file.exists()){
            //删除
            return file.delete();
        }
        return false;
    }
}
