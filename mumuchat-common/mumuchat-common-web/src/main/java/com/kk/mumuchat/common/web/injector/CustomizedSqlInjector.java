package com.kk.mumuchat.common.web.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.yulichang.injector.MPJSqlInjector;
import com.kk.mumuchat.common.web.method.InsertBatchMethod;
import com.kk.mumuchat.common.web.method.UpdateBatchMethod;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * sql注入器
 *
 * @author mumuchat
 */
public class CustomizedSqlInjector extends MPJSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
        methodList.add(new InsertBatchMethod());
        methodList.add(new UpdateBatchMethod());
        return methodList;
    }
}
