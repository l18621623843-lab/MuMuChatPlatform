package com.kk.mumuchat.gen.controller.admin;

import com.alibaba.fastjson2.JSONObject;
import com.kk.mumuchat.common.core.utils.core.ConvertUtil;
import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.validate.V_E;
import com.kk.mumuchat.common.log.annotation.Log;
import com.kk.mumuchat.common.log.enums.BusinessType;
import com.kk.mumuchat.gen.controller.base.BGenController;
import com.kk.mumuchat.gen.domain.dto.GenTableColumnDto;
import com.kk.mumuchat.gen.domain.dto.GenTableDto;
import com.kk.mumuchat.gen.domain.query.GenTableColumnQuery;
import com.kk.mumuchat.gen.domain.query.GenTableQuery;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * 代码生成管理 业务处理
 *
 * @author mumuchat
 */
@RestController
@RequestMapping("/admin/gen")
public class AGenController extends BGenController {

    @Override
    @GetMapping("/list")
    @Operation(summary = "查询代码生成列表")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_LIST)")
    public AjaxResult list(GenTableQuery table) {
        return super.list(table);
    }

    @GetMapping("/db/list")
    @Operation(summary = "查询数据库列表")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_LIST)")
    public AjaxResult dataList(GenTableQuery table) {
        startPage();
        List<GenTableDto> list = baseService.selectDbTableList(table);
        return getDataTable(list);
    }

    @GetMapping(value = "/column/list")
    @Operation(summary = "查询数据表字段列表")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_LIST)")
    public AjaxResult columnList(GenTableColumnQuery query) {
        startPage();
        List<GenTableColumnDto> list = subService.selectList(query);
        return getDataTable(list);
    }

    @Override
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询代码生成详细")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    @GetMapping(value = "/sub/{id}")
    @Operation(summary = "查询代码生成详细 | 包含代码生成数据")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_SINGLE)")
    public AjaxResult getInfoExtra(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    @Operation(summary = "预览代码")
    @GetMapping("/preview/{tableId}")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_PREVIEW)")
    public AjaxResult previewMulti(@PathVariable("tableId") Long tableId) {
        List<JSONObject> dataMap = baseService.previewCode(tableId);
        return success(dataMap);
    }

    @PostMapping("/importTable")
    @Operation(summary = "导入表结构（保存）")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_IMPORT)")
    @Log(title = "代码生成", businessType = BusinessType.IMPORT)
    public AjaxResult importTableSave(@RequestParam("tables") String tables, @RequestParam(value = "sourceName", required = false) String sourceName) {
        String[] tableNames = ConvertUtil.toStrArray(tables);
        // 查询表信息
        List<GenTableDto> tableList = baseService.selectDbTableListByNames(tableNames, sourceName);
        baseService.importGenTable(tableList, sourceName);
        return success();
    }

    @Override
    @PutMapping
    @Operation(summary = "代码生成修改")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_EDIT)")
    @Log(title = "代码生成管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody GenTableDto table) {
        return super.edit(table);
    }

    @GetMapping("/download/{tableId}")
    @Operation(summary = "生成代码（下载方式）")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_CODE)")
    @Log(title = "代码生成", businessType = BusinessType.GEN_CODE)
    public void downloadMulti(HttpServletResponse response, @PathVariable("tableId") Long tableId) throws IOException {
        byte[] data = baseService.downloadCode(tableId);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     */
    @Operation(summary = "生成代码（自定义路径）")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_CODE)")
    @Log(title = "代码生成", businessType = BusinessType.GEN_CODE)
    @GetMapping("/generate/{tableId}")
    public AjaxResult genMultiCode(@PathVariable("tableId") Long tableId) {
        baseService.generatorCode(tableId);
        return success();
    }

    @GetMapping("/batchGenCode")
    @Operation(summary = "批量生成代码")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_CODE)")
    @Log(title = "代码生成", businessType = BusinessType.GEN_CODE)
    public void batchMultiGenCode(HttpServletResponse response, @RequestParam Long[] ids) throws IOException {
        byte[] data = baseService.downloadCode(ids);
        genCode(response, data);
    }

    @Override
    @DeleteMapping("/batch/{idList}")
    @Operation(summary = "代码生成批量删除")
    @PreAuthorize("@ss.hasAuthority(@Auth.GEN_GENERATE_DEL)")
    @Log(title = "代码生成管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }
}
