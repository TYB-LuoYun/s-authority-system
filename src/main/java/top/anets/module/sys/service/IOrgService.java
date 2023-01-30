package top.anets.module.sys.service;

import top.anets.module.sys.entity.Org;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 组织表，因为组织和部门有不同的属性，所以要分开 服务类
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
public interface IOrgService extends IService<Org> {

}
