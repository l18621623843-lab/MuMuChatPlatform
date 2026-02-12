package com.kk.mumuchat.common.web.entity.mapper.basic;

import com.github.yulichang.base.MPJBaseMapper;
import com.kk.mumuchat.common.core.web.entity.base.TreeEntity;

/**
 * 数据层 树型基类数据处理
 * 仅用于基类调用 禁止被继承使用
 *
 * @author xueyi
 */
public interface BasicTreeMapper<D> extends MPJBaseMapper<TreeEntity<D>> {
}
