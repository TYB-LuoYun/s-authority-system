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
 * 组织表，因为组织和部门有不同的属性，所以要分开
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_org")
@ApiModel(value="Org对象", description="组织表，因为组织和部门有不同的属性，所以要分开")
public class Org implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "组织类型（0-公司,1-医院）")
    private Integer type;

    @ApiModelProperty(value = "组织名")
    private String name;

    @ApiModelProperty(value = "组织编码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "详细介绍")
    private String introduction;

    @ApiModelProperty(value = "官网地址")
    private String webSite;

    @ApiModelProperty(value = "logo地址")
    private String logoImage;

    @ApiModelProperty(value = "组织地址")
    private String address;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "上级")
    private String parentId;

    @ApiModelProperty(value = "级别")
    private String level;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Integer deleteFlag;


}
