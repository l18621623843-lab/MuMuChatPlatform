<template>
  <CollapseContainer title="基础配置">
    <BasicForm @register="register" />
  </CollapseContainer>

  <CollapseContainer title="参数配置">
    <BasicForm @register="basicRegister" />
  </CollapseContainer>

  <CollapseContainer title="主表配置" v-show="!isMergeTpl(tplType)">
    <BasicForm @register="baseRegister" />
  </CollapseContainer>

  <CollapseContainer title="树表配置" v-show="isTreeTpl(tplType)">
    <BasicForm @register="treeRegister" />
  </CollapseContainer>

  <CollapseContainer title="接口配置" v-show="!isMergeTpl(tplType)">
    <BasicForm @register="apiRegister" />
  </CollapseContainer>

  <CollapseContainer title="其他配置" v-show="!isMergeTpl(tplType)">
    <BasicForm @register="otherRegister" />
  </CollapseContainer>
</template>

<script setup lang="ts">
  import { CollapseContainer } from '@/components/Container';
  import { BasicForm, useForm } from '@/components/Form';
  import { TemplateTypeEnum } from '@/enums/gen/generate';
  import { GenTableIM, OptionIM } from '@/model/gen/generate';
  import { reactive, ref } from 'vue';
  import {
    dict,
    generateApiSchema,
    generateBaseSchema,
    generateBasicSchema,
    generateFormSchema,
    generateOtherSchema,
    generateTreeSchema,
    genList,
    getOptions,
    isMergeTpl,
    isTreeTpl,
  } from './gen.detail.data';
  import { getMenuRouteListApi } from '@/api/system/authority/menu.api';
  import { MenuTypeEnum } from '@/enums/system/authority';
  import { sourceAssign } from '@/utils/xueyi';

  const emit = defineEmits(['submit']);
  defineExpose({ initialize, submit });

  const state = reactive<{
    info: Nullable<GenTableIM>;
  }>({
    info: null,
  });

  const tplType = ref<TemplateTypeEnum>(TemplateTypeEnum.BASE);

  const [register, { setFieldsValue, validate, updateSchema }] = useForm({
    labelWidth: 160,
    schemas: generateFormSchema,
    showActionButtonGroup: false,
  });

  const [basicRegister, { setFieldsValue: basicSetFieldsValue, validate: basicValidate }] = useForm(
    {
      labelWidth: 160,
      schemas: generateBasicSchema,
      showActionButtonGroup: false,
    },
  );

  const [
    baseRegister,
    { setFieldsValue: baseSetFieldsValue, validate: baseValidate, updateSchema: baseUpdateSchema },
  ] = useForm({
    labelWidth: 160,
    schemas: generateBaseSchema,
    showActionButtonGroup: false,
  });

  const [
    treeRegister,
    { setFieldsValue: treeSetFieldsValue, validate: treeValidate, updateSchema: treeUpdateSchema },
  ] = useForm({
    labelWidth: 160,
    schemas: generateTreeSchema,
    showActionButtonGroup: false,
  });

  const [apiRegister, { setFieldsValue: apiSetFieldsValue, validate: apiValidate }] = useForm({
    labelWidth: 160,
    schemas: generateApiSchema,
    showActionButtonGroup: false,
  });

  const [otherRegister, { setFieldsValue: otherSetFieldsValue, validate: otherValidate }] = useForm(
    {
      labelWidth: 160,
      schemas: generateOtherSchema,
      showActionButtonGroup: false,
    },
  );

  /** 数据初始化 */
  function initialize(info: GenTableIM) {
    state.info = info;
    tplType.value = state.info.tplCategory as TemplateTypeEnum;
    initBasic();
    const dataList = state.info.subList === undefined ? [] : getOptions(state.info.subList);
    const option = state.info?.options;
    initBase(option);
    initTree(dataList);
    basicSetFieldsValue({ ...option });
    treeSetFieldsValue({ ...option });
    baseSetFieldsValue({ ...option });
    apiSetFieldsValue({ ...option });
    otherSetFieldsValue({ ...option });
  }

  /** 基础配置初始化 */
  function initBasic() {
    setFieldsValue({ ...state.info });
    updateSchema({
      field: 'tplCategory',
      componentProps: () => {
        return {
          options: dict.DicTemplateTypeOption,
          onChange: (e: any) => {
            tplType.value = e;
          },
        };
      },
    });
  }

  /** 单表配置初始化 */
  async function initBase(options: OptionIM) {
    const parentMenuIdOptions =
      options?.menuInfo?.parentModuleId === undefined
        ? []
        : await getMenuRouteListApi({
            moduleId: options?.menuInfo?.parentModuleId,
            menuTypeLimit: MenuTypeEnum.DIR,
            defaultNode: true,
          });
    baseUpdateSchema([
      { field: 'menuInfo.parentMenuId', componentProps: { treeData: parentMenuIdOptions } },
    ]);
  }

  /** 树表配置初始化 */
  function initTree(subList: any[]) {
    treeUpdateSchema([
      { field: 'fieldInfo.treeCode', componentProps: { options: subList } },
      { field: 'fieldInfo.parentId', componentProps: { options: subList } },
      { field: 'fieldInfo.treeName', componentProps: { options: subList } },
      { field: 'fieldInfo.ancestors', componentProps: { options: subList } },
      { field: 'fieldInfo.level', componentProps: { options: subList } },
    ]);
  }

  /** 保存校验 */
  async function submit(info: GenTableIM) {
    try {
      info = sourceAssign(info, await validate());
      let options = {};
      options = sourceAssign(options, await basicValidate());
      if (isTreeTpl(tplType.value)) {
        options = sourceAssign(options, await treeValidate());
      }
      if (!isMergeTpl(tplType.value)) {
        options = sourceAssign(options, await baseValidate());
      }
      options = sourceAssign(options, await apiValidate());
      options = sourceAssign(options, await otherValidate());
      info = sourceAssign(info, { options: options });
      return info;
    } catch {
      emit('submit', genList[1].key);
    }
  }
</script>
