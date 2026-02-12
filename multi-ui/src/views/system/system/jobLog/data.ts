import { FormSchema } from '@/components/Form';
import { BasicColumn } from '@/components/Table';
import { DescItem } from '@/components/Description';
import { dicDictList } from '@/api/sys/dict.api';
import { dictConversion } from '@/utils/xueyi';
import { SysJobIM, SysJobLogIM } from '@/model/system/system';
import { listJobApi } from '@/api/system/system/job.api';
import { DicCodeEnum } from '@/enums';
import { DicCodeJobEnum } from '@/enums/system/system';
import { sysJobInnerTypeOptions } from '../job/data';

/** 字典查询 */
export const dictMap = await dicDictList([
  DicCodeEnum.SYS_HTTP_TYPE,
  DicCodeEnum.SYS_MESSAGE_STATUS,
  DicCodeJobEnum.SYS_JOB_GROUP,
]);

/** 字典表 */
export const dict: any = {
  DicHttpTypeOptions: dictMap[DicCodeEnum.SYS_HTTP_TYPE],
  DicJobGroupOptions: dictMap[DicCodeJobEnum.SYS_JOB_GROUP],
  DicMessageStatusOptions: dictMap[DicCodeEnum.SYS_MESSAGE_STATUS],
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
    width: 220,
    customRender: ({ record }) => {
      const data = record as SysJobIM;
      return dictConversion(dict.DicJobGroupOptions, data.jobGroup);
    },
  },
  {
    title: '执行状态',
    dataIndex: 'status',
    width: 220,
    customRender: ({ record }) => {
      const data = record as SysJobLogIM;
      return dictConversion(dict.DicMessageStatusOptions, data.status);
    },
  },
  {
    title: '执行时间',
    dataIndex: 'createTime',
    width: 220,
  },
  {
    title: '调用目标字符串',
    dataIndex: 'invokeTarget',
    width: 220,
  },
  {
    title: '日志信息',
    dataIndex: 'jobMessage',
    width: 220,
  },
];

/** 查询数据 */
export const searchFormSchema: FormSchema[] = [
  {
    label: '任务名称',
    field: 'jobId',
    component: 'ApiSelect',
    componentProps: {
      api: listJobApi,
      showSearch: true,
      optionFilterProp: 'label',
      resultField: 'items',
      labelField: 'name',
      valueField: 'id',
    },
    colProps: { span: 6 },
  },
  {
    label: '任务组名',
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
    label: '执行状态',
    field: 'status',
    component: 'Select',
    componentProps: {
      options: dict.DicMessageStatusOptions,
      showSearch: true,
      optionFilterProp: 'label',
    },
    colProps: { span: 6 },
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
    label: '日志信息',
    field: 'jobMessage',
    span: 8,
  },
  {
    label: '执行状态',
    field: 'status',
    render: (val) => {
      return dictConversion(dict.DicMessageStatusOptions, val);
    },
    span: 8,
  },
  {
    label: '执行时间',
    field: 'createTime',
    span: 8,
  },
  {
    label: '异常信息',
    field: 'exceptionInfo',
    span: 8,
  },
];
