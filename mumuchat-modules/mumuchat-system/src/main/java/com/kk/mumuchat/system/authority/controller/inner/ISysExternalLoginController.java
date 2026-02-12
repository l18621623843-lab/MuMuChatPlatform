package com.kk.mumuchat.system.authority.controller.inner;

import com.kk.mumuchat.common.cache.utils.SourceUtil;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.utils.core.IdUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.web.model.SysSource;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.common.web.entity.controller.BasisController;
import com.kk.mumuchat.system.api.model.LoginExternal;
import com.kk.mumuchat.system.api.organize.domain.dto.SysEnterpriseDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.authority.service.ISysLoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 外系统端登录|内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/login/external")
public class ISysExternalLoginController extends BasisController {

    @Resource
    private ISysLoginService loginService;

    @GetMapping
    @Operation(summary = "第三方系统获取登录信息")
    public R<LoginExternal> getLoginInfoInner(@RequestParam String enterpriseName, @RequestParam String secret) {
        SysEnterpriseDto enterprise = loginService.loginByEnterpriseName(enterpriseName);
        // 不存在直接返回空数据|与网络调用错误区分
        if (ObjectUtil.isNull(enterprise)) {
            return R.ok(null, "企业账号不存在");
        }
        SecurityContextHolder.setEnterpriseId(enterprise.getId().toString());
        SecurityContextHolder.setIsLessor(enterprise.getIsLessor());
        SysSource source = SourceUtil.getSourceCache(enterprise.getStrategyId());
        // 不存在直接返回空数据|与网络调用错误区分
        if (ObjectUtil.isNull(source)) {
            return R.ok(null, "数据源不存在");
        }
        SecurityContextHolder.setSourceName(source.getMaster());
        SysUserDto user = loginService.loginByAdminUser();
        // 不存在直接返回空数据|与网络调用错误区分
        if (ObjectUtil.isNull(user)) {
            return R.ok(null, "用户账号不存在");
        }
        SecurityContextHolder.setUserType(user.getUserType());

        LoginExternal loginUser = new LoginExternal();
        loginUser.initEnterprise(enterprise);
        loginUser.initUser(user);
        loginUser.initSource(source);
        loginUser.setSignature(IdUtil.simpleUUID());
        loginUser.setSecret(secret);
        return R.ok(loginUser);
    }
}
