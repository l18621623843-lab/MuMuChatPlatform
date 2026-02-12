<template>
  <BasicDrawer
    v-bind="$attrs"
    :title="getTitle"
    @register="registerDrawer"
    width="50%"
    showFooter
    :showOkBtn="false"
  >
    <Description @register="register" class="mt-4" />
  </BasicDrawer>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { getStrategyApi } from '@/api/tenant/source/strategy.api';
  import { Description, useDescription } from '@/components/Description';
  import { detailSchema } from './data';
  import { DescItemSizeEnum } from '@/enums';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';

  /** 标题初始化 */
  const getTitle = ref('源策略组详情');

  const [register, { setDescProps }] = useDescription({
    title: '源策略组详情',
    schema: detailSchema,
    column: DescItemSizeEnum.DEFAULT,
  });

  const [registerDrawer, { setDrawerProps }] = useDrawerInner(async (data) => {
    setDrawerProps({ loading: true, confirmLoading: false });
    const baseInfo = await getStrategyApi(data.record.id);
    setDescProps({ data: baseInfo });
    getTitle.value = '源策略组详情:' + baseInfo?.name;
    setDrawerProps({ loading: false });
  });
</script>
