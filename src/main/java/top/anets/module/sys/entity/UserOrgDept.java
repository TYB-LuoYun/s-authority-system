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
@TableName("s_user_org_dept")
@ApiModel(value="UserOrgDept对象", description="")
public class UserOrgDept implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String orgId;

    private String deptId;


}
