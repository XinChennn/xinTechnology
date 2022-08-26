package cn.ixinjiu.springsecurity.dao;

import cn.ixinjiu.springsecurity.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by XinChen on 2022/8/25
 *
 * @Description : TODO
 */
@Mapper
@Component
public interface UserMapper {


    User getUserFromDatabase(@Param("username") String username);

}
