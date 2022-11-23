package com.tugos.dst.admin.vo;

import lombok.Data;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author qinming
 * @date 2022-10-01 15:54:37
 * <p> 存档快照信息 </p>
 */
@Data
public class GameSnapshotVO {

    private String original;

    private String playDay;

    /**
     * spring summer autumn winter
     */
    private String season;

    public String getSeasonChinese() {
        Locale locale = LocaleContextHolder.getLocale();
        if (Locale.CHINA.getLanguage().equals(locale.getLanguage())) {
            switch (this.season) {
                case "spring":
                    return "春天";
                case "summer":
                    return "夏天";
                case "autumn":
                    return "秋天";
                case "winter":
                    return "冬天";
                default:
                    return "未知季节";
            }
        }else {
            return this.season;
        }
    }

}
