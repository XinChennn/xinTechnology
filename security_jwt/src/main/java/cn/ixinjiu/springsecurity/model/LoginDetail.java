package cn.ixinjiu.springsecurity.model;

/**
 * Created by XinChen on 2022/8/25
 *
 * @Description : TODO
 */
public interface LoginDetail {

    String getUsername();
    String getPassword();
    boolean enable();
}
