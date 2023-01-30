package top.anets.module.sys.controller;

import org.springframework.web.bind.annotation.*;
import top.anets.module.base.BaseController;
import top.anets.module.base.PageQuery;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.service.IUserGroupService;
import top.anets.module.sys.entity.UserGroup;
import top.anets.module.sys.vo.UserGroupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户组 前端控制器
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Api(tags = {"用户组"})
@RestController
@RequestMapping("/user-group")
public class UserGroupController  extends BaseController<UserGroup> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IUserGroupService userGroupService;




    @ApiOperation(value = "分页关联查询")
    @RequestMapping("lists")
    public IPage lists(PageQuery query, UserGroupVo userGroupVo){
        IPage  pages = userGroupService.page(query.Page(), WrapperQuery.query(userGroupVo));
        WrapperQuery.ipage(pages,UserGroupVo.class).getRecords().forEach(item->{
        //         todo    item.get...

        });
        //关联查询
        //WrapperQuery.wpage(pages,UserGroupVo.class).associate(userGroupService);
        return pages;
    }

   







 }
