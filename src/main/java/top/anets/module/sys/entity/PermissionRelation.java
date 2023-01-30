package top.anets.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限关联表
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_permission_relation")
@ApiModel(value="PermissionRelation对象", description="权限关联表")
public class PermissionRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "关系类型: 0-用户组与权限,1-角色与权限，2-用户组与角色")
    private Integer relationType;

    @ApiModelProperty(value = "用户组ID")
    private String groupId;

    @ApiModelProperty(value = "用户组关联的角色")
    private String roleId;

    @ApiModelProperty(value = "关联的权限ID")
    private String permissionId;

    @ApiModelProperty(value = "权限类型: 0-菜单，1-功能，2-数据 （兼容旧版本而设）")
    private Integer permissionType;


}
