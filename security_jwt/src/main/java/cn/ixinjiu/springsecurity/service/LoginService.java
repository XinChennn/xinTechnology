package cn.ixinjiu.springsecurity.service;

import cn.ixinjiu.springsecurity.model.LoginDetail;
import cn.ixinjiu.springsecurity.model.TokenDetail;

/**
 * Created by XinChen on 2022/8/25
 *
 * @Description : TODO
 */
public interface LoginService {

    LoginDetail getLoginDetail(String username);

    String generateToken(TokenDetail tokenDetail);

}
