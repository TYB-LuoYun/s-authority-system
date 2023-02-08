package top.anets.module.security;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.anets.common.constants.SysConstants;
import top.anets.common.utils.CookieUtils;
import top.anets.exception.ServiceException;
import top.anets.module.sys.entity.Permission;
import top.anets.module.sys.entity.SysUser;
import top.anets.module.sys.service.IUserRoleGroupService;
import top.anets.module.sys.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;




    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userService.loadUserByUsername(userName);
    }

    public UserDetails initUser(String userId) {
        SysUser byId = userService.getById(userId);
        return loadUserByUsername(byId.getUsername());
    }


    public String getToken(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, SysConstants.TOKEN_SERVICE);
        if(StringUtils.isNotBlank(token)){
            return token;
        }
        token = request.getHeader(SysConstants.TOKEN_HEADER);
        return token;
    }


}
