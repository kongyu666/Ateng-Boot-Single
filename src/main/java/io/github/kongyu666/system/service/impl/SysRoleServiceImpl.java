package local.ateng.boot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import local.ateng.boot.system.entity.SysRole;
import local.ateng.boot.system.mapper.SysRoleMapper;
import local.ateng.boot.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储系统中的角色信息 服务层实现。
 *
 * @author 孔余
 * @since 1.0.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public void addRole(SysRole entity) {
        this.save(entity);
    }

    @Override
    public List<SysRole> listRole() {
        return this.list();
    }

    @Override
    public SysRole getRole(Integer id) {
        return this.getById(id);
    }
}
