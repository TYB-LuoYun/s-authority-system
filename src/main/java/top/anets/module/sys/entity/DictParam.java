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
 * 
 * </p>
 *
 * @author ftm
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_dict_param")
@ApiModel(value="DictParam对象", description="")
public class DictParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "字典组")
    private String groupCode;

    @ApiModelProperty(value = "类型")
    private String typeCode;

    @ApiModelProperty(value = "字典名")
    private String name;

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "字典默认值")
    private String value;

    @ApiModelProperty(value = "值类型(0-value,1-选项,2图片,3轮播图,4logo)")
    private Integer valueType;

    @ApiModelProperty(value = "选项(^隔开)")
    private String options;

    private Integer sort;

    private String insetsUrl;

    private String imgUrl;

    private String linkUrl;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String rootId;

    private String parentId;

    private Boolean isDir;

    private Boolean isLeaf;

    private Boolean state;

    private Integer level;

    private Integer deleted;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;

    private String attr5;


}
