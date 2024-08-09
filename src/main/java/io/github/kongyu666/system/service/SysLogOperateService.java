package local.ateng.boot.system.service;

import com.mybatisflex.core.service.IService;
import local.ateng.boot.system.entity.SysLogOperate;

/**
 * 操作日志记录 服务层。
 *
 * @author 孔余
 * @since 1.0.0
 */
public interface SysLogOperateService extends IService<SysLogOperate> {
    // 保存日志
    void logAdd(SysLogOperate entity);
}
