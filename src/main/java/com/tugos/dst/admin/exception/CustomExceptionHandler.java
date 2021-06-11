package com.tugos.dst.admin.exception;

import com.tugos.dst.admin.common.ResultCodeEnum;
import com.tugos.dst.admin.common.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qinming
 * @date 2020-10-03 21:16:30
 * <p> 无 </p>
 */
@Controller
@Slf4j
public class CustomExceptionHandler implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private static final Integer ERROR_START_404 = 404;
    private static final Integer ERROR_START_405 = 405;
    private static final Integer ERROR_START_500 = 500;


    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * web页面错误处理
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorPageHandler(Model model, HttpServletResponse response) {
        int status = response.getStatus();
        String errorMsg = ResultCodeEnum.SYSTEM_ERR.getMessage();
        if (status == ERROR_START_404) {
            errorMsg =  ResultCodeEnum.REQUEST_NOT_FOUND.getMessage();
        }
        model.addAttribute("statusCode", status);
        model.addAttribute("msg", errorMsg);
        return "system/main/error";
    }

    /**
     * 除web页面外的错误处理，比如json/xml等
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ResultVO<String> errorApiHandler(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        log.debug("处理用户请求异常状态码：{}", status);
        response.setStatus(200);
        if (status == ERROR_START_404) {
            //404
            return ResultVO.fail(ResultCodeEnum.REQUEST_NOT_FOUND);
        } else if (status == ERROR_START_405) {
            //405
            return ResultVO.fail(ResultCodeEnum.METHOD_NOT_ALLOWED);
        } else if (status == ERROR_START_500) {
            //500
            return ResultVO.fail(ResultCodeEnum.SYSTEM_ERR);
        } else {
            //其他异常
            return ResultVO.fail(ResultCodeEnum.FAILURE);
        }
    }


}
