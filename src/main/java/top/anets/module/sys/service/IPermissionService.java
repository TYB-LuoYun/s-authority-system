package top.anets.module.sys.service;

import top.anets.module.sys.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import top.anets.module.sys.model.ResourceRoles;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 查询某资源对应所需要的角色或者组
     * @return
     */
    List<ResourceRoles> loadResourceRoles(List<Integer> types);
}
