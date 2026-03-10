package com.wl.big_things.controller;

import com.wl.big_things.pojo.Result;
import com.wl.big_things.pojo.User;
import com.wl.big_things.service.UserService;
import com.wl.big_things.utils.JwtUtil;
import com.wl.big_things.utils.Md5Util;
import com.wl.big_things.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public <E>Result<E> register(@Pattern(regexp="^\\S{5,16}$") String username, @Pattern(regexp="^\\S{5,16}$") String password)
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
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name="Authorization") String token*/)
    {
/*        Map<String, Object> map = JwtUtil.parseToken(token);
        String username=(String) map.get("username");*/
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }
}
