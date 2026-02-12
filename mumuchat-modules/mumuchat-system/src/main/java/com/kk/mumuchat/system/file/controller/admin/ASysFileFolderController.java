package com.kk.mumuchat.system.file.controller.admin;

import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.validate.V_A;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.system.file.controller.base.BSysFileFolderController;
import com.kk.mumuchat.system.file.domain.dto.SysFileFolderDto;
import com.kk.mumuchat.system.file.domain.query.SysFileFolderQuery;
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
 * 系统服务|素材模块|文件分类管理|管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/folder")
public class ASysFileFolderController extends BSysFileFolderController {

    /**
     * 查询菜单文件分类列表
     */
    @Override
    @GetMapping("/list")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_FOLDER_LIST)")
    public AjaxResult list(SysFileFolderQuery fileFolder) {
        return super.list(fileFolder);
    }

    /**
     * 查询菜单文件分类详细
     */
    @Override
    @GetMapping(value = "/{id}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_FOLDER_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    /**
     * 菜单文件分类新增
     */
    @Override
    @PostMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_FOLDER_ADD)")
    @Log(title = "系统服务|素材模块|文件分类管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysFileFolderDto fileFolder) {
        return super.add(fileFolder);
    }

    /**
     * 菜单文件分类修改
     */
    @Override
    @PutMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_FOLDER_EDIT)")
    @Log(title = "系统服务|素材模块|文件分类管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysFileFolderDto fileFolder) {
        return super.edit(fileFolder);
    }

    /**
     * 菜单文件分类批量删除
     */
    @Override
    @DeleteMapping("/batch/{idList}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_FILE_FOLDER_DEL)")
    @Log(title = "系统服务|素材模块|文件分类管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }

}
