package com.kk.mumuchat.system.api.organize.feign.factory;

import com.kk.mumuchat.common.core.exception.ServiceException;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.feign.RemoteDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统服务|组织模块|部门服务 降级处理
 *
 * @author mumuchat
 */
@Slf4j
@Component
public class RemoteDeptFallbackFactory implements FallbackFactory<RemoteDeptService> {

    @Override
    public RemoteDeptService create(Throwable throwable) {
        log.error("部门服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new RemoteDeptService() {
            @Override
            public R<SysDeptDto> addInner(SysDeptDto dept) {
                return R.fail("新增部门失败:" + throwable.getMessage());
            }

            @Override
            public R<SysDeptDto> selectByIdInner(Serializable id) {
                log.error("获取部门信息失败:{}", throwable.getMessage());
                throw new ServiceException("获取部门信息失败");
            }

            @Override
            public R<List<SysDeptDto>> selectListByIdsInner(Collection<? extends Serializable> ids) {
                log.error("获取部门信息失败:{}", throwable.getMessage());
                throw new ServiceException("获取部门信息失败");
            }
        };
    }
}