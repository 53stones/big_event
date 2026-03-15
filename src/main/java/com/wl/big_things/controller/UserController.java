package com.wl.big_things.controller;

import com.wl.big_things.pojo.Result;
import com.wl.big_things.pojo.User;
import com.wl.big_things.service.UserService;
import com.wl.big_things.utils.JwtUtil;
import com.wl.big_things.utils.Md5Util;
import com.wl.big_things.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
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
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
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

    @PutMapping("/update")
    public Result<Void> update(@RequestBody @Validated User user)
    {
        Map<String,Object> chaim = ThreadLocalUtil.get();
        if(user.getId().equals(chaim.get("id")))
        {
            userService.update(user);
            return Result.success();
        }
        else{
            return Result.error("id错误");
        }
    }

    @PatchMapping("/updateAvatar")
    public Result<Void> updateAvatar(@RequestParam @URL String avatarUrl)
    {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result<Void> updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token)
    {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd))
        {
            return Result.error("缺少必要的参数");
        }
        Map<String,Object> map=ThreadLocalUtil.get();
        String username=(String) map.get("username");
        User loginUser=userService.findByUserName(username);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd)))
        {
            return Result.error("原密码错误");
        }
        if(!rePwd.equals(newPwd))
        {
            return Result.error("两次填写的密码不正确");
        }
        userService.updatePwd(newPwd);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
