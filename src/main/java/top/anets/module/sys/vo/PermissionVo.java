package top.anets.module.sys.vo;
import top.anets.module.sys.entity.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class  PermissionVo extends Permission{

}