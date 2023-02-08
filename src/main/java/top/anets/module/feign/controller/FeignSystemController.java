package top.anets.module.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.anets.module.sys.entity.SysUser;
import top.anets.module.sys.service.IUserService;

/**
 * @author ftm
 * @date 2023/2/8 0008 11:39
 */

@RestController
@RequestMapping("/feign/user")
public class FeignSystemController {
    @Autowired
    private IUserService userService;
    /**
     * 对外的feign服务
     */
    @RequestMapping("/loadUserByUsername")
    public SysUser loadUserByUsername(String username){
        return userService.loadUserByUsername(username);
    }

}
