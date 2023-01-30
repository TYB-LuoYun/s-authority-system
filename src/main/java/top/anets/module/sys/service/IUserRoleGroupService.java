package top.anets.module.sys.service;

import top.anets.module.sys.entity.SysUser;
import top.anets.module.sys.entity.UserRoleGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
public interface IUserRoleGroupService extends IService<UserRoleGroup> {

    List<String> getAuthorities(String userId);
}
