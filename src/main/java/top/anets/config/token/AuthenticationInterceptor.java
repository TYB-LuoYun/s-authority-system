package top.anets.config.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.anets.common.constants.RedisConstant;
import top.anets.common.constants.SysConstants;
import top.anets.common.utils.Result;
import top.anets.common.utils.JwtTokenUtil;
import top.anets.common.utils.SecurityUtils;
import top.anets.module.sys.entity.SysUser;
import top.anets.module.security.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;


/**
 * ClassName:AuthenticationInterceptor <br/>
 * Function:  <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2022/8/15 13:48 <br/>
 *
 * @author sheng.chen
 * @see
 * @since JDK 1.8
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserDetailsServiceImpl sysUserDetailsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进入方法之前进行的操作
        // 白名单直接放行
        boolean isMatch = this.matchWhiteListUrl(request);
        if(isMatch){
            return true;
        }
        //获取token
        //如果不是映射到方法直接通过
        String token = sysUserDetailsService.getToken(request);
        if(StringUtils.isNotBlank(token)){
            Claims claimsFromToken = JwtTokenUtil.getClaimsFromToken(token);
            if(claimsFromToken == null){
                this.setAuthenticateFailMsg(response,"无效token");
                return false;
            }
            String userId = (String) claimsFromToken.get(JwtTokenUtil.USER_ID);//有个问题，用户登出后，JWT token 立即失效，让JWT失效的唯一途径就是等token自己过期，无法做到主动让JWT失效;所以后面要判断
            if(StringUtils.isNotBlank(userId)){

                SysUser sessionUser= null;
                try {
                     sessionUser= (SysUser)SecurityUtils.getUser();
                }catch (Exception e){
                    System.out.println("匿名用户");
                }

                if(sessionUser != null){
//                  登录未失效
                    return validationAuthority(request,response,sessionUser);
                }
//              登录失效,自动重新登录，这个可能有bug,不能光凭借ID，但是一般有自己的秘钥，做伪不了,按理来说，一般都是这么做的
//                SysUser user = (SysUser) sysUserDetailsService.initUser(userId);
                SysUser user = (SysUser) redisTemplate.opsForValue().get(SysConstants.REDIS_USER_PREFIX+userId);
                if(user!=null){
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities()));
                    return validationAuthority(request,response,user);
                }else{
//                  缓存中没有，说明用户没有登陆过或者登陆的用户缓存已过期
                    this.setAuthenticateFailMsg(response,"token已过期" );
                    return false;
                }
            }else{
                this.setAuthenticateFailMsg(response,"无效token" );
                return false;
            }
        }else{
            this.setAuthenticateFailMsg(response,"请登录" );
            return false;
        }


    }

    /**
     * 校验资源权限
     * @param request
     * @param user
     * @return
     */
    private boolean validationAuthority(HttpServletRequest request,HttpServletResponse response, SysUser user) throws IOException {
//        管理员直接放行
        if("admin".equals(user.getUsername())){
            return true;
        }
//        根据路径查看访问该路径所需要的角色有哪些,比如['ADMIN']
        String uri = request.getRequestURI();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if(authorities == null){
            this.setAuthenticateFailMsg(response,"该用户未设置权限!");
            return false;
        }
//      细粒度
        List<String> needAuthorities = (List<String>) redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, uri);
        if(needAuthorities == null){
            //      粗粒度 - 查看是否有该模块的权限 , 如果url是 /system/order/pay 但是缓存路径是 /system/** , 针对这种情况
            Map<String, List<String>> moduleRes = redisTemplate.opsForHash().entries(RedisConstant.MODULE_RESOURCE_ROLES_MAP);
            if(moduleRes == null){
                this.setAuthenticateFailMsg(response,"没有权限访问");
                return false;
            }
            Set<String> keys = moduleRes.keySet();
            if(keys == null){
                this.setAuthenticateFailMsg(response,"没有权限访问");
                return false;
            }
            PathMatcher pathMatcher = new AntPathMatcher();
            for(String key : keys){
                if(pathMatcher.match(key, uri)){
                    needAuthorities =  moduleRes.get(key);
                    break;
                }
            }
        }

        if(needAuthorities != null){
            for (GrantedAuthority item :authorities) {
                if(needAuthorities.contains(item.getAuthority())){
                    return true;
                }
            }
        }
        this.setAuthenticateFailMsg(response,"没有权限访问");
        return false;
    }

    public static void main(String[] args){

        List<String> strings = new ArrayList<>();
        for(int i=0; i<1000;i++){
            strings.add("/"+i+"/system/**");
        }
        long start = System.currentTimeMillis();
        PathMatcher pathMatcher = new AntPathMatcher();
        for(String item : strings){
            System.out.println( pathMatcher.match(item, "/9999/system/get"));
        }
        long end = System.currentTimeMillis();
        System.out.println("结束");
        System.out.println((end-start)*1.0/1000 +"s");

    }

    private boolean matchWhiteListUrl(HttpServletRequest request) {
        //          白名单放行
        String uri = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行，这个可以在ResourceServerConfig中配置【其实这段代码配置了可不要】
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri)) {
                return true;
            }
        }
        return false;
    }


    private void setAuthenticateFailMsg(HttpServletResponse response, String msg) throws IOException {
        response.setContentType(SysConstants.APPLICATION_JSON_UTF8);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(mapper.writeValueAsString(Result.error(msg)));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //方法处理之后但是并未渲染视图的时候进行的操作
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //渲染视图之后进行的操作
    }
}