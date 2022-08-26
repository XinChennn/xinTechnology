package cn.ixinjiu.service;

import cn.ixinjiu.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by XinChen on 2022/8/23
 *
 * @Description : TODO  userService
 */
@Service
public class UserService {
    public User getUser(String userid, String password) {
        if ("admin".equals(userid) && "admin".equals(password)) {
            User user = new User();
            user.setUserID("admin");
            user.setUserName("admin");
            user.setPassWord("admin");
            return user;
        } else {
            return null;
        }
    }

    public User getUser(String userid) {
        if ("admin".equals(userid)) {
            User user = new User();
            user.setUserID("admin");
            user.setUserName("admin");
            user.setPassWord("admin");
            return user;
        } else {
            return null;
        }
    }
}
