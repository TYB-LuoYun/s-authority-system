package top.anets.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.anets.entity.SysUser;
import top.anets.common.utils.JwtTokenUtil;
import top.anets.common.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

import static top.anets.common.utils.JwtTokenUtil.getClaimsFromToken;

@RestController
@RequestMapping("user")
public class UserController {
//   测试步骤

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;


    private String REDIS_USER_PREFIX="user-uid-";
    

    @RequestMapping("/login")
    private String login(String username, String password, HttpServletRequest request)
    {
        Authentication authentication = SecurityUtils.login(request, username, password, authenticationManager);
//      认证通过后就能获取到用户信息 
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof SysUser) {
                SysUser userDetails = (SysUser) authentication.getPrincipal();
//              redis存储用户信息
                redisTemplate.opsForValue().set(REDIS_USER_PREFIX+userDetails.getId(),userDetails );
//              JWT通过用户的一部分字段（自己定）生成token,自定义JWT
                String token = JwtTokenUtil.generateToken(userDetails);
                return token;
            }else{
                throw new RuntimeException("验证失败");
            }
        }else{
            throw new RuntimeException("验证失败");
        }
    }

    @GetMapping("/getUser")
    private SysUser getUser(String token){
//      之前登录成功后存的字段都在这里面
        Claims fromToken = getClaimsFromToken(token);
        String id = (String) fromToken.get("id");
        return (SysUser) redisTemplate.opsForValue().get(REDIS_USER_PREFIX+id);
    }





}
