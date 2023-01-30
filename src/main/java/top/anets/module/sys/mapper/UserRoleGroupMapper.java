package top.anets.module.sys.mapper;

import org.apache.ibatis.annotations.Param;
import top.anets.module.sys.entity.UserRoleGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
    * mapper接口
    * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
public interface UserRoleGroupMapper extends BaseMapper<UserRoleGroup> {
    List<String> getAuthorities(@Param("roles") List<String> roles,@Param("groups") List<String> groups);
}