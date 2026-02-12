import { BasicFetchResult, BasicPageParams, SubBaseEntity } from '@/model';
import { SysJobLogIM } from './jobLog.model';
import {
  JobConcurrentEnum,
  JobGroupEnum,
  JobMisfireEnum,
  JobStatusEnum,
} from '@/enums/system/system';

/** job info model */
export interface SysJobIM extends SubBaseEntity<SysJobLogIM> {
  /** Id */
  id: string;
  /** 任务名称 */
  name: string;
  /** 任务组名 */
  jobGroup: JobGroupEnum;
  /** 归属服务 */
  serverType: string;
  /** 请求类型 */
  httpType: string;
  /** 请求地址 */
  apiUrl: string;
  /** 调用目标字符串 */
  invokeTarget: string;
  /** 调用租户字符串 */
  invokeTenant: string;
  /** cron执行表达式 */
  cronExpression: string;
  /** 计划执行错误策略（0默认 1立即执行 2执行一次 3放弃执行） */
  misfirePolicy: JobMisfireEnum;
  /** 是否并发执行（0允许 1禁止） */
  concurrent: JobConcurrentEnum;
  /** 状态（0正常 1暂停） */
  status: JobStatusEnum;
}

/** job list model */
export type SysJobLM = SysJobIM[];

/** job param model */
export interface SysJobPM extends SubBaseEntity<SysJobLogIM> {
  /** Id */
  id?: string;
  /** 任务名称 */
  name?: string;
  /** 任务组名 */
  jobGroup?: JobGroupEnum;
  /** 归属服务 */
  serverType?: string;
  /** 请求类型 */
  httpType?: string;
  /** 计划执行错误策略（0默认 1立即执行 2执行一次 3放弃执行） */
  misfirePolicy?: JobMisfireEnum;
  /** 是否并发执行（0允许 1禁止） */
  concurrent?: JobConcurrentEnum;
  /** 状态（0正常 1暂停） */
  status?: JobStatusEnum;
}

/** job page param model */
export type SysJobPPM = BasicPageParams & SysJobPM;

/** job list result model */
export type SysJobLRM = BasicFetchResult<SysJobIM>;

/** 内部服务映射 数据结果模型 */
export interface SysJobServiceMappingIM {
  /**
   * 服务唯一标识
   * 微服务架构中服务的注册名称，用于服务发现和调用
   */
  serviceId: string;
  /**
   * 服务显示名称
   * 用于前端展示和日志输出的友好名称
   */
  serviceName: string;
  /**
   * 服务映射标识
   * 网关路由配置中的路径，用于聚合服务请求转发等场景
   */
  mappingId: string;
}
