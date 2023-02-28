package top.anets.module.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.anets.common.constants.RedisConstant;
import top.anets.exception.ServiceException;
import top.anets.module.base.BaseController;
import top.anets.module.base.PageQuery;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.enums.PermissionType;
import top.anets.module.sys.model.ResourceRoles;
import top.anets.module.sys.service.IPermissionService;
import top.anets.module.sys.entity.Permission;
import top.anets.module.sys.vo.PermissionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Api(tags = {"权限表"})
@RestController
@RequestMapping("/permission")
public class PermissionController  extends BaseController<Permission> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IPermissionService permissionService;

    @Autowired
    private RedisTemplate redisTemplate;




    @ApiOperation(value = "分页关联查询")
    @RequestMapping("lists")
    public IPage lists(PageQuery query, PermissionVo permissionVo){
        IPage  pages = permissionService.page(query.Page(), WrapperQuery.query(permissionVo));
        WrapperQuery.ipage(pages,PermissionVo.class).getRecords().forEach(item->{
        //         todo    item.get...

        });
        //关联查询
        //WrapperQuery.wpage(pages,PermissionVo.class).associate(permissionService);
        return pages;
    }


    /**
     * 加载所有的权限资源
     */
    @PostConstruct
    @RequestMapping("loadResourceRoles")
    public void loadResourceRoles(){
        log.info("加载角色资源开始=====");
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        Map<String, List<String>> moduleResourceRolesMap = new TreeMap<>();
        List<ResourceRoles> res = permissionService.loadResourceRoles(Arrays.asList(PermissionType.MENU.getValue(),PermissionType.BUTTOM.getValue(),PermissionType.FUNCTION.getValue()));
        if(!CollectionUtils.isEmpty(res)){
            res.forEach(item->{
                resourceRolesMap.put(item.getResource(), item.getRoles());
            });

            redisTemplate.delete(RedisConstant.RESOURCE_ROLES_MAP);
            redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
        }
        List<ResourceRoles> moduleRes = permissionService.loadResourceRoles(Arrays.asList(PermissionType.MODULE.getValue()));
        if(!CollectionUtils.isEmpty(moduleRes)){
            moduleRes.forEach(item->{
                moduleResourceRolesMap.put(item.getResource(), item.getRoles());
            });
            redisTemplate.delete(RedisConstant.MODULE_RESOURCE_ROLES_MAP);
            redisTemplate.opsForHash().putAll(RedisConstant.MODULE_RESOURCE_ROLES_MAP, moduleResourceRolesMap);
        }




        log.info("加载角色资源结束=====");
    }


    /**
     * 添加或者修改资源
     */
    @RequestMapping("saveOrUpdateResource")
    public String saveOrUpdateResource(@Valid @RequestBody PermissionVo permissionVo){
//       校验
        PermissionType byValue = PermissionType.findByValue(permissionVo.getType());
        if(byValue == null){
            throw new ServiceException("资源类型不匹配");
        }
        permissionService.saveOrUpdate(permissionVo);
        return permissionVo.getId();
    }


    /**
     * 查看资源树（所有）
     * 查看页面权限（系统-菜单） ---->只能添加菜单和按钮
     * 查看功能权限（系统-目录（菜单，模块）-(功能/按钮(按钮)）） ----->只能添加模块和功能
     */
    @RequestMapping("resourceTree")
    public List<PermissionVo> resourceTree(){
        return permissionService.resourceTree();
    }


}
