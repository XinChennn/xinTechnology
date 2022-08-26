package cn.ixinjiu.springsecurity.model;

import cn.ixinjiu.springsecurity.model.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Date;

/**
 * Created by XinChen on 2022/8/25
 *
 * @Description : TODO
 */
public class SecurityModelFactory {

    public static UserDetailImpl create(User user) {
        Collection<? extends GrantedAuthority> authorities;
        try {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities());
        } catch (Exception e) {
            authorities = null;
        }

        Date lastPasswordReset = new Date();
        lastPasswordReset.setTime(user.getLastPasswordChange());
        return new UserDetailImpl(
                user.getUsername(),
                user.getUsername(),
                user.getPassword(),
                lastPasswordReset,
                authorities,
                user.enable()
        );
    }

}
