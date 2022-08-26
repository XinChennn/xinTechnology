package cn.ixinjiu.service;

import cn.ixinjiu.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by XinChen on 2022/8/23
 *
 * @Description : TODO  Token生成
 */
@Service
public class TokenService {
    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    public String getToken(User user) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token="";
        token= JWT.create().withAudience(user.getUserID()) // 将 user id 保存到 token 里面
                .withExpiresAt(date) // 五分钟后token过期
                .sign(Algorithm.HMAC256(user.getPassWord())); // 以 password 作为 token 的密钥
        return token;
    }
}
