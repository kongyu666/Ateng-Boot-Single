package io.github.kongyu666.common.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import io.github.kongyu666.system.entity.SysUser;
import io.github.kongyu666.system.vo.SysUserVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录鉴权助手
 * <p>
 * user_type 为 用户类型 同一个用户表 可以有多种用户类型 例如 pc,app
 * deivce 为 设备类型 同一个用户类型 可以有 多种设备类型 例如 web,ios
 * 可以组成 用户类型与设备类型多对多的 权限灵活控制
 * <p>
 * 多用户体系 针对 多种用户类型 但权限控制不一致
 * 可以组成 多用户类型表与多设备类型 分别控制权限
 *
 * @author Lion Li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaTokenUtils {

    public static final String LOGIN_USER_KEY = "userInfo";
    public static final String TENANT_KEY = "tenantId";
    public static final String USER_KEY = "userId";
    public static final String DEPT_KEY = "deptId";
    public static final String CLIENT_KEY = "clientid";
    public static final String TENANT_ADMIN_KEY = "isTenantAdmin";


    public static SysUserVo login(SysUser user) {
        // 登录用户
        String clientType = HttpUtils.getClientType();
        StpUtil.login(user.getUserId(), clientType);
        // 存储权限信息
        List<String> roleList = StpUtil.getRoleList();
        List<String> permissionList = StpUtil.getPermissionList();
        String tokenValue = StpUtil.getTokenValue();
        // 存储到Session
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtil.copyProperties(user, sysUserVo);
        sysUserVo.setPassword("******");
        sysUserVo.setRoleList(roleList);
        sysUserVo.setPermissionList(permissionList);
        sysUserVo.setToken(tokenValue);
        SaSession session = StpUtil.getSession();
        session.set(LOGIN_USER_KEY, sysUserVo);
        return sysUserVo;
    }

    /**
     * 获取用户信息
     */
    public static SysUserVo getSysUserVo() {
        SaSession session = StpUtil.getSession();
        JSONObject userInfoJson = (JSONObject) session.get(LOGIN_USER_KEY);
        return userInfoJson.toJavaObject(SysUserVo.class);
    }

}
