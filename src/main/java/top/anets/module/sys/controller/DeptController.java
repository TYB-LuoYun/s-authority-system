package top.anets.module.sys.controller;

import org.springframework.web.bind.annotation.*;
import top.anets.module.base.BaseController;
import top.anets.module.base.PageQuery;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.service.IDeptService;
import top.anets.module.sys.entity.Dept;
import top.anets.module.sys.vo.DeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Api(tags = {"部门表"})
@RestController
@RequestMapping("/dept")
public class DeptController  extends BaseController<Dept> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IDeptService deptService;




    @ApiOperation(value = "分页关联查询")
    @RequestMapping("lists")
    public IPage lists(PageQuery query, DeptVo deptVo){
        IPage  pages = deptService.page(query.Page(), WrapperQuery.query(deptVo));
        WrapperQuery.ipage(pages,DeptVo.class).getRecords().forEach(item->{
        //         todo    item.get...

        });
        //关联查询
        //WrapperQuery.wpage(pages,DeptVo.class).associate(deptService);
        return pages;
    }

   







 }
