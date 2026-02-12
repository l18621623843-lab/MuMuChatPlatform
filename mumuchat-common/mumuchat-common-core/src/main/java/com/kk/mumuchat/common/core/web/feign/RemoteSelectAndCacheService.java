package com.kk.mumuchat.common.core.web.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.entity.base.BaseEntity;
import com.kk.mumuchat.common.core.web.result.R;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 远程查询&缓存服务层
 *
 * @param <D> Dto
 * @author xueyi
 */
public interface RemoteSelectAndCacheService<D extends BaseEntity> extends RemoteSelectService<D> {

    /**
     * 刷新缓存
     *
     * @return 结果
     */
    @GetMapping(value = "/refresh", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> refreshCacheInner();
}
