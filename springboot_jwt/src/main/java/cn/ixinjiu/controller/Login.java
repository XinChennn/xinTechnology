package cn.ixinjiu.controller;

import cn.ixinjiu.annotation.LoginToken;
import cn.ixinjiu.entity.User;
import cn.ixinjiu.service.TokenService;
import cn.ixinjiu.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by XinChen on 2022/8/23
 *
 * @Description : TODO  登录Controller
 */
@RestController
public class Login {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    /**  structure
     * TODO     login
     * /login
     * @param username
     * @param password
     * @return java.lang.Object
     */
    @PostMapping("login")
    public Object login(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        User user = userService.getUser(username, password);
        if (user == null) {
            jsonObject.put("message", "login fail");
            return jsonObject;
        } else {
            String token = tokenService.getToken(user);
            jsonObject.put("token", token);
            jsonObject.put("user", user);
            return jsonObject;
        }
    }

    @LoginToken
    @PostMapping("/getMessage")
    public String getMessage() {
        return "login success!";
    }
}
