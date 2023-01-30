package top.anets.module.sys.controller;

import org.springframework.web.bind.annotation.*;
import top.anets.module.base.BaseController;
import top.anets.module.base.PageQuery;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.service.IRoleService;
import top.anets.module.sys.entity.Role;
import top.anets.module.sys.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Api(tags = {""})
@RestController
@RequestMapping("/role")
public class RoleController  extends BaseController<Role> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IRoleService roleService;




    @ApiOperation(value = "分页关联查询")
    @RequestMapping("lists")
    public IPage lists(PageQuery query, RoleVo roleVo){
        IPage  pages = roleService.page(query.Page(), WrapperQuery.query(roleVo));
        WrapperQuery.ipage(pages,RoleVo.class).getRecords().forEach(item->{
        //         todo    item.get...

        });
        //关联查询
        //WrapperQuery.wpage(pages,RoleVo.class).associate(roleService);
        return pages;
    }

   







 }
