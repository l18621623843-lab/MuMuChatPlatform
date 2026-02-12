<template>
  <BasicDrawer
    v-bind="$attrs"
    :title="getTitle"
    @register="registerDrawer"
    width="50%"
    showFooter
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
    <template #insertFooter>
      <a-button type="primary" danger @click="connection">连接测试</a-button>
    </template>
  </BasicDrawer>
</template>

<script setup lang="ts">
  import { computed, ref, unref } from 'vue';
  import { formSchema } from './data';
  import { useMessage } from '@/hooks/web/useMessage';
  import {
    addSourceApi,
    connectionSourceApi,
    editSourceApi,
    getSourceApi,
  } from '@/api/tenant/source/source.api';
  import { BasicForm, useForm } from '@/components/Form';
  import { SourceIM } from '@/model/tenant/source';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';

  const emit = defineEmits(['success', 'register']);

  const { createMessage } = useMessage();
  const isUpdate = ref(true);

  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    labelWidth: 120,
    schemas: formSchema,
    showActionButtonGroup: false,
  });

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    resetFields();
    setDrawerProps({ loading: true, confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;

    if (unref(isUpdate)) {
      const sourceInfo = await getSourceApi(data.record.id);
      setFieldsValue({
        ...sourceInfo,
      });
    }
    setDrawerProps({ loading: false });
  });

  /** 标题初始化 */
  const getTitle = computed(() => (!unref(isUpdate) ? '新增数据源' : '编辑数据源'));

  /** 提交按钮 */
  async function handleSubmit() {
    try {
      const values: SourceIM = await validate();
      setDrawerProps({ confirmLoading: true });
      unref(isUpdate)
        ? await editSourceApi(values).then(() => {
            closeDrawer();
            createMessage.success('编辑数据源成功！');
          })
        : await addSourceApi(values).then(() => {
            closeDrawer();
            createMessage.success('新增数据源成功！');
          });
      emit('success');
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }

  /** 连接测试按钮 */
  async function connection() {
    const values: SourceIM = await validate();
    await connectionSourceApi(values).then(() => createMessage.success('数据源连接成功！'));
  }
</script>
