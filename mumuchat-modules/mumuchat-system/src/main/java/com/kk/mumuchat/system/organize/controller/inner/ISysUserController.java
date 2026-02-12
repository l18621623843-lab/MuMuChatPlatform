package com.kk.mumuchat.system.organize.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.api.organize.feign.RemoteUserService;
import com.kk.mumuchat.system.organize.controller.base.BSysUserController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统服务|组织模块|用户管理|内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/user")
public class ISysUserController extends BSysUserController implements RemoteUserService {

    @Override
    @GetMapping("/id")
    @Operation(summary = "根据Id查询用户信息")
    public R<SysUserDto> selectByIdInner(@RequestParam Serializable id) {
        return super.selectByIdInner(id);
    }

    @Override
    @GetMapping("/ids")
    @Operation(summary = "根据Ids查询用户信息集合")
    public R<List<SysUserDto>> selectListByIdsInner(@RequestParam Collection<? extends Serializable> ids) {
        return super.selectListByIdsInner(ids);
    }

    @PostMapping
    @Operation(summary = "新增用户")
    public R<SysUserDto> addInner(@RequestBody SysUserDto user) {
        return baseService.addInner(user) > 0 ? R.ok(user) : R.fail();
    }
}
