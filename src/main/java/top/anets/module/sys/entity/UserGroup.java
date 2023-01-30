package top.anets.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户组
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_user_group")
@ApiModel(value="UserGroup对象", description="用户组")
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String orgId;

    private String deptId;

    @ApiModelProperty(value = "组名")
    private String name;

    @ApiModelProperty(value = "组编码")
    private String code;

    @ApiModelProperty(value = "授权类型(0-直接授权，1-角色授权)")
    private Integer authorizeType;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Integer deleteFlag;


}
