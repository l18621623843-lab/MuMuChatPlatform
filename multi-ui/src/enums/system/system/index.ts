export * from './job.enum';

/** 字典编码：定时任务 */
export enum DicCodeJobEnum {
  // 定时任务|任务并发
  SYS_JOB_CONCURRENT = 'sys_job_concurrent',
  // 定时任务|任务分组
  SYS_JOB_GROUP = 'sys_job_group',
  // 定时任务|任务策略
  SYS_JOB_POLICY = 'sys_job_policy',
  // 定时任务|任务状态
  SYS_JOB_STATUS = 'sys_job_status',
}
