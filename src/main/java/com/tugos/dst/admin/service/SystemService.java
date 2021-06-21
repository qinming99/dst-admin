package com.tugos.dst.admin.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.tugos.dst.admin.config.I18nResourcesConfig;
import com.tugos.dst.admin.enums.DstLogTypeEnum;
import com.tugos.dst.admin.utils.*;
import com.tugos.dst.admin.vo.GamePortVO;
import com.tugos.dst.admin.vo.ScheduleVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @author qinming
 * @date 2020-10-25 21:46:53
 * <p> 系统服务 </p>
 */
@Service
public class SystemService {

    /**
     * 拉取dst游戏日志
     * @param type 0地面日志 ，1 洞穴日志 2 玩家聊天记录
     * @param rowNum 日志的行数，从后开始取
     * @return 日志
     */
    public List<String> getDstLog(Integer type, Integer rowNum) {
        String path;
        switch (Objects.requireNonNull(DstLogTypeEnum.get(type))){
            case CAVES_LOG:
                //洞穴
                path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_CAVES_SERVER_LOG_PATH;
                break;
            case CHAT_LOG:
                //聊天
                path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_MASTER_SERVER_CHAT_LOG_PATH;
                break;
            default:
                //地面
                path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_MASTER_SERVER_LOG_PATH;
        }
        File file = new File(path);
        List<String> result = FileUtils.readLastNLine(file, rowNum);
        if (CollectionUtils.isEmpty(result)){
            result = Lists.newArrayList(I18nResourcesConfig.getMessage("tip.log.not.exist")+":"+path);
        }
        return result;
    }

    /**
     * 获取任务时间列表
     * @return 数据
     */
    public ScheduleVO getScheduleList() {
        ScheduleVO data = new ScheduleVO();
        Set<String> updateSet = DstConfigData.SCHEDULE_UPDATE_MAP.keySet();
        if (CollectionUtils.isNotEmpty(updateSet)){
            List<ScheduleVO.InnerData> updateTimeList = new ArrayList<>();
            updateSet.forEach(e->{
                ScheduleVO.InnerData innerData = new ScheduleVO.InnerData();
                innerData.setTime(e);
                innerData.setCount(DstConfigData.SCHEDULE_UPDATE_MAP.get(e));
                updateTimeList.add(innerData);
            });
            data.setUpdateTimeList(updateTimeList);
        }
        Set<String> backupSet = DstConfigData.SCHEDULE_BACKUP_MAP.keySet();
        if (CollectionUtils.isNotEmpty(backupSet)){
            List<ScheduleVO.InnerData> backupTimeList = new ArrayList<>();
            backupSet.forEach(e->{
                ScheduleVO.InnerData innerData = new ScheduleVO.InnerData();
                innerData.setTime(e);
                innerData.setCount(DstConfigData.SCHEDULE_BACKUP_MAP.get(e));
                backupTimeList.add(innerData);
            });
            data.setBackupTimeList(backupTimeList);
        }
        data.setNotStartMaster(DstConfigData.notStartMaster);
        data.setNotStartCaves(DstConfigData.notStartCaves);
        data.setSmartUpdate(DstConfigData.smartUpdate);
        return data;
    }

    /**
     * 将任务时间写入缓存中
     * @param vo 提交的数据
     */
    public void saveSchedule(ScheduleVO vo) {
        DstConfigData.clearAllData();
        if (CollectionUtils.isNotEmpty(vo.getBackupTimeList())){
            //写入缓存
            vo.getBackupTimeList().forEach(e->{
                if (StringUtils.isNotBlank(e.getTime())){
                    DateTime parse = DateUtil.parse(e.getTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN);
                    String format = DateUtil.format(parse, DatePattern.NORM_TIME_PATTERN);
                    DstConfigData.SCHEDULE_BACKUP_MAP.put(format,e.getCount());
                }
            });
        }
        if (CollectionUtils.isNotEmpty(vo.getUpdateTimeList())){
            //写入缓存
            vo.getUpdateTimeList().forEach(e->{
                if (StringUtils.isNotBlank(e.getTime())) {
                    DateTime parse = DateUtil.parse(e.getTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN);
                    String format = DateUtil.format(parse, DatePattern.NORM_TIME_PATTERN);
                    DstConfigData.SCHEDULE_UPDATE_MAP.put(format, e.getCount());
                }
            });
        }
        if (vo.getNotStartCaves() != null) {
            DstConfigData.notStartMaster = vo.getNotStartMaster();
        }else {
            DstConfigData.notStartMaster = false;
        }
        if (vo.getNotStartCaves() != null) {
            DstConfigData.notStartCaves = vo.getNotStartCaves();
        }else {
            DstConfigData.notStartCaves = false;
        }
        if (vo.getSmartUpdate() != null) {
            DstConfigData.smartUpdate = vo.getSmartUpdate();
        }else {
            DstConfigData.smartUpdate = false;
        }
    }

    /**
     * 获取服务器的版本号
     * @return 版本号
     */
    public Map<String,String> getVersion() {
        String steamVersion = DstVersionUtils.getSteamVersion();
        String localVersion = DstVersionUtils.getLocalVersion();
        Map<String,String> map = new HashMap<>(16);
        map.put("steamVersion",steamVersion);
        map.put("localVersion",localVersion);
        return map;
    }


    public GamePortVO getGamePort(){
        GamePortVO gamePortVO = new GamePortVO();
        gamePortVO.setMasterPort(DstConfigData.masterPort);
        gamePortVO.setGroundPort(DstConfigData.groundPort);
        gamePortVO.setCavesPort(DstConfigData.cavesPort);
        return gamePortVO;
    }

    public void saveGamePort(GamePortVO gamePortVO) {
        DstConfigData.masterPort = gamePortVO.getMasterPort();
        DstConfigData.groundPort = gamePortVO.getGroundPort();
        DstConfigData.cavesPort = gamePortVO.getCavesPort();
        DBUtils.saveDataToFile();
    }
}
