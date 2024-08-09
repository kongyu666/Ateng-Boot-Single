package local.ateng.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import local.ateng.boot.common.annotation.Log;
import local.ateng.boot.common.enums.BusinessType;
import local.ateng.boot.system.entity.SysUserRole;
import local.ateng.boot.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统设置/用户角色设置
 *
 * @author 孔余
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/user-role")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SaCheckRole("super-admin")
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    /**
     * 新增
     */
    @Log(module = "用户角色设置", desc = "新增用户角色", type = BusinessType.ADD)
    @PostMapping("/add")
    public void add(@RequestBody SysUserRole entity) {
        sysUserRoleService.addUserRole(entity);
    }

    /**
     * 查看列表
     */
    @GetMapping("/list")
    public List<SysUserRole> list() {
        return sysUserRoleService.listUserRole();
    }

    /**
     * 查看单个
     */
    @GetMapping("/get")
    public SysUserRole get(Integer id) {
        return sysUserRoleService.getUserRole(id);
    }
}
