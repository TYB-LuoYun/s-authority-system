package top.anets.module.sys.service.impl;

import top.anets.module.sys.entity.Role;
import top.anets.module.sys.mapper.RoleMapper;
import top.anets.module.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
