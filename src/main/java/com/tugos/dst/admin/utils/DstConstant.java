package com.tugos.dst.admin.utils;

import org.apache.commons.lang3.SystemUtils;

/**
 * 饥荒文件常量池
 */
public final class DstConstant {

    public static final String INSTALL_DST = "install.sh";
    public static final String DST_START = "dstStart.sh";

    /**
     * 启动脚本的存放路径 ~/dst/bin
     */
    public static final String START_DST_BIN_PATH = "/dst/bin";
    /**
     * 启动地面脚本的名称
     */
    public static final String START_MASTER_SHELL_NAME = "overworld.sh";

    /**
     * 启动洞穴脚本的名称
     */
    public static final String START_CAVES_SHELL_NAME = "cave.sh";

    /**
     * 启动地面进程命令 设置名称为 DST_MASTER
     */
    public static final String START_MASTER_CMD = "cd ~/dst/bin/ ; screen -d -m -S \"DST_MASTER\"  sh overworld.sh ;";

    /**
     * 启动洞穴进程命令 设置名称为 DST_CAVES
     */
    public static final String START_CAVES_CMD = "cd ~/dst/bin/ ; screen -d -m -S \"DST_CAVES\"  sh cave.sh ;";

    /**
     * 检查目前所有的screen作业，并删除已经无法使用的screen作业
     */
    public static final String CLEAR_SCREEN_CMD = "screen -wipe ";

    /**
     * 杀死地面进程命令
     */
    public static final String STOP_MASTER_CMD = "ps -ef | grep -v grep |grep 'Master' |sed -n '1P'|awk '{print $2}' |xargs kill -9";

    /**
     * 杀死洞穴进程命令
     */
    public static final String STOP_CAVES_CMD = "ps -ef | grep -v grep |grep 'Caves' |sed -n '1P'|awk '{print $2}' |xargs kill -9";

    /**
     * 更新游戏目录
     */
    public static final String UPDATE_GAME_CMD = "cd ~/steamcmd ; ./steamcmd.sh +login anonymous +force_install_dir ~/dst +app_update 343050 validate +quit";

    /**
     * 删除地面游戏记录
     */
    public static final String DEL_RECORD_MASTER_CMD = "rm -r ~/.klei/DoNotStarveTogether/MyDediServer/Master/save";

    /**
     * 删除地面游戏记录
     */
    public static final String DEL_RECORD_CAVES_CMD = "rm -r ~/.klei/DoNotStarveTogether/MyDediServer/Caves/save";

    public static final String DST_MASTER = "Master";

    public static final String DST_CAVES = "Caves";

    /**
     * 单斜杠
     */
    public static final String SINGLE_SLASH = "/";

    /**
     * 备份的存档文件的扩展名
     */
    public static final String BACKUP_FILE_EXTENSION = ".tar";

    /**
     * 游戏文档
     */
    public static final String DST_DOC_PATH = ".klei/DoNotStarveTogether";

    /**
     * 饥荒游戏用户存档位置
     */
    public static final String DST_USER_GAME_CONFG_PATH = "/.klei/DoNotStarveTogether/MyDediServer";

    /**
     * 饥荒游戏存档路径
     */
    public static final String DST_USER_SAVE_FILE = "save";

    /**
     * 游戏配置的名称
     */
    public static final String DST_USER_SERVER_INI_NAME = "server.ini";

    /**
     * 游戏房间设置的文件名称
     */
    public static final String DST_USER_CLUSTER_INI_NAME = "cluster.ini";

    /**
     * token设置文件
     */
    public static final String DST_USER_CLUSTER_TOKEN = "cluster_token.txt";

    /**
     * 地上mod保存地址
     */
    public static final String DST_USER_GAME_MASTER_MOD_PATH = ".klei/DoNotStarveTogether/MyDediServer/Master/modoverrides.lua";

    /**
     * 洞穴mod保存位置
     */
    public static final String DST_USER_GAME_CAVES_MOD_PATH = ".klei/DoNotStarveTogether/MyDediServer/Caves/modoverrides.lua";

    /**
     * 地面地图配置地址
     */
    public static final String DST_USER_GAME_MASTER_MAP_PATH = ".klei/DoNotStarveTogether/MyDediServer/Master/leveldataoverride.lua";

    /**
     * 洞穴地图配置地址
     */
    public static final String DST_USER_GAME_CAVES_MAP_PATH = ".klei/DoNotStarveTogether/MyDediServer/Caves/leveldataoverride.lua";

    /**
     * 游戏配置文件
     */
    public static final String DST_USER_GAME_CONFIG_PATH = ".klei/DoNotStarveTogether/MyDediServer/cluster.ini";

    /**
     * 地面游戏运行日志位置
     */
    public static final String DST_MASTER_SERVER_LOG_PATH = ".klei/DoNotStarveTogether/MyDediServer/Master/server_log.txt";

    /**
     * 地面用户聊天信息
     */
    public static final String DST_MASTER_SERVER_CHAT_LOG_PATH = ".klei/DoNotStarveTogether/MyDediServer/Master/server_chat_log.txt";

    /**
     * 洞穴游戏运行日志位置
     */
    public static final String DST_CAVES_SERVER_LOG_PATH = ".klei/DoNotStarveTogether/MyDediServer/Caves/server_log.txt";

    /**
     * 管理员存储位置
     */
    public static final String DST_ADMIN_LIST_PATH = ".klei/DoNotStarveTogether/MyDediServer/adminlist.txt";

    /**
     * 黑名单存储位置
     */
    public static final String DST_PLAYER_BLOCK_LIST_PATH = ".klei/DoNotStarveTogether/MyDediServer/blocklist.txt";

    /**
     * 游戏mod设置
     */
    public static final String DST_MOD_SETTING_PATH = "dst/mods/dedicated_server_mods_setup.lua";

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
     * 项目的数据
     */
    public static final String DST_ADMIN_JSON = ".dst_admin_db.json";

    /**
     * 系统根目录mac:/Users/qinming , ubuntu: /home/ubuntu ,游戏将按照在该目录下
     */
    public static final String ROOT_PATH;

    static {
        String fileName = "shell";
        String projectPath = System.getProperty("user.dir");
        SHELL_FILE_PATH = projectPath + "/" +fileName;
        SHELL_FILE_NAME = fileName;
        ROOT_PATH = SystemUtils.getUserHome().getPath();
    }


}
