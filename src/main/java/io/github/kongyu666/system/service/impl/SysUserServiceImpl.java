package local.ateng.boot.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.aizuda.snailjob.client.core.annotation.Retryable;
import com.feiniaojin.gracefulresponse.GracefulResponse;
import com.mybatisflex.core.mask.MaskManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import local.ateng.boot.common.enums.AppCodeEnum;
import local.ateng.boot.common.utils.SaTokenUtils;
import local.ateng.boot.system.bo.SysUserLoginBo;
import local.ateng.boot.system.bo.SysUserPageBo;
import local.ateng.boot.system.entity.SysUser;
import local.ateng.boot.system.entity.SysUserRole;
import local.ateng.boot.system.mapper.SysUserMapper;
import local.ateng.boot.system.service.SysRoleService;
import local.ateng.boot.system.service.SysUserRoleService;
import local.ateng.boot.system.service.SysUserService;
import local.ateng.boot.system.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static local.ateng.boot.system.entity.table.SysPermissionTableDef.SYS_PERMISSION;
import static local.ateng.boot.system.entity.table.SysRolePermissionTableDef.SYS_ROLE_PERMISSION;
import static local.ateng.boot.system.entity.table.SysRoleTableDef.SYS_ROLE;
import static local.ateng.boot.system.entity.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static local.ateng.boot.system.entity.table.SysUserTableDef.SYS_USER;

/**
 * 存储用户的基本信息 服务层实现。
 *
 * @author 孔余
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;

    @Override
    public SysUserVo loginUser(SysUserLoginBo bo) {
        String userName = bo.getUserName();
        String password = bo.getPassword();
        SysUser user;
        try {
            // 跳过脱敏处理
            MaskManager.skipMask();
            user = this.queryChain()
                    .select(SYS_USER.ALL_COLUMNS)
                    .from(SYS_USER)
                    .where(SYS_USER.USER_NAME.eq(userName))
                    .one();
        } finally {
            // 恢复脱敏处理
            MaskManager.restoreMask();
        }
        // 判断用户是否存在
        GracefulResponse.wrapAssert(AppCodeEnum.AUTH_USER_NOT_FOUND.getCode(), () -> Assert.isTrue(!ObjectUtils.isEmpty(user), AppCodeEnum.AUTH_USER_NOT_FOUND.getDescription()));
        // 校验密码
        boolean checkpw = BCrypt.checkpw(password, user.getPassword());
        GracefulResponse.wrapAssert(AppCodeEnum.AUTH_PASSWORD_INCORRECT.getCode(), () -> Assert.isTrue(checkpw, AppCodeEnum.AUTH_PASSWORD_INCORRECT.getDescription()));
        // 登录用户
        SysUserVo loginUser = SaTokenUtils.login(user);
        // 返回用户信息
        return loginUser;
    }

    @Override
    @Transactional
    public void addUser(SysUser entity) {
        String userName = entity.getUserName();
        String password = entity.getPassword();
        SysUser user = this.queryChain()
                .select()
                .from(SYS_USER)
                .where(SYS_USER.USER_NAME.eq(userName))
                .one();
        // 判断用户是否存在
        GracefulResponse.wrapAssert(AppCodeEnum.AUTH_USER_ALREADY_EXISTS.getCode(), () -> Assert.isTrue(ObjectUtils.isEmpty(user), AppCodeEnum.AUTH_USER_ALREADY_EXISTS.getDescription()));
        // 新增用户
        String passwordEncrypt = BCrypt.hashpw(password);
        entity.setPassword(passwordEncrypt);
        this.save(entity);
        // 关联角色
        Integer userRoleId = sysRoleService
                .queryChain()
                .select(SYS_ROLE.ROLE_ID)
                .from(SYS_ROLE)
                .where(SYS_ROLE.ROLE_NAME.eq("user"))
                .one()
                .getRoleId();
        sysUserRoleService.addUserRole(new SysUserRole(entity.getUserId(), userRoleId));
    }

    @Override
    public Page<SysUser> pageUser(SysUserPageBo bo) {
        String nickName = bo.getNickName();
        Page<SysUser> page = this.queryChain()
                .select()
                .from(SYS_USER)
                .where(SYS_USER.NICK_NAME.like(nickName, !ObjectUtils.isEmpty(nickName)))
                .page(new Page<>(bo.getPageNumber(), bo.getPageSize()));
        return page;
    }

    @Override
    public void deleteBatchUser(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void updateUser(SysUser entity) {
        String userName = entity.getUserName();
        String password = entity.getPassword();
        SysUser user = this.getById(entity.getUserId());
        // 判断用户是否存在
        GracefulResponse.wrapAssert(AppCodeEnum.AUTH_USER_NOT_FOUND.getCode(), () -> Assert.isTrue(!ObjectUtils.isEmpty(user), AppCodeEnum.AUTH_USER_NOT_FOUND.getDescription()));
        GracefulResponse.wrapAssert(AppCodeEnum.AUTH_USER_NOT_INCONSISTENT.getCode(), () -> Assert.isTrue(user.getUserName().equals(userName), AppCodeEnum.AUTH_USER_NOT_INCONSISTENT.getDescription()));
        // 更新用户
        String passwordEncrypt = BCrypt.hashpw(password);
        entity.setPassword(passwordEncrypt);
        entity.setUpdateTime(DateUtil.date().toTimestamp());
        this.updateById(entity, true);
    }

    @Override
    public List<String> getUserRoleName(Integer id) {
        // 根据用户id查询角色列表
        List<String> list = this
                .queryChain()
                .select(SYS_ROLE.ROLE_NAME)
                .from(SYS_USER)
                .where(SYS_USER.USER_ID.eq(id))
                .leftJoin(SYS_USER_ROLE)
                .on(SYS_USER.USER_ID.eq(SYS_USER_ROLE.USER_ID))
                .leftJoin(SYS_ROLE)
                .on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
                .listAs(String.class);
        return list;
    }

    @Override
    public List<String> getUserPermissionName(Integer id) {
        // 根据用户id查询权限列表
        List<String> list = this
                .queryChain()
                .select(SYS_PERMISSION.PERMISSION_NAME)
                .from(SYS_USER)
                .where(SYS_USER.USER_ID.eq(id))
                .leftJoin(SYS_USER_ROLE)
                .on(SYS_USER.USER_ID.eq(SYS_USER_ROLE.USER_ID))
                .leftJoin(SYS_ROLE)
                .on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
                .leftJoin(SYS_ROLE_PERMISSION)
                .on(SYS_ROLE_PERMISSION.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
                .leftJoin(SYS_PERMISSION)
                .on(SYS_PERMISSION.PERMISSION_ID.eq(SYS_ROLE_PERMISSION.PERMISSION_ID))
                .listAs(String.class);
        return list;
    }

    @Override
    // 这个函数里面我们设置重试次数为4，每次间隔10s
    @Retryable(scene = "userRetry", localTimes = 4, localInterval = 10)
    public void userRetry() {
        log.info("local retry 方法开始执行");
        double i = 1 / 0;
    }
}
