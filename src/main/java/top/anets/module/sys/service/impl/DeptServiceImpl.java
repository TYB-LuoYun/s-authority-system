package top.anets.module.sys.service.impl;

import top.anets.module.sys.entity.Dept;
import top.anets.module.sys.mapper.DeptMapper;
import top.anets.module.sys.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
