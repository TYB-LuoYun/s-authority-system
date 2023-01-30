package top.anets.module.sys.controller;

import org.springframework.web.bind.annotation.*;
import top.anets.module.base.BaseController;
import top.anets.module.base.PageQuery;
import top.anets.module.base.WrapperQuery;
import top.anets.module.sys.service.IDataRuleGroupService;
import top.anets.module.sys.entity.DataRuleGroup;
import top.anets.module.sys.vo.DataRuleGroupVo;
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
@RequestMapping("/data-rule-group")
public class DataRuleGroupController  extends BaseController<DataRuleGroup> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IDataRuleGroupService dataRuleGroupService;




    @ApiOperation(value = "分页关联查询")
    @RequestMapping("lists")
    public IPage lists(PageQuery query, DataRuleGroupVo dataRuleGroupVo){
        IPage  pages = dataRuleGroupService.page(query.Page(), WrapperQuery.query(dataRuleGroupVo));
        WrapperQuery.ipage(pages,DataRuleGroupVo.class).getRecords().forEach(item->{
        //         todo    item.get...

        });
        //关联查询
        //WrapperQuery.wpage(pages,DataRuleGroupVo.class).associate(dataRuleGroupService);
        return pages;
    }

   







 }
