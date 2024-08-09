package local.ateng.boot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import local.ateng.boot.system.entity.SysRolePermission;
import local.ateng.boot.system.mapper.SysRolePermissionMapper;
import local.ateng.boot.system.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现角色与权限之间的多对多关系 服务层实现。
 *
 * @author 孔余
 * @since 1.0.0
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Override
    public void addRolePermission(SysRolePermission entity) {
        this.save(entity);
    }

    @Override
    public List<SysRolePermission> listRolePermission() {
        return this.list();
    }

    @Override
    public SysRolePermission getRolePermission(Integer id) {
        return this.getById(id);
    }
}
