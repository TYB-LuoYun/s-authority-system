package top.anets.module.sys.controller;

import org.springframework.web.bind.annotation.*;
import top.anets.module.base.BaseController;
import top.anets.module.base.PageQuery;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.service.IPermissionRelationService;
import top.anets.module.sys.entity.PermissionRelation;
import top.anets.module.sys.vo.PermissionRelationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限关联表 前端控制器
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Api(tags = {"权限关联表"})
@RestController
@RequestMapping("/permission-relation")
public class PermissionRelationController  extends BaseController<PermissionRelation> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IPermissionRelationService permissionRelationService;




    @ApiOperation(value = "分页关联查询")
    @RequestMapping("lists")
    public IPage lists(PageQuery query, PermissionRelationVo permissionRelationVo){
        IPage  pages = permissionRelationService.page(query.Page(), WrapperQuery.query(permissionRelationVo));
        WrapperQuery.ipage(pages,PermissionRelationVo.class).getRecords().forEach(item->{
        //         todo    item.get...

        });
        //关联查询
        //WrapperQuery.wpage(pages,PermissionRelationVo.class).associate(permissionRelationService);
        return pages;
    }

   







 }
