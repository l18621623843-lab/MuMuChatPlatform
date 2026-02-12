import { BaseEntity, BasicFetchResult, BasicPageParams } from '@/model';
import { DicStatusEnum, DicYesNoEnum } from '@/enums';

/** strategy info model */
export interface StrategyIM extends BaseEntity {
  /** Id */
  id: string;
  /** 源策略组名称 */
  name: string;
  /** 主数据源Id */
  sourceId: string;
  /** 主数据源编码 */
  sourceSlave: string;
  /** 状态（0正常 1停用） */
  status: DicStatusEnum;
  /** 默认源策略组（Y是 N否） */
  isDefault: DicYesNoEnum;
  /** 策略组类型配置 */
  sourceTypeInfo?: any;
}

/** strategy list model */
export type StrategyLM = StrategyIM[];

/** strategy param model */
export interface StrategyPM extends BaseEntity {
  /** Id */
  id?: string;
  /** 源策略组名称 */
  name?: string;
  /** 主数据源Id */
  sourceId?: string;
  /** 主数据源编码 */
  sourceSlave?: string;
  /** 状态（0正常 1停用） */
  status?: DicStatusEnum;
  /** 默认源策略组（Y是 N否） */
  isDefault?: DicYesNoEnum;
}

/** strategy page param model */
export type StrategyPPM = BasicPageParams & StrategyPM;

/** strategy list result model */
export type StrategyLRM = BasicFetchResult<StrategyIM>;
