package top.anets.module.sys.service.impl;

import top.anets.module.sys.entity.UserGroup;
import top.anets.module.sys.mapper.UserGroupMapper;
import top.anets.module.sys.service.IUserGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

}
