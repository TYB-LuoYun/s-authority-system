package top.anets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.anets.entity.SysRole;
import top.anets.entity.SysUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (s == null || "".equals(s))
        {
            throw new RuntimeException("用户不能为空");
        }
        // 调用方法查询用户

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        ArrayList<SysRole> sysRoles = new ArrayList<>();
        SysRole sysRole1 = new SysRole();
        sysRole1.setName("admin");
        sysRoles.add(sysRole1);
        for (SysRole sysRole : sysRoles)
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+ sysRole.getName()));
        }


        SysUser sysUser = new SysUser("admin", passwordEncoder.encode("123456"), null, authorities);
        return sysUser;
    }

    public UserDetails initUser(String userId) {
//        这里去查库
        SysUser  byId =new SysUser("admin", passwordEncoder.encode("123456"),null,null);
        return loadUserByUsername(byId.getUsername());
    }
}
