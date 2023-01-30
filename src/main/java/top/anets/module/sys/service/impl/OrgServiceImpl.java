package top.anets.module.sys.service.impl;

import top.anets.module.sys.entity.Org;
import top.anets.module.sys.mapper.OrgMapper;
import top.anets.module.sys.service.IOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织表，因为组织和部门有不同的属性，所以要分开 服务实现类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {

}
