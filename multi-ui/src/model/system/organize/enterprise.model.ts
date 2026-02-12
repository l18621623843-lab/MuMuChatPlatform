import { BaseEntity, BasicFetchResult, BasicPageParams } from '@/model';
import { DicStatusEnum, DicYesNoEnum, TenantTypeEnum } from '@/enums';

/** enterprise item model */
export interface EnterpriseIM extends BaseEntity {
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
  isLessor: TenantTypeEnum;
  /** 状态（0正常 1停用） */
  status: DicStatusEnum;
  /** 企业自定义域名 */
  domainName: string;
  /** 默认企业（Y是 N否） */
  isDefault: DicYesNoEnum;
}

/** enterprise list model */
export type EnterpriseLM = EnterpriseIM[];

/** enterprise param model */
export interface EnterprisePM extends BaseEntity {
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
  isLessor?: TenantTypeEnum;
  /** 状态（0正常 1停用） */
  status?: DicStatusEnum;
  /** 默认企业（Y是 N否） */
  isDefault?: DicYesNoEnum;
}

/** enterprise page param model */
export type EnterprisePPM = BasicPageParams & EnterprisePM;

/** enterprise list result model */
export type EnterpriseLRM = BasicFetchResult<EnterpriseIM>;
