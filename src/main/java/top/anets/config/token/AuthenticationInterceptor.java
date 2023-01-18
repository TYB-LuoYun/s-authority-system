package top.anets.config.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.anets.common.constants.SysConstants;
import top.anets.common.support.Result;
import top.anets.common.utils.CookieUtils;
import top.anets.common.utils.SecurityUtils;
import top.anets.entity.SysUser;
import top.anets.service.impl.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String token = CookieUtils.getCookieValue(request, SysConstants.TOKEN_SERVICE);
        if(StringUtils.isNotBlank(token)){
           String userId =    ( String) redisTemplate.opsForValue().get(SysConstants.TOKEN_SERVICE+ token);
            if(StringUtils.isNotBlank(userId)){
                UserDetails user =sysUserDetailsService.initUser(userId);
                SysUser sessionUser= null;
                try {
                     sessionUser= (SysUser)SecurityUtils.getUser();
                }catch (Exception e){
                    System.out.println("匿名用户");
                }
                if(user!=null &&sessionUser ==null ){
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities()));
                }
                return true;
            }else{
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
                response.setContentType(SysConstants.APPLICATION_JSON_UTF8);
                response.setStatus(HttpStatus.OK.value());
                response.getWriter().write(mapper.writeValueAsString(Result.error("无效token")));
                return false;
            }
        }else{
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
            response.setContentType(SysConstants.APPLICATION_JSON_UTF8);
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(mapper.writeValueAsString(Result.error("请登录")));
            return false;
        }


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