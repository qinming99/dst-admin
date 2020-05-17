package com.tugos.dst.admin.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    @Autowired
    private ShellService shellService;


    public boolean getMasterStatus() {
        String masterProcessNum = shellService.getMasterProcessNum();
        return StringUtils.isNotBlank(masterProcessNum);
    }

    public boolean getCavesStatus() {
        String cavesProcessNum = shellService.getCavesProcessNum();
        return StringUtils.isNoneBlank(cavesProcessNum);
    }


    public void start(Integer type) {
        if (type == null || type == 0) {
            shellService.startMaster();
            shellService.startCaves();
            return;
        }
        if (type == 1) {
            shellService.startMaster();

        } else {
            shellService.startCaves();

        }
    }


    public void stop(Integer type) {
        if (type == null || type == 0) {
            shellService.stopMaster();
            shellService.stopCaves();
            return;
        }
        if (type == 1) {
            shellService.stopMaster();
        } else {
            shellService.stopCaves();
        }
    }


}
