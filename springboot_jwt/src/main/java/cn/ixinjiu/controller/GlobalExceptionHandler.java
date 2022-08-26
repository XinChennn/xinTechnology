package cn.ixinjiu.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by XinChen on 2022/8/23
 *
 * @Description : TODO  配置全局异常捕获
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        String message = e.getMessage();
        if (message == null || message.equals("")) {
            message = "Server error";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1000);
        jsonObject.put("message", message);
        return jsonObject;
    }
}
