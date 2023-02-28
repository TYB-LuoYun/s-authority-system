package top.anets.module.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ftm
 * @date 2023/1/31 0031 11:25
 */
@Getter
@AllArgsConstructor
public enum PermissionType {
    SYSTEM("系统",0),
    MENU("菜单",1),
    MODULE("模块",2),
    BUTTOM("按钮",3),
    FUNCTION("功能",4),
    ;

    private String name;
    private Integer value;


    public static PermissionType findByValue(Integer value){
        for (PermissionType item : PermissionType.values()){
            if(item.getValue() == value){
                return item;
            }
        }
        return null;
    }
}
