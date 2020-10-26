package com.tugos.dst.admin.service;

import com.google.common.collect.Lists;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.utils.FileUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author qinming
 * @date 2020-10-25 21:46:53
 * <p> 系统服务 </p>
 */
@Service
public class SystemService {

    /**
     * 拉取dst游戏日志
     * @param type 0地面日志 ，1 洞穴日志
     * @param rowNum 日志的行数，从后开始取
     * @return 日志
     */
    public List<String> getDstLog(Integer type, Integer rowNum) {
        String path;
        if (type == 1) {
            //洞穴日志存放位置
            path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_CAVES_SERVER_LOG_PATH;
        } else {
            path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_MASTER_SERVER_LOG_PATH;
        }
        File file = new File(path);
        List<String> result = FileUtils.readLastNLine(file, rowNum);
        if (CollectionUtils.isEmpty(result)){
            result = Lists.newArrayList("未找到日志，日志的路径为:"+path);
        }
        return result;
    }

}
