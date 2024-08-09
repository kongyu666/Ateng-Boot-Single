package local.ateng.boot.common.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import local.ateng.boot.system.entity.SysUser;
import local.ateng.boot.system.vo.LoginUserVo;
import local.ateng.boot.system.vo.SysUserVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

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

    public static final String LOGIN_USER_KEY = "loginUser";
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
        sysUserVo.setSatoken(tokenValue);
        SaSession session = StpUtil.getSession();
        session.set("userInfo", sysUserVo);
        return sysUserVo;
    }

    /**
     * 获取用户(多级缓存)
     */
    public static LoginUserVo getLoginUserVo() {
        return (LoginUserVo) getStorageIfAbsentSet(LOGIN_USER_KEY, () -> {
            SaSession session = StpUtil.getTokenSession();
            if (ObjectUtil.isNull(session)) {
                return null;
            }
            return session.get(LOGIN_USER_KEY);
        });
    }

    /**
     * 获取用户基于token
     */
    public static LoginUserVo getLoginUserVo(String token) {
        SaSession session = StpUtil.getTokenSessionByToken(token);
        if (ObjectUtil.isNull(session)) {
            return null;
        }
        return (LoginUserVo) session.get(LOGIN_USER_KEY);
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return Convert.toLong(getExtra(USER_KEY));
    }

    /**
     * 获取租户ID
     */
    public static String getTenantId() {
        return Convert.toStr(getExtra(TENANT_KEY));
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        return Convert.toLong(getExtra(DEPT_KEY));
    }

    private static Object getExtra(String key) {
        return getStorageIfAbsentSet(key, () -> StpUtil.getExtra(key));
    }

    /**
     * 获取用户账户
     */
    public static String getUsername() {
        return getLoginUserVo().getUsername();
    }

    public static Object getStorageIfAbsentSet(String key, Supplier<Object> handle) {
        try {
            Object obj = SaHolder.getStorage().get(key);
            if (ObjectUtil.isNull(obj)) {
                obj = handle.get();
                SaHolder.getStorage().set(key, obj);
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }
}
