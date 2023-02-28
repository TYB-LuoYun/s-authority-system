package top.anets.module.sys.vo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import top.anets.module.sys.entity.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class  PermissionVo extends Permission{


    @NotNull
    @ApiModelProperty(value = "类型(0,系统,1菜单,2模块,3按钮,4功能)")
    private Integer type;


    @NotBlank
    @ApiModelProperty(value = "名称")
    private String name;



    @ApiModelProperty(value = "是否为外链（0否 1是）")
    private Integer isFrame = 0;




    @ApiModelProperty(value = "可见状态（0隐藏 1显示）")
    private Boolean visible = true;

    @ApiModelProperty(value = "菜单状态（0停用 1启用）")
    private Boolean status = true;


    private Integer sort = 0;


    List<PermissionVo> children;
}