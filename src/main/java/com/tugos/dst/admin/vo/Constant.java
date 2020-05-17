package com.tugos.dst.admin.vo;

public class Constant {


    public static final String START_MASTER = "startmaster.sh";


    public static final String START_CAVES = "startcaves.sh";

    public static final String STOP_MASTER = "stopmaster.sh";

    public static final String STOP_CAVES = "stopcaves.sh";

    public static final String UPDATE_GAME = "update.sh";




    /**
     * 脚本目录名称
     */
    public static final String SHELL_FILE_NAME;

    /**
     * 脚本绝对路径
     */
    public static final String SHELL_FILE_PATH;

    /**
     * 脚本的项目存放位置
     */
    public static final String SHELL_PROJECT_PATH = "shell/";

    static {
        String fileName = "shell";
        String projectPath = System.getProperty("user.dir");
        SHELL_FILE_PATH = projectPath + "/" +fileName;
        SHELL_FILE_NAME = fileName;
    }


}
