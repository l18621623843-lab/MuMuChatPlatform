package com.kk.mumuchat.system.organize.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.system.api.organize.domain.dto.SysPostDto;
import com.kk.mumuchat.system.api.organize.feign.RemotePostService;
import com.kk.mumuchat.system.organize.controller.base.BSysPostController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务|组织模块|岗位管理|内部调用 业务处理
 *
 * @author mumuchat
 */
@InnerAuth
@RestController
@RequestMapping("/inner/post")
public class ISysPostController extends BSysPostController implements RemotePostService {

    @PostMapping
    @Operation(summary = "新增岗位")
    public R<SysPostDto> addInner(@RequestBody SysPostDto post) {
        return baseService.addInner(post) > 0 ? R.ok(post) : R.fail();
    }
}
