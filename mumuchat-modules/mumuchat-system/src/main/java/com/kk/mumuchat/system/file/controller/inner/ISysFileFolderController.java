package com.kk.mumuchat.system.file.controller.inner;

import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.system.file.controller.base.BSysFileFolderController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务|素材模块|文件分类管理|内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/folder")
public class ISysFileFolderController extends BSysFileFolderController {
}
