package io.github.kongyu666.system.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import io.github.kongyu666.common.annotation.Log;
import io.github.kongyu666.common.enums.BusinessType;
import io.github.kongyu666.common.utils.Result;
import io.github.kongyu666.system.entity.SysRole;
import io.github.kongyu666.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统设置/角色设置
 *
 * @author 孔余
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/role")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SaCheckRole("super-admin")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    /**
     * 新增
     */
    @Log(module = "角色设置", desc = "新增角色", type = BusinessType.ADD)
    @PostMapping("/add")
    public Result add(@RequestBody SysRole entity) {
        sysRoleService.addRole(entity);
        return Result.success();
    }

    /**
     * 查看列表
     */
    @GetMapping("/list")
    public Result list() {
        List<SysRole> list = sysRoleService.listRole();
        return Result.success(list);
    }

    /**
     * 查看单个
     */
    @GetMapping("/get")
    public Result get(Integer id) {
        SysRole role = sysRoleService.getRole(id);
        return Result.success(role);
    }
}
