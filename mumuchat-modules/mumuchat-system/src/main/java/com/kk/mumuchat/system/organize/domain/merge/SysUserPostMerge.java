package com.kk.mumuchat.system.organize.domain.merge;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kk.mumuchat.common.core.web.tenant.base.TBasisEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 系统服务|组织模块|用户-岗位关联 持久化对象
 *
 * @author mumuchat
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_post_merge")
public class SysUserPostMerge extends TBasisEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户Id */
    private Long userId;

    /** 岗位Id */
    private Long postId;

    public SysUserPostMerge(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

}
