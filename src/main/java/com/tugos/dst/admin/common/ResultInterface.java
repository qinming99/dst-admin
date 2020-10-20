package com.tugos.dst.admin.common;

/**
 * @author qinming
 * @date 2020-10-03 20:13:48
 * <p> 无 </p>
 */
public interface ResultInterface {

    /**
     * 获取状态编码
     * @return 编码
     */
    int getCode();

    /**
     * 获取提示信息
     * @return 提示信息
     */
    String getMessage();

}
