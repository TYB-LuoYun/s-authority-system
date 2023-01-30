package top.anets.config.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.anets.common.constants.SysConstants;
import top.anets.common.utils.Result;
import top.anets.common.utils.JwtTokenUtil;
import top.anets.common.utils.SecurityUtils;
import top.anets.entity.SysUser;
import top.anets.service.impl.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


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
        //获取token
        //如果不是映射到方法直接通过
        String token = sysUserDetailsService.getToken(request);
        if(StringUtils.isNotBlank(token)){
            Claims claimsFromToken = JwtTokenUtil.getClaimsFromToken(token);
            if(claimsFromToken == null){
                // 白名单放行
                boolean isMatch = this.matchWhiteListUrl(request);
                if(isMatch){
                    return true;
                }
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
                    return true;
                }
//              登录失效,自动重新登录，这个可能有bug,不能光凭借ID，但是一般有自己的秘钥，做伪不了,按理来说，一般都是这么做的
//                SysUser user = (SysUser) sysUserDetailsService.initUser(userId);
                SysUser user = (SysUser) redisTemplate.opsForValue().get(SysConstants.REDIS_USER_PREFIX+userId);
                if(user!=null){
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities()));
                    return true;
                }else{
//                  缓存中没有，说明用户没有登陆过或者登陆的用户缓存已过期
//                    白名单放行
                    boolean isMatch = this.matchWhiteListUrl(request);
                    if(isMatch){
                        return true;
                    }
                    this.setAuthenticateFailMsg(response,"token已过期" );
                    return false;
                }
            }else{
//              白名单放行
                boolean isMatch = this.matchWhiteListUrl(request);
                if(isMatch){
                    return true;
                }
                this.setAuthenticateFailMsg(response,"无效token" );
                return false;
            }
        }else{
            boolean isMatch = this.matchWhiteListUrl(request);
            if(isMatch){
                return true;
            }
            this.setAuthenticateFailMsg(response,"请登录" );
            return false;
        }


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