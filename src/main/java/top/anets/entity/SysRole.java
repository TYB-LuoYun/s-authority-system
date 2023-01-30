package top.anets.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRole implements Serializable {
    private Integer id;
    private String username;
    private String name;
}
