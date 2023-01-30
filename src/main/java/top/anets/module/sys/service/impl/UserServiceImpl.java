package top.anets.module.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.entity.Permission;
import top.anets.module.sys.entity.SysUser;
import top.anets.module.sys.mapper.UserMapper;
import top.anets.module.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public SysUser getUser(String userName) {
        return WrapperQuery.queryOne(this, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername,userName ));
    }



}
