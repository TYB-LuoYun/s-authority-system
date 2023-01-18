package top.anets.common.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static top.anets.common.utils.JwtTokenUtil.getClaimsFromToken;

public class SecurityUtils {
    public static Authentication login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager){
        //      一些验证逻辑
        UsernamePasswordAuthenticationToken  usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return authentication;
    }



    /**
     * 通过token 验证（校验是否登录 和 域中的用户信息过期了也可以通过token实现自动登录）
     * @param token
     */
    public void checkAuthentication(String token){

        // 获取令牌并根据令牌获取登录认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return  ;
        }
        Claims claims = getClaimsFromToken(token);
        if(claims == null) {
            throw new RuntimeException("token没有用户实体信息");
        }

        String username = claims.getSubject();
        if(username == null) {
            throw new RuntimeException("token没有用户名信息");
        }

        Object authors = claims.get(JwtTokenUtil.AUTHORITIES);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authentication = new UsernamePasswordAuthenticationToken(username, null, authorities );

        // 设置登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    /**
     * 获取用户信息
     * @return 用户信息
     */
    public static Object getUser() {
        return   SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
