package com.kk.mumuchat.common.web.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kk.mumuchat.common.security.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 自动填充处理器
 *
 * @author mumuchat
 */
public class XueYiMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter("createBy"))
            this.strictInsertFill(metaObject, "createBy", SecurityUtils::getUserId, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter("updateBy"))
            this.strictUpdateFill(metaObject, "updateBy", SecurityUtils::getUserId, Long.class);
    }
}