package top.anets.service.impl;

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
import top.anets.entity.SysRole;
import top.anets.entity.SysUser;

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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (s == null || "".equals(s))
        {
            throw new RuntimeException("用户不能为空");
        }
        // 调用方法查询用户

        Set<GrantedAuthority> authorities = Sets.newHashSet();
        ArrayList<SysRole> sysRoles = new ArrayList<>();
        SysRole sysRole1 = new SysRole();
        sysRole1.setName("admin");
        sysRoles.add(sysRole1);
//        for (SysRole sysRole : sysRoles)
//        {
//            authorities.add(new MyGrantedAuthority("ROLE_"+ sysRole.getName()));
//        }


        SysUser sysUser = new SysUser("admin", passwordEncoder.encode("123456"),null,authorities);
        sysUser.setId("1");
        return sysUser;
    }

    public UserDetails initUser(String userId) {
        Set<GrantedAuthority> authorities = Sets.newHashSet();
//        这里去查库
        SysUser  byId =new SysUser("admin", passwordEncoder.encode("123456"),null,authorities );
        byId.setId("1");
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
