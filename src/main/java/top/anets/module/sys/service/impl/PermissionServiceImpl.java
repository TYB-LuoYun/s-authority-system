package top.anets.module.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import top.anets.exception.ServiceException;
import top.anets.module.sys.entity.Permission;
import top.anets.module.sys.entity.PermissionRelation;
import top.anets.module.sys.enums.PermissionRelationType;
import top.anets.module.sys.enums.PermissionType;
import top.anets.module.sys.mapper.PermissionMapper;
import top.anets.module.sys.mapper.UserRoleGroupMapper;
import top.anets.module.sys.model.ResourceRoles;
import top.anets.module.sys.service.IPermissionRelationService;
import top.anets.module.sys.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.anets.module.sys.vo.PermissionVo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Autowired
    private IPermissionRelationService permissionRelationService;
    @Autowired
    private UserRoleGroupMapper userRoleGroupMapper;

    @Override
    public List<ResourceRoles> loadResourceRoles(List<Integer> types) {
        List<Permission> list = this.list(Wrappers.<Permission>lambdaQuery().in(Permission::getType, types).isNotNull(Permission::getUrl));
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<ResourceRoles> resourceRolesList = new LinkedList<>();
        list.forEach(item->{
            String url = item.getUrl();
            if(StringUtils.isNotBlank(url)){
                ResourceRoles resourceRoles = new ResourceRoles();
                resourceRoles.setResource(url);
//               查询该资源对应的角色
                List<PermissionRelation> list1 = permissionRelationService.list(Wrappers.<PermissionRelation>lambdaQuery().eq(PermissionRelation::getPermissionId, item.getId()));
                if(CollectionUtils.isEmpty(list1)){
                    return;
                }
                List<String> roles = new ArrayList<>();
                List<String> groups = new ArrayList<>();
                list1.forEach(each->{
                    if(PermissionRelationType.ROLE_PERMISSION.getValue() == each.getRelationType() &&  StringUtils.isNotBlank(each.getRoleId())){
                        roles.add(each.getRoleId());
                    }
                    if(PermissionRelationType.USER_GROUP_PERMISSION.getValue() == each.getRelationType()&&  StringUtils.isNotBlank(each.getGroupId())){
                        groups.add(each.getGroupId());
                    }
                });
                List<String> authorities = userRoleGroupMapper.getAuthorities(roles, groups);
                if(authorities != null){
                    resourceRoles.setRoles(authorities.stream().filter(e->StringUtils.isNotBlank(e )).collect(Collectors.toList()));
                }
                resourceRolesList.add(resourceRoles);
            }
        });
        return resourceRolesList;
    }

    @Override
    public List<PermissionVo> resourceTree() {
        return this.recursiveResourceTree(null);
    }

    private List<PermissionVo> recursiveResourceTree(String parentId) {
        List<Permission> list = null;
        if(parentId == null){
            list = this.list(Wrappers.<Permission>lambdaQuery().isNull(Permission::getParentId).or().eq(Permission::getParentId, ""));
        }else{
            list = this.list(Wrappers.<Permission>lambdaQuery().eq(Permission::getParentId, parentId));
        }

        if(list == null || list.isEmpty()){
            return null;
        }
        List<PermissionVo> collect = list.stream().map(e -> {
            PermissionVo permissionVo = new PermissionVo();
            BeanUtils.copyProperties(e, permissionVo);
            return permissionVo;
        }).collect(Collectors.toList());
        collect.forEach(item->{
            item.setChildren(this.recursiveResourceTree(item.getId()));
        });
        return collect;
    }
}
