package local.ateng.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import local.ateng.boot.common.annotation.Log;
import local.ateng.boot.common.enums.BusinessType;
import local.ateng.boot.system.entity.SysRole;
import local.ateng.boot.system.service.SysRoleService;
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
    public void add(@RequestBody SysRole entity) {
        sysRoleService.addRole(entity);
    }

    /**
     * 查看列表
     */
    @GetMapping("/list")
    public List<SysRole> list() {
        return sysRoleService.listRole();
    }

    /**
     * 查看单个
     */
    @GetMapping("/get")
    public SysRole get(Integer id) {
        return sysRoleService.getRole(id);
    }
}
