package top.anets.module.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ftm
 * @date 2023/1/30 0030 13:26
 */
@Getter
@AllArgsConstructor
public enum UserRoleGroupType {
    USER_ROLE("用户-角色",0),
    USER_GROUP("用户-用户组",1)
    ;

    private String name;
    private Integer value;
}
