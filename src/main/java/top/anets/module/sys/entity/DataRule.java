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
 * 
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_data_rule")
@ApiModel(value="DataRule对象", description="")
public class DataRule implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "所属规则组编码")
    private String groupCode;

    @ApiModelProperty(value = "规则字段名")
    private String fieldName;

    @ApiModelProperty(value = "规则字段")
    private String field;

    @ApiModelProperty(value = "表达式（in,like）")
    private String operation;

    @ApiModelProperty(value = "规则值")
    private String value;

    @ApiModelProperty(value = "连接下一个条件（and or）")
    private String connectCondition;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private Integer deleteFlag;


}
