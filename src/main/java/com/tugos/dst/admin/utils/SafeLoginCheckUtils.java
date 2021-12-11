package com.tugos.dst.admin.utils;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qinming
 * @date 2021-12-10 22:51:28
 * <p> 登陆安全检测工具 </p>
 */
public final class SafeLoginCheckUtils {

    /**
     * 登陆错误信息
     */
    final private static Map<String, LoginErrorVO> LOGIN_ERROR_MAP = new ConcurrentHashMap<>(16);

    /**
     * 允许的最大错误次数
     */
    final public static int MAX_ERROR_TIMES = 5;

    /**
     * 冻结时间
     */
    public static final int MAX_EFFICIENT_MINUTE = 30;

    /**
     * 是否允许登陆
     *
     * @param request 请求
     * @return true 允许
     */
    public static boolean isAllowLogin(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        if (LOGIN_ERROR_MAP.containsKey(ip)) {
            LoginErrorVO loginErrorVO = LOGIN_ERROR_MAP.get(ip);
            if (loginErrorVO.efficientTime.compareTo(new Date()) > 0) {
                return loginErrorVO.getCount().intValue() < MAX_ERROR_TIMES;
            }
        }
        return true;
    }

    /**
     * 登陆错误记录
     *
     * @param request 请求
     */
    public static void loginErrorRecord(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        if (LOGIN_ERROR_MAP.containsKey(ip)) {
            LoginErrorVO loginErrorVO = LOGIN_ERROR_MAP.get(ip);
            if (loginErrorVO.getEfficientTime().compareTo(new Date()) > 0) {
                //还没有失效
                loginErrorVO.recordCount();
                loginErrorVO.setLastTime(new Date());
            } else {
                LOGIN_ERROR_MAP.put(ip, new LoginErrorVO(ip));
            }
        } else {
            LOGIN_ERROR_MAP.put(ip, new LoginErrorVO(ip));
        }
    }

    /**
     * 获取最大尝试次数
     *
     * @param request 请求
     * @return 次数
     */
    public static int getRemainingTimes(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        if (LOGIN_ERROR_MAP.containsKey(ip)) {
            return MAX_ERROR_TIMES - LOGIN_ERROR_MAP.get(ip).getCount().intValue();
        }
        return MAX_ERROR_TIMES - 1;
    }

    /**
     * 清理错误信息
     * @param request 请求
     */
    public static void cleanErrorRecord(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        LOGIN_ERROR_MAP.remove(ip);
    }


    @Data
    private static class LoginErrorVO {

        /**
         * 错误ip
         */
        private String ip;

        /**
         * 错误次数
         */
        private AtomicInteger count;

        /**
         * 创建时间
         */
        private Date createTime;

        /**
         * 上一次时间
         */
        private Date lastTime;

        /**
         * 有效时间
         */
        private Date efficientTime;

        public LoginErrorVO(String ip) {
            this.ip = ip;
            this.count = new AtomicInteger(1);
            this.createTime = new Date();
            this.lastTime = new Date();
            this.efficientTime = DateUtils.addMinutes(new Date(), MAX_EFFICIENT_MINUTE);
        }

        public void recordCount() {
            this.lastTime = new Date();
            this.count.incrementAndGet();
        }

    }

}
