package top.anets.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_permission")
@ApiModel(value="Permission对象", description="菜单权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "类型(0,系统,1菜单,2模块,3按钮,4功能)")
    private Boolean type;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "标识码")
    private String code;

    @ApiModelProperty(value = "父菜单ID")
    private String parentId;

    @ApiModelProperty(value = "菜单路由地址")
    private String menuPath;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "是否为外链（0否 1是）")
    private Integer isFrame;

    @ApiModelProperty(value = "外链地址或内链相对url")
    private String url;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "可见状态（0隐藏 1显示）")
    private Boolean visible;

    @ApiModelProperty(value = "菜单状态（0停用 1启用）")
    private Boolean status;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "显示顺序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
