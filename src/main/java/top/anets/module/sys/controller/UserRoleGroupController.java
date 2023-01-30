package top.anets.module.sys.controller;

import org.springframework.web.bind.annotation.*;
import top.anets.module.base.BaseController;
import top.anets.module.base.PageQuery;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.service.IUserRoleGroupService;
import top.anets.module.sys.entity.UserRoleGroup;
import top.anets.module.sys.vo.UserRoleGroupVo;
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
@RequestMapping("/user-role-group")
public class UserRoleGroupController  extends BaseController<UserRoleGroup> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IUserRoleGroupService userRoleGroupService;




    @ApiOperation(value = "分页关联查询")
    @RequestMapping("lists")
    public IPage lists(PageQuery query, UserRoleGroupVo userRoleGroupVo){
        IPage  pages = userRoleGroupService.page(query.Page(), WrapperQuery.query(userRoleGroupVo));
        WrapperQuery.ipage(pages,UserRoleGroupVo.class).getRecords().forEach(item->{
        //         todo    item.get...

        });
        //关联查询
        //WrapperQuery.wpage(pages,UserRoleGroupVo.class).associate(userRoleGroupService);
        return pages;
    }

   







 }
