package local.ateng.boot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import local.ateng.boot.system.entity.SysLogOperate;
import local.ateng.boot.system.mapper.SysLogOperateMapper;
import local.ateng.boot.system.service.SysLogOperateService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志记录 服务层实现。
 *
 * @author 孔余
 * @since 1.0.0
 */
@Service
public class SysLogOperateServiceImpl extends ServiceImpl<SysLogOperateMapper, SysLogOperate> implements SysLogOperateService {

    @Override
    @Async
    @EventListener
    public void logAdd(SysLogOperate entity) {
        this.save(entity);
    }

}
