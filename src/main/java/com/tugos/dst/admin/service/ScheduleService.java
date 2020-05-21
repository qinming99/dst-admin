package com.tugos.dst.admin.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleService {


    @Autowired
    private HomeService homeService;



    @Scheduled(cron = "0 0 06 * * ?")
    public void updateGame(){
        log.info("定时更新并重启游戏");
        homeService.updateGame();
        homeService.start(0);
    }


    @Scheduled(cron = "0 0 05,18 * * ?")
    public void backupGame(){
        log.info("定时备份游戏");
        homeService.backup(null);
    }



}
