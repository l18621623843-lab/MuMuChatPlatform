import { BaseEntity, BasicFetchResult, BasicPageParams } from '@/model';
import { JobGroupEnum } from '@/enums/system/system';

/** log info model */
export interface SysJobLogIM extends BaseEntity {
  /** Id */
  id: string;
  /** 任务Id */
  jobId: string;
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
  /** 日志信息 */
  jobMessage: string;
  /** 执行状态（0正常 1失败） */
  status: string;
  /** 异常信息 */
  exceptionInfo: string;
}

/** log list model */
export type SysJobLogLM = SysJobLogIM[];

/** log param model */
export interface SysJobLogPM extends BaseEntity {
  /** Id */
  id?: string;
  /** 任务Id */
  jobId?: string;
  /** 任务组名 */
  jobGroup?: JobGroupEnum;
  /** 归属服务 */
  serverType?: string;
  /** 请求类型 */
  httpType?: string;
  /** 执行状态（0正常 1失败） */
  status?: string;
}

/** log page param model */
export type SysJobLogPPM = BasicPageParams & SysJobLogPM;

/** log list result model */
export type SysJobLogLRM = BasicFetchResult<SysJobLogIM>;
