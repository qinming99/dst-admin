package com.tugos.dst.admin.vo;

import com.tugos.dst.admin.utils.ShellUtil;

public class Constant {


    public static final String START_MASTER = "startmaster.sh";


    public static final String START_CAVES = "startcaves.sh";

    public static final String STOP_MASTER = "stopmaster.sh";

    public static final String STOP_CAVES = "stopcaves.sh";

    public static final String UPDATE_GAME = "update.sh";

    public static final String DEL_RECORD = "delrecord.sh";

    public static final String DST_MASTER = "Master";

    public static final String DST_CAVES = "Caves";

    /**
     * 游戏文档
     */
    public static final String DST_DOC_PATH = ".klei/DoNotStarveTogether";

    /**
     * 饥荒游戏用户存档位置
     */
    public static final String DST_USER_GAME_CONFG_PATH = "/.klei/DoNotStarveTogether/MyDediServer";


    /**
     * 脚本目录名称 shell
     */
    public static final String SHELL_FILE_NAME;

    /**
     * 脚本绝对路径 /Users/qinming/Documents/project/dst-admin/shell
     */
    public static final String SHELL_FILE_PATH;

    /**
     * 脚本的项目存放位置
     */
    public static final String SHELL_PROJECT_PATH = "shell/";

    /**
     * 系统根目录mac:/Users/qinming , ubuntu: /home/ubuntu ,游戏将按照在该目录下
     */
    public static final String ROOT_PATH;

    static {
        String fileName = "shell";
        String projectPath = System.getProperty("user.dir");
        SHELL_FILE_PATH = projectPath + "/" +fileName;
        SHELL_FILE_NAME = fileName;
        ROOT_PATH = ShellUtil.runShell("echo ~").get(0);
    }


}
