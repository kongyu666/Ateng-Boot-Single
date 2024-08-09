package local.ateng.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import local.ateng.boot.common.annotation.Log;
import local.ateng.boot.common.enums.BusinessType;
import local.ateng.boot.system.entity.SysRolePermission;
import local.ateng.boot.system.service.SysRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统设置/角色权限设置
 *
 * @author 孔余
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/role-permission")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SaCheckRole("super-admin")
public class SysRolePermissionController {

    private final SysRolePermissionService sysRolePermissionService;

    /**
     * 新增
     */
    @Log(module = "角色权限设置", desc = "新增角色权限", type = BusinessType.ADD)
    @PostMapping("/add")
    public void add(@RequestBody SysRolePermission entity) {
        sysRolePermissionService.addRolePermission(entity);
    }

    /**
     * 查看列表
     */
    @GetMapping("/list")
    public List<SysRolePermission> list() {
        return sysRolePermissionService.listRolePermission();
    }

    /**
     * 查看单个
     */
    @GetMapping("/get")
    public SysRolePermission get(Integer id) {
        return sysRolePermissionService.getRolePermission(id);
    }
}
