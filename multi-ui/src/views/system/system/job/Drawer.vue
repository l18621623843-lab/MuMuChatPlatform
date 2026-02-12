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
  </BasicDrawer>
</template>

<script setup lang="ts">
  import { computed, ref, unref } from 'vue';
  import { formSchema } from './data';
  import { useMessage } from '@/hooks/web/useMessage';
  import { BasicForm, useForm } from '@/components/Form';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { addJobApi, editJobApi, getJobApi } from '@/api/system/system/job.api';
  import { SysJobIM } from '@/model/system/system';

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
      const info = await getJobApi(data.record.id);
      setFieldsValue({
        ...info,
      });
    }
    setDrawerProps({ loading: false });
  });

  /** 标题初始化 */
  const getTitle = computed(() => (!unref(isUpdate) ? '新增调度任务' : '编辑调度任务'));

  /** 提交按钮 */
  async function handleSubmit() {
    try {
      const values: SysJobIM = await validate();
      setDrawerProps({ confirmLoading: true });
      unref(isUpdate)
        ? await editJobApi(values).then(() => {
            closeDrawer();
            createMessage.success('编辑调度任务成功！');
          })
        : await addJobApi(values).then(() => {
            closeDrawer();
            createMessage.success('新增调度任务成功！');
          });
      emit('success');
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }
</script>
