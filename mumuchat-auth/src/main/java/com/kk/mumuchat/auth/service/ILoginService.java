package com.kk.mumuchat.auth.service;

import com.kk.mumuchat.auth.form.RegisterBody;

/**
 * 登录校验 服务层
 *
 * @author xueyi
 */
public interface ILoginService {

    /**
     * 注册
     */
    void register(RegisterBody registerBody);

}
