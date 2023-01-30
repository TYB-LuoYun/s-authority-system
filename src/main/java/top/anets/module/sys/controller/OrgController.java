package top.anets.module.sys.controller;

import org.springframework.web.bind.annotation.*;
import top.anets.module.base.BaseController;
import top.anets.module.base.PageQuery;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.service.IOrgService;
import top.anets.module.sys.entity.Org;
import top.anets.module.sys.vo.OrgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 组织表，因为组织和部门有不同的属性，所以要分开 前端控制器
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Api(tags = {"组织表，因为组织和部门有不同的属性，所以要分开"})
@RestController
@RequestMapping("/org")
public class OrgController  extends BaseController<Org> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IOrgService orgService;




    @ApiOperation(value = "分页关联查询")
    @RequestMapping("lists")
    public IPage lists(PageQuery query, OrgVo orgVo){
        IPage  pages = orgService.page(query.Page(), WrapperQuery.query(orgVo));
        WrapperQuery.ipage(pages,OrgVo.class).getRecords().forEach(item->{
        //         todo    item.get...

        });
        //关联查询
        //WrapperQuery.wpage(pages,OrgVo.class).associate(orgService);
        return pages;
    }

   







 }
