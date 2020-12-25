package com.tugos.dst.admin.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * @author qinming
 * @date 2020-12-19 16:55:43
 * <p> 国际化状态码转换资源 </p>
 */
@PropertySource(value = {"classpath:i18n/messages*.properties"})
public class I18nResourcesConfig {

    /**
     * 国际化信息
     */
    private static final Map<String, ResourceBundle> MESSAGES = new HashMap<>();

    /**
     * 获取国际化信息
     */
    public static String getMessage(String key) {
        //获取当前语言环境
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle message = MESSAGES.get(locale.getLanguage());
        if (message == null) {
            synchronized (MESSAGES) {
                //在这里读取配置信息
                message = MESSAGES.get(locale.getLanguage());
                if (message == null) {
                    message = ResourceBundle.getBundle("i18n/messages", locale);
                    MESSAGES.put(locale.getLanguage(), message);
                }
            }
        }
        return message.getString(key);
    }

    /**
     * 清除国际化信息
     */
    public static void flushMessage() {
        MESSAGES.clear();
    }
}
