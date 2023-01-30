package top.anets.module.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import top.anets.module.sys.entity.UserRoleGroup;
import top.anets.module.sys.enums.UserRoleGroupType;
import top.anets.module.sys.mapper.UserRoleGroupMapper;
import top.anets.module.sys.service.IUserRoleGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Service
public class UserRoleGroupServiceImpl extends ServiceImpl<UserRoleGroupMapper, UserRoleGroup> implements IUserRoleGroupService {


    @Override
    public List<String> getAuthorities(String userId) {
        List<UserRoleGroup> list = this.list(Wrappers.<UserRoleGroup>lambdaQuery().eq(UserRoleGroup::getUserId, userId));
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<String> roles = new ArrayList<>();
        List<String> groups = new ArrayList<>();
        list.forEach(item->{
            if(UserRoleGroupType.USER_ROLE.getValue() == item.getType()&& StringUtils.isNotBlank(item.getRoleId())){
                roles.add(item.getRoleId());
            }
            if(UserRoleGroupType.USER_GROUP.getValue() == item.getType()&&StringUtils.isNotBlank(item.getGroupId())){
                groups.add(item.getGroupId());
            }
        });
        List<String> authorities = baseMapper.getAuthorities(roles, groups);
        if(authorities == null){
            return null;
        }
        return  authorities.stream().filter(e->  StringUtils.isNotBlank(e)).collect(Collectors.toList());

    }


}
