package com.wl.big_things.controller;

import com.wl.big_things.pojo.Result;
import com.wl.big_things.pojo.User;
import com.wl.big_things.service.UserService;
import com.wl.big_things.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@Pattern(regexp="^\\S{5,16}$") String username, @Pattern(regexp="^\\S{5,16}$") String password)
    {
/*        if(username!=null &&username.length()>=5 &&username.length()<=16 &&password!=null
                &&password.length()>=5 &&password.length()<=16)
        {*/
            User u = userService.findByUserName(username);
            if(u==null)
            {
                userService.register(username,password);
                return Result.success();
            }
            else {
                return Result.error("用户名已被占用");
            }
        }
/*        else {
            return Result.error("参数不合法");
        }
    }*/
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password)
    {
        User loginUser = userService.findByUserName(username);
        if(loginUser==null)
        {
            return Result.error("用户名错误");
        }
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword()))
        {
            return Result.success("jwt token令牌");
        }
        return Result.error("密码错误");
    }
}
