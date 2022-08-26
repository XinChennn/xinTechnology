package cn.ixinjiu.springsecurity.model;

/**
 * Created by XinChen on 2022/8/25
 *
 * @Description : TODO
 */
public class TokenDetailImpl implements TokenDetail {

    private final String username;

    public TokenDetailImpl(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
