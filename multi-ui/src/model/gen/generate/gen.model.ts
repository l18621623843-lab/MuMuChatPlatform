import { BaseEntity, BasicFetchResult, BasicPageParams, SubBaseEntity } from '@/model';
import { GenModeEnum, GenStatusEnum, SourceModeEnum, TemplateTypeEnum } from '@/enums/gen/generate';
import { DicYesNoEnum } from '@/enums';

/** genTable item model */
export interface GenTableIM extends SubBaseEntity<GenTableColumnIM> {
  /** Id */
  id: string;
  /** 名称 */
  name: string;
  /** 表描述 */
  comment: string;
  /** 实体类名称(首字母大写) */
  className: string;
  /** 实体类名称前缀(首字母大写) */
  prefix: string;
  /** 使用的模板（base单表操作 tree树表操作 merge关联操作） */
  tplCategory: TemplateTypeEnum;
  /** 生成后端包路径 */
  rdPackageName: string;
  /** 生成权限名 */
  fePackageName: string;
  /** 生成模块路径 */
  moduleName: string;
  /** 生成业务名 */
  businessName: string;
  /** 生成权限标识 */
  authorityName: string;
  /** 生成功能名 */
  functionName: string;
  /** 生成作者 */
  functionAuthor: string;
  /** 生成路径类型（0默认路径 1自定义路径） */
  genType: GenModeEnum;
  /** 后端生成路径（不填默认项目路径） */
  genPath: string;
  /** 前端生成路径（不填默认项目路径） */
  uiPath: string;
  /** 其它生成选项 */
  options: OptionIM;
}

/** genTable list model */
export type GenTableLM = GenTableIM[];

/** genTable param model */
export interface GenTablePM extends SubBaseEntity<GenTableColumnIM> {
  /** Id */
  id?: string;
  /** 名称 */
  name?: string;
  /** 使用的模板（base单表操作 tree树表操作 merge关联操作） */
  tplCategory?: TemplateTypeEnum;
}

/** genTable page param model */
export type GenTablePPM = BasicPageParams & GenTablePM;

/** genTable list result model */
export type GenTableLRM = BasicFetchResult<GenTableIM>;

/** genTableColumn item model */
export interface GenTableColumnIM extends BaseEntity {
  id: string;
  tableId: string;
  name: string;
  comment: string;
  type: string;
  javaType: string;
  javaField: string;
  isPk: boolean;
  isIncrement: boolean;
  isRequired: boolean;
  isView: boolean;
  isInsert: boolean;
  isEdit: boolean;
  isList: boolean;
  isQuery: boolean;
  isImport: boolean;
  isExport: boolean;
  isCover: boolean;
  isHide: boolean;
  isUnique: boolean;
  queryType: string;
  htmlType: string;
  dictType: string;
  sort: number;
}

/** genTableColumn list model */
export type GenTableColumnLM = GenTableColumnIM[];

/** genTableColumn param model */
export type GenTableColumnPM = GenTableColumnIM;

/** genTableColumn page param model */
export type GenTableColumnPPM = BasicPageParams & GenTableColumnPM;

/** genTableColumn list result model */
export type GenTableColumnLRM = BasicFetchResult<GenTableColumnIM>;

/** gen code item model */
export interface GenCodeIM {
  name: string;
  language: string;
  template: string;
}

/** gen code list model */
export type GenCodeLM = GenCodeIM[];

/** option item model */
export interface OptionIM {
  /** 默认配置 */
  basicInfo: {
    /** 多租户模式 */
    isTenant: GenStatusEnum;
    /** 源策略模式 */
    sourceMode: SourceModeEnum;
    /** 依赖缩写模式 */
    dependMode: DicYesNoEnum;
  };
  /** 字段配置 */
  fieldInfo: {
    /** 树编码字段 */
    treeCode: string;
    /** 树父编码字段 */
    parentId: string;
    /** 树名称字段 */
    treeName: string;
    /** 序号字段 */
    sort: string;
    /** 祖籍列表字段 */
    ancestors: string;
    /** 层级字段 */
    level: string;
  };
  /** 接口配置 */
  apiInfo: {
    /** 列表查询 */
    apiList: GenStatusEnum;
    /** 详情查询 */
    apiGetInfo: GenStatusEnum;
    /** 新增 */
    apiAdd: GenStatusEnum;
    /** 修改 */
    apiEdit: GenStatusEnum;
    /** 存在状态修改 */
    hasApiES: GenStatusEnum;
    /** 状态修改 */
    apiEditStatus: GenStatusEnum;
    /** 批量删除 */
    apiBatchRemove: GenStatusEnum;
    /** 导入 */
    apiImport: GenStatusEnum;
    /** 导出 */
    apiExport: GenStatusEnum;
    /** 缓存 */
    apiCache: GenStatusEnum;
  };
  /** 菜单配置 */
  menuInfo: {
    /** 菜单生成Id */
    idGenerator: string;
    /** 归属模块 */
    parentModuleId: string;
    /** 上级菜单 */
    parentMenuId: string;
  };
}
