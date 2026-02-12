package com.kk.mumuchat.system.file.controller.admin;

import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.validate.V_A;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.system.file.controller.base.BSysFileController;
import com.kk.mumuchat.system.file.domain.dto.SysFileDto;
import com.kk.mumuchat.system.file.domain.query.SysFileQuery;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 系统服务|素材模块|文件管理|管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/file")
public class ASysFileController extends BSysFileController {

    /**
     * 查询菜单文件列表
     */
    @Override
    @GetMapping("/list")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_LIST)")
    public AjaxResult list(SysFileQuery file) {
        return super.list(file);
    }

    /**
     * 查询菜单文件详细
     */
    @Override
    @GetMapping(value = "/{id}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    /**
     * 菜单文件新增
     */
    @Override
    @PostMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_ADD)")
    @Log(title = "系统服务|素材模块|文件管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysFileDto file) {
        return super.add(file);
    }

    /**
     * 菜单文件修改
     */
    @Override
    @PutMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_EDIT)")
    @Log(title = "系统服务|素材模块|文件管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysFileDto file) {
        return super.edit(file);
    }

    /**
     * 菜单文件批量删除
     */
    @Override
    @DeleteMapping("/batch/{idList}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_DEL)")
    @Log(title = "系统服务|素材模块|文件管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }

}
