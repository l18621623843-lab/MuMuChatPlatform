import { BaseEntity, BasicFetchResult, BasicPageParams } from '@/model';
import { DicStatusEnum, DicYesNoEnum } from '@/enums';

/** tenant info model */
export interface TenantIM extends BaseEntity {
  /** Id */
  id: string;
  /** 策略Id */
  strategyId: string;
  /** 名称 */
  name: string;
  /** 系统名称 */
  systemName: string;
  /** 企业名称 */
  nick: string;
  /** 企业Logo */
  logo: string;
  /** 企业账号修改次数 */
  nameFrequency: number;
  /** 超管租户（Y是 N否） */
  isLessor: DicYesNoEnum;
  /** 状态（0正常 1停用） */
  status: DicStatusEnum;
  /** 企业自定义域名 */
  domainName: string;
  /** 默认企业（Y是 N否） */
  isDefault: DicYesNoEnum;
  /** 权限Ids */
  authIds: string[];
}

/** tenant list model */
export type TenantLM = TenantIM[];

/** tenant param model */
export interface TenantPM extends BaseEntity {
  /** Id */
  id?: string;
  /** 策略Id */
  strategyId?: string;
  /** 名称 */
  name?: string;
  /** 系统名称 */
  systemName?: string;
  /** 企业名称 */
  nick?: string;
  /** 超管租户（Y是 N否） */
  isLessor?: DicYesNoEnum;
  /** 状态（0正常 1停用） */
  status?: DicStatusEnum;
  /** 默认企业（Y是 N否） */
  isDefault?: DicYesNoEnum;
  /** 权限Ids */
  authIds?: string[];
}

/** tenant page param model */
export type TenantPPM = BasicPageParams & TenantPM;

/** tenant list result model */
export type TenantLRM = BasicFetchResult<TenantIM>;
