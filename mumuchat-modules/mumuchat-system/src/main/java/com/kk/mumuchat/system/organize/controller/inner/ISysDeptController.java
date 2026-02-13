package com.kk.mumuchat.system.organize.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.feign.RemoteDeptService;
import com.kk.mumuchat.system.organize.controller.base.BSysDeptController;
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
 * 系统服务|组织模块|部门管理|内部调用 业务处理
 *
 * @author mumuchat
 */
@InnerAuth
@RestController
@RequestMapping("/inner/dept")
public class ISysDeptController extends BSysDeptController implements RemoteDeptService {

    @Override
    @GetMapping("/id")
    @Operation(summary = "根据Id查询部门信息")
    public R<SysDeptDto> selectByIdInner(@RequestParam("id") Serializable id) {
        return super.selectByIdInner(id);
    }

    @Override
    @GetMapping("/ids")
    @Operation(summary = "根据Ids查询部门信息集合")
    public R<List<SysDeptDto>> selectListByIdsInner(@RequestParam("ids") Collection<? extends Serializable> ids) {
        return super.selectListByIdsInner(ids);
    }

    @PostMapping
    @Operation(summary = "新增部门")
    public R<SysDeptDto> addInner(@RequestBody SysDeptDto dept) {
        return baseService.addInner(dept) > 0 ? R.ok(dept) : R.fail();
    }
}
