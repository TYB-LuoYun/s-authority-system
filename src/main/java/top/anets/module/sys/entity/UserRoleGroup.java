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
 * 
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_user_role_group")
@ApiModel(value="UserRoleGroup对象", description="")
public class UserRoleGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    @ApiModelProperty(value = "关联类型(0-用户与角色，1-用户与用户组)")
    private Integer type;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "用户组id")
    private String groupId;


}
