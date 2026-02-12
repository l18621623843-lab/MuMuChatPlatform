import { FormSchema } from '@/components/Form';
import { BasicColumn } from '@/components/Table';
import { DescItem } from '@/components/Description';
import { dicDictList } from '@/api/sys/dict.api';
import { dictConversion } from '@/utils/xueyi';
import { SysJobIM } from '@/model/system/system';
import {
  DicCodeJobEnum,
  JobConcurrentEnum,
  JobGroupEnum,
  JobMisfireEnum,
  JobStatusEnum,
} from '@/enums/system/system';
import { DicCodeEnum } from '@/enums';
import { getJobInnerServiceMappingApi } from '@/api/system/system/job.api';
import { DictLM } from '@/model/sys';

/** 字典查询 */
export const dictMap = await dicDictList([
  DicCodeEnum.SYS_HTTP_TYPE,
  DicCodeJobEnum.SYS_JOB_CONCURRENT,
  DicCodeJobEnum.SYS_JOB_GROUP,
  DicCodeJobEnum.SYS_JOB_STATUS,
  DicCodeJobEnum.SYS_JOB_POLICY,
]);

/**
 * 获取系统内部服务映射选项
 * 用于任务调度中"归属服务"的选择项
 */
export const sysJobInnerTypeOptions: DictLM = await getJobInnerServiceMappingApi().then((res) => {
  return res.map((item) => {
    return {
      label: item.serviceName,
      value: item.serviceId,
    };
  });
});

/** 字典表 */
export const dict: any = {
  DicHttpTypeOptions: dictMap[DicCodeEnum.SYS_HTTP_TYPE],
  DicJobConcurrentOptions: dictMap[DicCodeJobEnum.SYS_JOB_CONCURRENT],
  DicJobGroupOptions: dictMap[DicCodeJobEnum.SYS_JOB_GROUP],
  DicJobPolicyOptions: dictMap[DicCodeJobEnum.SYS_JOB_POLICY],
  DicJobStatusOptions: dictMap[DicCodeJobEnum.SYS_JOB_STATUS],
};

/** 表格数据 */
export const columns: BasicColumn[] = [
  {
    title: '任务名称',
    dataIndex: 'name',
    width: 220,
  },
  {
    title: '任务类型',
    dataIndex: 'jobGroup',
    width: 120,
    customRender: ({ record }) => {
      const data = record as SysJobIM;
      return dictConversion(dict.DicJobGroupOptions, data.jobGroup);
    },
  },
  {
    title: '归属服务',
    dataIndex: 'serverType',
    width: 120,
    customRender: ({ record }) => {
      const data = record as SysJobIM;
      return dictConversion(sysJobInnerTypeOptions, data.serverType);
    },
  },
  {
    title: '请求类型',
    dataIndex: 'httpType',
    width: 120,
    customRender: ({ record }) => {
      const data = record as SysJobIM;
      return dictConversion(dict.DicHttpTypeOptions, data.httpType);
    },
  },
  {
    title: '请求地址',
    dataIndex: 'apiUrl',
    width: 220,
  },
  {
    title: '调用目标字符串',
    dataIndex: 'invokeTarget',
    width: 220,
  },
  {
    title: 'cron执行表达式',
    dataIndex: 'cronExpression',
    width: 220,
  },
  {
    title: '状态',
    dataIndex: 'status',
    width: 220,
    customRender: ({ record }) => {
      const data = record as SysJobIM;
      return dictConversion(dict.DicJobStatusOptions, data.status);
    },
  },
  {
    title: '计划执行错误策略',
    dataIndex: 'misfirePolicy',
    width: 220,
    customRender: ({ record }) => {
      const data = record as SysJobIM;
      return dictConversion(dict.DicJobPolicyOptions, data.misfirePolicy);
    },
  },
  {
    title: '是否并发执行',
    dataIndex: 'concurrent',
    width: 220,
    customRender: ({ record }) => {
      const data = record as SysJobIM;
      return dictConversion(dict.DicJobConcurrentOptions, data.concurrent);
    },
  },
];

/** 查询数据 */
export const searchFormSchema: FormSchema[] = [
  {
    label: '任务名称',
    field: 'name',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '任务类型',
    field: 'jobGroup',
    component: 'Select',
    componentProps: {
      options: dict.DicJobGroupOptions,
      showSearch: true,
      optionFilterProp: 'label',
    },
    colProps: { span: 6 },
  },
  {
    label: '状态',
    field: 'status',
    component: 'Select',
    componentProps: {
      options: dict.DicJobStatusOptions,
      showSearch: true,
      optionFilterProp: 'label',
    },
    colProps: { span: 6 },
  },
];

/** 表单数据 */
export const formSchema: FormSchema[] = [
  {
    label: '任务Id',
    field: 'id',
    component: 'Input',
    show: false,
    colProps: { span: 12 },
  },
  {
    label: '任务名称',
    field: 'name',
    component: 'Input',
    required: true,
    colProps: { span: 12 },
  },
  {
    label: '任务类型',
    field: 'jobGroup',
    component: 'Select',
    componentProps: {
      options: dict.DicJobGroupOptions,
      showSearch: true,
      optionFilterProp: 'label',
    },
    required: true,
    colProps: { span: 12 },
  },
  {
    label: '调用目标字符串',
    field: 'invokeTarget',
    component: 'Input',
    ifShow: ({ values }) => values?.jobGroup === JobGroupEnum.DEFAULT,
    required: ({ values }) => values?.jobGroup === JobGroupEnum.DEFAULT,
    colProps: { span: 12 },
  },
  {
    label: '归属服务',
    field: 'serverType',
    component: 'Select',
    componentProps: {
      options: sysJobInnerTypeOptions,
      showSearch: true,
      optionFilterProp: 'label',
    },
    ifShow: ({ values }) => values?.jobGroup === JobGroupEnum.INNER_SYSTEM,
    required: ({ values }) => values?.jobGroup === JobGroupEnum.INNER_SYSTEM,
    colProps: { span: 12 },
  },
  {
    label: '请求类型',
    field: 'httpType',
    component: 'Select',
    componentProps: {
      options: dict.DicHttpTypeOptions,
      showSearch: true,
      optionFilterProp: 'label',
    },
    ifShow: ({ values }) =>
      values?.jobGroup === JobGroupEnum.INNER_SYSTEM ||
      values?.jobGroup === JobGroupEnum.EXTERNAL_SYSTEM,
    required: ({ values }) =>
      values?.jobGroup === JobGroupEnum.INNER_SYSTEM ||
      values?.jobGroup === JobGroupEnum.EXTERNAL_SYSTEM,
    colProps: { span: 12 },
  },
  {
    label: '请求地址',
    field: 'apiUrl',
    component: 'Input',
    ifShow: ({ values }) =>
      values?.jobGroup === JobGroupEnum.INNER_SYSTEM ||
      values?.jobGroup === JobGroupEnum.EXTERNAL_SYSTEM,
    required: ({ values }) =>
      values?.jobGroup === JobGroupEnum.INNER_SYSTEM ||
      values?.jobGroup === JobGroupEnum.EXTERNAL_SYSTEM,
    colProps: { span: 12 },
  },
  {
    label: 'cron执行表达式',
    field: 'cronExpression',
    component: 'Input',
    required: true,
    colProps: { span: 12 },
  },
  {
    label: '计划执行错误策略',
    field: 'misfirePolicy',
    component: 'Select',
    defaultValue: JobMisfireEnum.DEFAULT,
    componentProps: {
      options: dict.DicJobPolicyOptions,
      showSearch: true,
      optionFilterProp: 'label',
    },
    required: true,
    colProps: { span: 12 },
  },
  {
    label: '是否并发执行',
    field: 'concurrent',
    component: 'RadioButtonGroup',
    defaultValue: JobConcurrentEnum.FORBID,
    componentProps: {
      options: dict.DicJobConcurrentOptions,
    },
    required: true,
    colProps: { span: 12 },
  },
  {
    label: '状态',
    field: 'status',
    component: 'RadioButtonGroup',
    defaultValue: JobStatusEnum.PAUSE,
    componentProps: {
      options: dict.DicJobStatusOptions,
    },
    required: true,
    colProps: { span: 12 },
  },
  {
    label: '备注',
    field: 'remark',
    component: 'InputTextArea',
    colProps: { span: 24 },
  },
];

/** 详情数据 */
export const detailSchema: DescItem[] = [
  {
    label: '任务名称',
    field: 'name',
    span: 8,
  },
  {
    label: '任务类型',
    field: 'jobGroup',
    render: (val) => {
      return dictConversion(dict.DicJobGroupOptions, val);
    },
    span: 8,
  },
  {
    label: '归属服务',
    field: 'serverType',
    render: (val) => {
      return dictConversion(sysJobInnerTypeOptions, val);
    },
    span: 8,
  },
  {
    label: '请求类型',
    field: 'httpType',
    render: (val) => {
      return dictConversion(dict.DicHttpTypeOptions, val);
    },
    span: 8,
  },
  {
    label: '请求地址',
    field: 'apiUrl',
    span: 8,
  },
  {
    label: '调用目标字符串',
    field: 'invokeTarget',
    span: 8,
  },
  {
    label: 'cron执行表达式',
    field: 'cronExpression',
    span: 8,
  },
  {
    label: '计划执行错误策略',
    field: 'misfirePolicy',
    render: (val) => {
      return dictConversion(dict.DicJobPolicyOptions, val);
    },
    span: 8,
  },
  {
    label: '是否并发执行',
    field: 'concurrent',
    render: (val) => {
      return dictConversion(dict.DicJobConcurrentOptions, val);
    },
    span: 8,
  },
  {
    label: '状态',
    field: 'status',
    render: (val) => {
      return dictConversion(dict.DicJobStatusOptions, val);
    },
    span: 8,
  },
  {
    label: '备注',
    field: 'remark',
    span: 8,
  },
];
