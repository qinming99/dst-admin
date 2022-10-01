package com.tugos.dst.admin.vo;

import lombok.Data;

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
    }

}
