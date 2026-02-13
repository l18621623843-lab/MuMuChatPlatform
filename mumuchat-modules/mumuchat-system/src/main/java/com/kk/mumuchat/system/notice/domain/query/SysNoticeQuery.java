package com.kk.mumuchat.system.notice.domain.query;

import com.kk.mumuchat.system.notice.domain.po.SysNoticePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统服务|消息模块|通知公告 数据查询对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticeQuery extends SysNoticePo {

    @Serial
    private static final long serialVersionUID = 1L;

}
