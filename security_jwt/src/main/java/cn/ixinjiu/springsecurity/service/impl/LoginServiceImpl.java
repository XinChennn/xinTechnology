package cn.ixinjiu.springsecurity.service.impl;

import cn.ixinjiu.springsecurity.dao.UserMapper;
import cn.ixinjiu.springsecurity.model.LoginDetail;
import cn.ixinjiu.springsecurity.model.TokenDetail;
import cn.ixinjiu.springsecurity.service.LoginService;
import cn.ixinjiu.springsecurity.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by XinChen on 2022/8/25
 *
 * @Description : TODO
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;
    private final TokenUtils tokenUtils;

    @Autowired
    public LoginServiceImpl(UserMapper userMapper, TokenUtils tokenUtils) {
        this.userMapper = userMapper;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public LoginDetail getLoginDetail(String username) {
        return userMapper.getUserFromDatabase(username);
    }

    @Override
    public String generateToken(TokenDetail tokenDetail) {
        return tokenUtils.generateToken(tokenDetail);
    }
}
