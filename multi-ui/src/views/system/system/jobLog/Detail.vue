<template>
  <BasicDrawer
    v-bind="$attrs"
    :title="getTitle"
    @register="registerDrawer"
    width="70%"
    showFooter
    :showOkBtn="false"
  >
    <Description @register="register" class="mt-4" />
  </BasicDrawer>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { Description, useDescription } from '@/components/Description';
  import { detailSchema } from './data';
  import { DescItemSizeEnum } from '@/enums';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { getJobLogApi } from '@/api/system/system/jobLog.api';

  /** 标题初始化 */
  const getTitle = ref('调度日志详情');

  const [register, { setDescProps }] = useDescription({
    title: '调度日志详情',
    schema: detailSchema,
    column: DescItemSizeEnum.DEFAULT,
  });

  const [registerDrawer, { setDrawerProps }] = useDrawerInner(async (data) => {
    setDrawerProps({ loading: true, confirmLoading: false });
    const baseInfo = await getJobLogApi(data.record.id);
    setDescProps({ data: baseInfo });
    getTitle.value = '调度日志详情:' + baseInfo?.name;
    setDrawerProps({ loading: false });
  });
</script>
