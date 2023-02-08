package top.anets.module.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import top.anets.exception.ServiceException;
import top.anets.module.base.WrapperQuery;
import top.anets.module.security.MyGrantedAuthority;
import top.anets.module.sys.entity.Permission;
import top.anets.module.sys.entity.SysUser;
import top.anets.module.sys.mapper.UserMapper;
import top.anets.module.sys.service.IUserRoleGroupService;
import top.anets.module.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements IUserService {

    @Autowired
    private IUserRoleGroupService userRoleGroupService;

    @Override
    public SysUser getUser(String userName) {
        return WrapperQuery.queryOne(this, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername,userName ));
    }

    @Override
    public SysUser loadUserByUsername(String userName) {
        if (userName == null || "".equals(userName))
        {
            throw new ServiceException("用户不能为空");
        }
        // 调用方法查询用户
        SysUser sysUser = this.getUser(userName);
        if(sysUser == null){
            throw new ServiceException("用户不存在");
        }
//      查找用户角色
        List<String> authorities = userRoleGroupService.getAuthorities(sysUser.getId());
        sysUser.setRoles(authorities);
        Set<MyGrantedAuthority> grantedAuthoritys = Sets.newHashSet();
        if(authorities != null){
            authorities.forEach(item->{
                grantedAuthoritys.add( new MyGrantedAuthority( item));
            });
        }
        sysUser.setAuthorities(grantedAuthoritys);
        return sysUser;
    }


}
