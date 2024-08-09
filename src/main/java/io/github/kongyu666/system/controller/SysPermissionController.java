package local.ateng.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import local.ateng.boot.common.annotation.Log;
import local.ateng.boot.common.enums.BusinessType;
import local.ateng.boot.system.entity.SysPermission;
import local.ateng.boot.system.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统设置/权限设置
 *
 * @author 孔余
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system-permission")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SaCheckRole("super-admin")
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    /**
     * 新增
     */
    @Log(module = "权限设置", desc = "权限角色", type = BusinessType.ADD)
    @PostMapping("/add")
    public void add(@RequestBody SysPermission entity) {
        sysPermissionService.addRole(entity);
    }

    /**
     * 查看列表
     */
    @GetMapping("/list")
    public List<SysPermission> list() {
        return sysPermissionService.listPermission();

    }

    /**
     * 查看单个
     */
    @GetMapping("/get")
    public SysPermission get(Integer id) {
        return sysPermissionService.getPermission(id);
    }
}
