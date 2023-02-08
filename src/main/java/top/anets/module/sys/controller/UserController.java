package top.anets.module.sys.controller;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.anets.common.constants.SysConstants;
import top.anets.common.utils.CookieUtils;
import top.anets.common.utils.Result;
import top.anets.module.sys.entity.SysUser;
import top.anets.common.utils.JwtTokenUtil;
import top.anets.common.utils.SecurityUtils;
import top.anets.module.security.UserDetailsServiceImpl;
import top.anets.module.sys.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.concurrent.TimeUnit;

import static top.anets.common.utils.JwtTokenUtil.getClaimsFromToken;

@RestController
@RequestMapping("user")
public class UserController {
//   测试步骤

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserDetailsServiceImpl sysUserDetailsService;


    @Autowired
    private IUserService userService;


    

    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response)
    {
        Authentication authentication = SecurityUtils.login(request, username, password, authenticationManager);
//      认证通过后就能获取到用户信息 
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof SysUser) {
                SysUser userDetails = (SysUser) authentication.getPrincipal();
//              redis存储用户信息
                redisTemplate.opsForValue().set(SysConstants.REDIS_USER_PREFIX+userDetails.getId(),userDetails ,60*60*24*365, TimeUnit.SECONDS);
//              JWT通过用户的一部分字段（自己定）生成token,自定义JWT
                String token = JwtTokenUtil.generateToken(userDetails);
                /**
                 * 将token传给前端
                 */
                CookieUtils.setCookie(request,response , SysConstants.TOKEN_SERVICE, token,60*60*24*365);
                return token;
            }else{
                throw new RuntimeException("验证失败");
            }
        }else{
            throw new RuntimeException("验证失败");
        }
    }

    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtils.deleteCookie(request,response , SysConstants.TOKEN_SERVICE);
        SysUser sysUser =(SysUser) SecurityUtils.getUser();
        if(sysUser == null){
            return Result.success();
        }
//      使登录session状态失效
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }

//      使用户缓存失效
        redisTemplate.delete( SysConstants.REDIS_USER_PREFIX +sysUser.getId());
        return Result.success();
    }


    @GetMapping("/getUser")
    public SysUser getUser(String token){
        if(StringUtils.isBlank(token)){
            return (SysUser) SecurityUtils.getUser();
        }
//      之前登录成功后存的字段都在这里面
        Claims fromToken = getClaimsFromToken(token);
        String id = (String) fromToken.get(JwtTokenUtil.USER_ID);
        return (SysUser) redisTemplate.opsForValue().get(SysConstants.REDIS_USER_PREFIX+id);
    }
}
