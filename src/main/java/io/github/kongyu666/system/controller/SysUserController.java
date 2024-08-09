package local.ateng.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import local.ateng.boot.common.annotation.Log;
import local.ateng.boot.common.enums.BusinessType;
import local.ateng.boot.common.validation.AddGroup;
import local.ateng.boot.common.validation.UpdateGroup;
import local.ateng.boot.system.bo.SysUserLoginBo;
import local.ateng.boot.system.bo.SysUserPageBo;
import local.ateng.boot.system.entity.SysUser;
import local.ateng.boot.system.service.SysUserService;
import local.ateng.boot.system.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统设置/用户设置
 *
 * @author 孔余
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/user")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SysUserController {

    private final SysUserService sysUserService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 登录
     */
    @SaIgnore
    @PostMapping("/login")
    public SysUserVo login(@Validated @RequestBody SysUserLoginBo bo) {
        return sysUserService.loginUser(bo);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public void logout() {
        StpUtil.logout();
    }

    /**
     * 新增
     */
    @Log(module = "用户设置", desc = "新增用户", type = BusinessType.ADD)
    @SaCheckRole("admin")
    @SaCheckPermission("system.user.add")
    @PostMapping("/add")
    public void add(@Validated(AddGroup.class) @RequestBody SysUser entity) {
        sysUserService.addUser(entity);
    }

    /**
     * 查询所有
     */
    @SaCheckPermission(value = "system.user.get", orRole = "admin")
    @GetMapping("/list")
    public List<SysUser> list() {
        return sysUserService.list();
    }

    /**
     * 分页查询
     */
    @SaCheckPermission(value = "system.user.get", orRole = "admin")
    @PostMapping("/page")
    public Page<SysUser> page(@Validated @RequestBody SysUserPageBo bo) {
        return sysUserService.pageUser(bo);
    }

    /**
     * 批量删除
     */
    @Log(module = "用户设置", desc = "删除用户", type = BusinessType.DELETE)
    @SaCheckRole("admin")
    @SaCheckPermission("system.user.delete")
    @PostMapping("/delete-batch")
    public void deleteBatch(
            @Validated @RequestBody
            @Size(min = 1, message = "id列表不能为空")
            List<Long> ids
    ) {
        sysUserService.deleteBatchUser(ids);
    }

    /**
     * 更新
     */
    @Log(module = "用户设置", desc = "更新用户", type = BusinessType.UPDATE)
    @SaCheckRole("admin")
    @SaCheckPermission("system.user.update")
    @PostMapping("/update")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysUser entity) {
        sysUserService.updateUser(entity);
    }

    /**
     * 获取详细信息
     */
    @SaCheckPermission(value = "system.user.get", orRole = "admin")
    @GetMapping("/get")
    public SysUser get(
            @NotNull(message = "id不能为空")
            @Min(value = 1, message = "id不正确")
            Integer id
    ) {
        return sysUserService.getById(id);
    }

    /**
     * 获取账号权限
     */
    @GetMapping("/get-permission-list")
    public List<String> getPermissionList() {
        List<String> permissionList = StpUtil.getPermissionList();
        return permissionList;
    }

    /**
     * 获取账号角色
     */
    @GetMapping("/get-role-list")
    public List<String> getRoleList() {
        List<String> roleList = StpUtil.getRoleList();
        return roleList;
    }

    /**
     * Websocket示例
     *
     * @param message
     * @return
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) {
        log.info("客户端发送了一条消息: {}", message);
        messagingTemplate.convertAndSend("/topic/greetings", StrUtil.format("服务端定时发送了一条数据：{}", DateUtil.now()));
        return "Hello, " + message + "!";
    }

    @GetMapping("/user-retry")
    @SaIgnore
    public void userRetry() {
        sysUserService.userRetry();
    }

}
