# SpringBoot_JWT

## 1. JWT

### 1.1.What's JWT?

`Jwt`全称是：`json web token`。它将用户信息加密到`token`里，服务器不保存任何用户信息。服务器通过使用保存的**密钥**验证`token`的正确性，只要正确即通过验证。

### 1.2.Advantage

- 简洁: 可以通过URL、POST参数或者在HTTP header发送，因为数据量小，传输速度也很快；

- 自包含：负载中可以包含用户所需要的信息，避免了多次查询数据库；
  因为Token是以JSON加密的形式保存在客户端的，所以JWT是跨语言的，原则上任何web形式都支持；

- 不需要在服务端保存会话信息，特别适用于分布式微服务。

### 1.3.Defect

- 无法作废已颁布的令牌；

- 不易应对数据过期。

## 2.Create project

### 2.1.Structrue of the project

![Screenshot 2022-08-23 at 22.15.40.png](/var/folders/3v/2fxvh7f16yq3yj13k17b24r00000gn/T/TemporaryItems/NSIRD_screencaptureui_kd7nkn/Screenshot%202022-08-23%20at%2022.15.40.png)

### 2.2.Dependencies of the project

```java
<!--jwt-->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>3.10.3</version>
        </dependency>
```

### 2.3.User entity and service

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userID;
    private String userName;
    private String passWord;
}
```

```java
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
```

### 2.4.User-defined annotion

```java
// 需要登录才能进行操作的注解
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginToken {
    boolean required() default true;
}
```

```java
// 用来跳过验证的PassToken
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}
```

### 2.5.Registering an interceptor

```java
package cn.ixinjiu.config;

import cn.ixinjiu.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by XinChen on 2022/8/23
 *
 * @Description : TODO  注册拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录

        //注册TestInterceptor拦截器
//        InterceptorRegistration registration = registry.addInterceptor(jwtInterceptor());
//        registration.addPathPatterns("/**");                      //添加拦截路径
//        registration.excludePathPatterns(                         //添加不拦截路径
//            "/**/*.html",            //html静态资源
//            "/**/*.js",              //js静态资源
//            "/**/*.css",             //css静态资源
//            "/**/*.woff",
//            "/**/*.ttf",
//            "/swagger-ui.html"
//        );
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
```

### 2.6.Configure global exception catching

```java
package cn.ixinjiu.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by XinChen on 2022/8/23
 *
 * @Description : TODO  配置全局异常捕获
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        String message = e.getMessage();
        if (message == null || message.equals("")) {
            message = "Server error";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1000);
        jsonObject.put("message", message);
        return jsonObject;
    }
}
```

### 2.7.Login controller

```java
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
```

### 2.8.Token's interceptor

```java
package cn.ixinjiu.interceptor;

import cn.ixinjiu.annotation.LoginToken;
import cn.ixinjiu.annotation.PassToken;
import cn.ixinjiu.entity.User;
import cn.ixinjiu.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by XinChen on 2022/8/23
 *
 * @Description : TODO  拦截器拦截token
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                User user = userService.getUser(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassWord())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```

### 2.9.Create token

```java
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
```

## 3.Test from postman

### 3.1.Get token

![Screenshot 2022-08-23 at 22.05.51.png](/var/folders/3v/2fxvh7f16yq3yj13k17b24r00000gn/T/TemporaryItems/NSIRD_screencaptureui_A9PFCw/Screenshot%202022-08-23%20at%2022.05.51.png)

### 3.2.Without the token to login in

![Screenshot 2022-08-23 at 22.07.23.png](/var/folders/3v/2fxvh7f16yq3yj13k17b24r00000gn/T/TemporaryItems/NSIRD_screencaptureui_8tJ0tq/Screenshot%202022-08-23%20at%2022.07.23.png)

### 3.3.Login with a token

![Screenshot 2022-08-23 at 22.11.19.png](/var/folders/3v/2fxvh7f16yq3yj13k17b24r00000gn/T/TemporaryItems/NSIRD_screencaptureui_QLMZOn/Screenshot%202022-08-23%20at%2022.11.19.png)
