package top.anets.module.sys.service;

import top.anets.module.sys.entity.Permission;
import top.anets.module.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
public interface IUserService extends IService<SysUser> {

    SysUser getUser(String userName);

    SysUser loadUserByUsername(String username);
}
