<template>
  <BasicDrawer
    v-bind="$attrs"
    :title="getTitle"
    @register="registerDrawer"
    width="50%"
    showFooter
    @ok="handleSubmit"
  >
    <CollapseContainer title="基础配置">
      <BasicForm @register="baseRegisterForm" />
    </CollapseContainer>
    <CollapseContainer title="策略组配置">
      <BasicForm @register="typeRegisterForm" />
    </CollapseContainer>
  </BasicDrawer>
</template>

<script setup lang="ts">
  import { computed, ref, unref } from 'vue';
  import { dict, formSchema } from './data';
  import { useMessage } from '@/hooks/web/useMessage';
  import {
    addStrategyApi,
    editStrategyApi,
    getStrategyApi,
  } from '@/api/tenant/source/strategy.api';
  import { BasicForm, useForm } from '@/components/Form';
  import { StrategyIM } from '@/model/tenant/source';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { fromPairs } from 'lodash-es';
  import { sourceCopy } from '@/utils/xueyi';
  import { listSourceApi } from '@/api/tenant/source/source.api';
  import { DicStatusEnum, DicYesNoEnum } from '@/enums';
  import { isEmpty } from '@/utils/core/ObjectUtil';
  import { CollapseContainer } from '@/components/Container';

  const emit = defineEmits(['success', 'register']);

  const { createMessage } = useMessage();
  const isUpdate = ref(true);

  const [
    baseRegisterForm,
    { resetFields: baseResetFields, setFieldsValue: baseSetFieldsValue, validate: baseValidate },
  ] = useForm({
    labelWidth: 120,
    schemas: formSchema,
    showActionButtonGroup: false,
  });

  const [
    typeRegisterForm,
    {
      resetFields: typeResetFields,
      setProps: typeSetProps,
      setFieldsValue: typeSetFieldsValue,
      validate: typeValidate,
    },
  ] = useForm({
    labelWidth: 120,
    showActionButtonGroup: false,
  });

  const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
    baseResetFields();
    typeResetFields();
    setDrawerProps({ loading: true, confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;

    const sourceTypeInfo = fromPairs(
      dict.DicSourceTypeOptions.map((item) => [item?.value, undefined]),
    );
    if (unref(isUpdate)) {
      const strategyInfo = await getStrategyApi(data.record.id);

      console.error(sourceTypeInfo);
      if (strategyInfo?.sourceTypeInfo) {
        sourceCopy(sourceTypeInfo, strategyInfo?.sourceTypeInfo);
      }
      baseSetFieldsValue({
        ...strategyInfo,
      });
    }
    let typeRefreshMap: Map<string, boolean> = new Map();
    for (let key in sourceTypeInfo) {
      if (isEmpty(sourceTypeInfo?.[key])) {
        typeRefreshMap.set(key, true);
      }
    }
    const typeFormSchema = dict.DicSourceTypeOptions.map((item) => {
      return {
        label: item.label,
        field: item.value,
        component: 'ApiSelect',
        componentProps: {
          api: listSourceApi,
          params: { status: DicStatusEnum.NORMAL },
          showSearch: true,
          optionFilterProp: 'label',
          resultField: 'items',
          labelField: 'name',
          valueField: 'slave',
        },
        dynamicDisabled: !typeRefreshMap.get(item.value),
        required: item?.additionalA === DicYesNoEnum.YES,
        colProps: { span: 12 },
      };
    });
    typeSetProps({
      schemas: typeFormSchema,
    });
    typeSetFieldsValue({
      ...sourceTypeInfo,
    });
    setDrawerProps({ loading: false });
  });

  /** 标题初始化 */
  const getTitle = computed(() => (!unref(isUpdate) ? '新增源策略组' : '编辑源策略组'));

  /** 提交按钮 */
  async function handleSubmit() {
    try {
      const baseValues: StrategyIM = await baseValidate();
      baseValues.sourceTypeInfo = await typeValidate();
      setDrawerProps({ confirmLoading: true });
      unref(isUpdate)
        ? await editStrategyApi(baseValues).then(() => {
            closeDrawer();
            createMessage.success('编辑源策略组成功！');
          })
        : await addStrategyApi(baseValues).then(() => {
            closeDrawer();
            createMessage.success('新增源策略组成功！');
          });
      emit('success');
    } finally {
      setDrawerProps({ confirmLoading: false });
    }
  }
</script>
