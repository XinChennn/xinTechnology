package cn.ixinjiu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by XinChen on 2022/8/23
 *
 * @Description : TODO  用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userID;
    private String userName;
    private String passWord;
}
