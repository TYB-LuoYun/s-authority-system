package top.anets.module.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ftm
 * @date 2023/1/30 0030 15:31
 */
@Getter
@AllArgsConstructor
public enum PemissionRelationType {
    USER_GROUP_PERMISSION("用户组-权限",0),
    ROLE_PERMISSION("角色-权限",1),
    USER_GROUP_ROLE("用户组-角色",2),
    ;

    private String name;
    private Integer value;
}
