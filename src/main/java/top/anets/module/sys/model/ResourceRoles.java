package top.anets.module.sys.model;

import lombok.Data;

import java.util.List;

@Data
public class ResourceRoles {
    private String resource;
    private List<String> roles;
}
