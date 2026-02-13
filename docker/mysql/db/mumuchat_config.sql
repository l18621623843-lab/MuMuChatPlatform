DROP DATABASE IF EXISTS `mumuchat-config`;

CREATE DATABASE  `mumuchat-config` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `mumuchat-config`;


/******************************************/
/*   表名称 = config_info                  */
/******************************************/
CREATE TABLE `config_info` (
    `id`                 bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255)  NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128)           DEFAULT NULL COMMENT 'group_id',
    `content`            longtext      NOT NULL COMMENT 'content',
    `md5`                varchar(32)            DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text COMMENT 'source user',
    `src_ip`             varchar(50)            DEFAULT NULL COMMENT 'source ip',
    `app_name`           varchar(128)           DEFAULT NULL COMMENT 'app_name',
    `tenant_id`          varchar(128)           DEFAULT '' COMMENT '租户字段',
    `c_desc`             varchar(256)           DEFAULT NULL COMMENT 'configuration description',
    `c_use`              varchar(64)            DEFAULT NULL COMMENT 'configuration usage',
    `effect`             varchar(64)            DEFAULT NULL COMMENT '配置生效的描述',
    `type`               varchar(64)            DEFAULT NULL COMMENT '配置的类型',
    `c_schema`           text COMMENT '配置的模式',
    `encrypted_data_key` varchar(1024) NOT NULL DEFAULT '' COMMENT '密钥',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info';

/******************************************/
/*   表名称 = config_info  since 2.5.0                */
/******************************************/
CREATE TABLE `config_info_gray` (
    `id`                 bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255)    NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128)    NOT NULL COMMENT 'group_id',
    `content`            longtext        NOT NULL COMMENT 'content',
    `md5`                varchar(32)              DEFAULT NULL COMMENT 'md5',
    `src_user`           text COMMENT 'src_user',
    `src_ip`             varchar(100)             DEFAULT NULL COMMENT 'src_ip',
    `gmt_create`         datetime(3)     NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'gmt_create',
    `gmt_modified`       datetime(3)     NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'gmt_modified',
    `app_name`           varchar(128)             DEFAULT NULL COMMENT 'app_name',
    `tenant_id`          varchar(128)             DEFAULT '' COMMENT 'tenant_id',
    `gray_name`          varchar(128)    NOT NULL COMMENT 'gray_name',
    `gray_rule`          text            NOT NULL COMMENT 'gray_rule',
    `encrypted_data_key` varchar(256)    NOT NULL DEFAULT '' COMMENT 'encrypted_data_key',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfogray_datagrouptenantgray` (`data_id`, `group_id`, `tenant_id`, `gray_name`),
    KEY `idx_dataid_gmt_modified` (`data_id`, `gmt_modified`),
    KEY `idx_gmt_modified` (`gmt_modified`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='config_info_gray';

/******************************************/
/*   表名称 = config_tags_relation         */
/******************************************/
CREATE TABLE `config_tags_relation` (
    `id`        bigint(20)   NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64)  DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增长标识',
    PRIMARY KEY (`nid`),
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_tag_relation';

/******************************************/
/*   表名称 = group_capacity               */
/******************************************/
CREATE TABLE `group_capacity` (
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128)        NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='集群、各Group容量信息表';

/******************************************/
/*   表名称 = his_config_info              */
/******************************************/
CREATE TABLE `his_config_info` (
    `id`                 bigint(20) unsigned NOT NULL COMMENT 'id',
    `nid`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增标识',
    `data_id`            varchar(255)        NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128)        NOT NULL COMMENT 'group_id',
    `app_name`           varchar(128)                 DEFAULT NULL COMMENT 'app_name',
    `content`            longtext            NOT NULL COMMENT 'content',
    `md5`                varchar(32)                  DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text COMMENT 'source user',
    `src_ip`             varchar(50)                  DEFAULT NULL COMMENT 'source ip',
    `op_type`            char(10)                     DEFAULT NULL COMMENT 'operation type',
    `tenant_id`          varchar(128)                 DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` varchar(1024)       NOT NULL DEFAULT '' COMMENT '密钥',
    `publish_type`       varchar(50)                  DEFAULT 'formal' COMMENT 'publish type gray or formal',
    `gray_name`          varchar(50)                  DEFAULT NULL COMMENT 'gray name',
    `ext_info`           longtext                     DEFAULT NULL COMMENT 'ext info',
    PRIMARY KEY (`nid`),
    KEY `idx_gmt_create` (`gmt_create`),
    KEY `idx_gmt_modified` (`gmt_modified`),
    KEY `idx_did` (`data_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='多租户改造';


/******************************************/
/*   表名称 = tenant_capacity              */
/******************************************/
CREATE TABLE `tenant_capacity` (
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128)        NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='租户容量信息表';


CREATE TABLE `tenant_info` (
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) default '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) default '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32)  DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint(20)   NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint(20)   NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='tenant_info';

CREATE TABLE `users` (
    `username` varchar(50)  NOT NULL PRIMARY KEY COMMENT 'username',
    `password` varchar(500) NOT NULL COMMENT 'password',
    `enabled`  boolean      NOT NULL COMMENT 'enabled'
);

CREATE TABLE `roles` (
    `username` varchar(50) NOT NULL COMMENT 'username',
    `role`     varchar(50) NOT NULL COMMENT 'role',
    UNIQUE INDEX `idx_user_role` (`username` ASC, `role` ASC) USING BTREE
);

CREATE TABLE `permissions` (
    `role`     varchar(50)  NOT NULL COMMENT 'role',
    `resource` varchar(128) NOT NULL COMMENT 'resource',
    `action`   varchar(8)   NOT NULL COMMENT 'action',
    UNIQUE INDEX `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
);

INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (1, 'application-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # feign 配置\n  cloud:\n    openfeign:\n      okhttp:\n        enabled: true\n      httpclient:\n        enabled: false\n      client:\n        config:\n          default:\n            connectTimeout: 10000\n            readTimeout: 10000\n      compression:\n        request:\n          enabled: true\n        response:\n          enabled: true\nfeign:\n  sentinel:\n    enabled: true\n\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \' *\'\n\nxueyi:\n  # 安全配置\n  security:\n    # 密钥\n    secret:\n      # 网关地址\n      gatewayUrl: ${secret.security.secret.gatewayUrl}\n      # 令牌秘钥\n      token: ${secret.security.secret.token}\n      # 平台登录秘钥\n      platform: ${secret.security.secret.platform}', 'a4f7ff6e78c92c9174b874d37585895e', '2024-03-18 09:14:31', '2026-01-15 00:29:01', 'nacos', '172.18.0.1', '', '', '通用配置', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (2, 'application-secret-dev.yml', 'DEFAULT_GROUP', 'secret:\n  # redis参数信息\n  redis:\n    host: 127.0.0.1\n    port: 6379\n    password: 123456\n    database: 0\n  # 安全配置信息\n  security:\n    secret:\n      # 网关地址\n      gatewayUrl: http://localhost:8080\n      # 令牌秘钥\n      token: sptAaFDyDNKSWjtc0JTWyT5zCX6QGsRShk4b4Zb6SFe4WtJC0rbKcn2mftrWyrPbTmw0HtNAaKai09etfHcTTX3Zt9RKKArp9TfJY59tCEAm7BfBCMN7rKjNXTCydCsJnX0wtzcwZmPnk1i3hEA080t7\n      # 平台登录秘钥\n      platform: QCSx16hXabG2CdPCdeAA8HPmhz8jfH1T\n  # 服务状态监控参数信息\n  monitor:\n    name: xueyi\n    password: xueyi123\n    title: 服务状态监控\n  # swagger参数信息\n  swagger:\n    author:\n      name: xueyi\n      email: xueyitt@qq.com\n    version: v3.0.2\n    title: 接口文档\n    license: Powered By xueyi\n    licenseUrl: https://doc.xueyitt.cn\n  # datasource主库参数信息\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/mumuchat-cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&allowMultiQueries=true&serverTimezone=GMT%2B8\n    username: root\n    password: root\n  # nacos参数信息\n  nacos:\n    serverAddr: 127.0.0.1:8848\n    namespace:\n    username:\n    password:\n  # RabbitMQ 配置\n  rabbitmq:\n    # 开关\n    isOpen: false\n    # 默认地址\n    host: 127.0.0.1\n    # 默认端口\n    port: 5672\n    # 账号\n    username: guest\n    # 密码\n    password: guest\n    # 前缀标识\n    prefix: xueyitt\n    # 环境标识 最终生成/访问的MQ exchange|queue 格式为：前缀标识.环境标识.自定义exchange|queue名称 如：xueyitt.dev.xxxxx\n    evn: dev\n  # MQTT 配置\n  mqtt:\n    # 开关\n    isOpen: false\n    # 连接地址\n    hostUrl: tcp://127.0.0.1:1883\n    # 账号\n    username: admin\n    # 密码\n    password: password\n    # 前缀标识\n    prefix: xueyitt\n    # 环境标识\n    evn: dev\n\nxueyi:\n  # 远程服务映射配置\n  remote:\n    service:\n      # 认证服务\n      auth: xueyi-auth\n      # 文件服务\n      file: xueyi-file\n      # 系统服务\n      system: xueyi-system\n      # 租管服务\n      tenant: xueyi-tenant\n      # 定时任务服务\n      job: xueyi-job\n      # 代码生成服务\n      gen: xueyi-gen\n      # 监控服务\n      monitor: xueyi-monitor\n      # 服务映射（供融合服务别名转发or定时任务请求转发）\n      mapping:\n        - service-name: 认证服务\n          service-id: xueyi-auth\n          mapping-id: xueyi-auth\n        - service-name: 文件服务\n          service-id: xueyi-file\n          mapping-id: xueyi-file\n        - service-name: 系统服务\n          service-id: xueyi-system\n          mapping-id: xueyi-system\n        - service-name: 租管服务\n          service-id: xueyi-tenant\n          mapping-id: xueyi-tenant\n        - service-name: 定时任务服务\n          service-id: xueyi-job\n          mapping-id: xueyi-job\n        - service-name: 代码生成服务\n          service-id: xueyi-gen\n          mapping-id: xueyi-gen\n        - service-name: 监控服务\n          service-id: xueyi-monitor\n          mapping-id: xueyi-monitor\n', '04ae1bc464a7b6a02f3645246ab157c2', '2024-03-18 09:14:31', '2026-01-15 22:29:41', 'nacos', '172.18.0.1', '', '', '通用参数配置', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (3, 'application-datasource-dev.yml', 'DEFAULT_GROUP', '# spring配置\nspring:\n  data:\n    redis:\n      host: ${secret.redis.host}\n      port: ${secret.redis.port}\n      password: ${secret.redis.password}\n      database: ${secret.redis.database}\n  datasource:\n    dynamic:\n      hikari:\n        # 连接超时时间：毫秒\n        connection-timeout: 30000\n        # 连接测试活性最长时间：毫秒\n        validation-timeout: 5000\n        # 空闲连接最大存活时间\n        idle-timeout: 300000\n        # 连接最大存活时间\n        max-lifetime: 600000\n        # 最大连接数\n        max-pool-size: 20\n        # 最小空闲连接\n        min-idle: 10\n      datasource:\n          # 主库数据源\n          master:\n            driver-class-name: ${secret.datasource.driver-class-name}\n            url: ${secret.datasource.url}\n            username: ${secret.datasource.username}\n            password: ${secret.datasource.password}\n          # 数据源信息会通过master库进行获取并生成，请在主库的mumuchat_tenant_source中配置即可\n      # seata: true    # 开启seata代理，开启后默认每个数据源都代理，如果某个不需要代理可单独关闭\n\n# mybatis-plus配置\nmybatis-plus:\n  global-config:\n    # 是否控制台 print mybatis-plus 的 LOGO\n    banner: false\n    db-config:\n      # 字段验证策略之 select\n      selectStrategy: NOT_EMPTY\n      # 字段验证策略之 insert\n      insertStrategy: NOT_NULL\n      # 字段验证策略之 update\n      updateStrategy: ALWAYS\n      # 全局逻辑删除的实体字段名\n      logic-delete-field: delFlag\n      # 逻辑已删除值\n      logic-delete-value: 1\n      # 逻辑未删除值\n      logic-not-delete-value: 0\n  configuration:\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n\n# seata配置\nseata:\n  # 默认关闭，如需启用spring.datasource.dynami.seata需要同时开启\n  enabled: false\n  # Seata 应用编号，默认为 ${spring.application.name}\n  application-id: ${spring.application.name}\n  # Seata 事务组编号，用于 TC 集群名\n  tx-service-group: ${spring.application.name}-group\n  # 关闭自动代理\n  enable-auto-data-source-proxy: false\n  config:\n    type: nacos\n    nacos:\n      serverAddr: ${secret.nacos.serverAddr}\n      namespace: ${secret.nacos.namespace}\n      username: ${secret.nacos.username}\n      password: ${secret.nacos.password}\n      group: SEATA_GROUP\n  registry:\n    type: nacos\n    nacos:\n      application: seata-server\n      server-addr: ${secret.nacos.serverAddr}\n      namespace: ${secret.nacos.namespace}\n      username: ${secret.nacos.username}\n      password: ${secret.nacos.password}\n\n\n## springdoc 配置\nspringdoc:\n  info:\n    title: ${application.title}${secret.swagger.title}\n    license:\n      name: ${secret.swagger.license}\n      url: ${secret.swagger.licenseUrl}\n    contact:\n      name: ${secret.swagger.author.name}\n      email: ${secret.swagger.author.email}\n    version: ${secret.swagger.version}\n    description: ${application.title}${secret.swagger.title}\n    termsOfService: ${secret.swagger.licenseUrl}\n  api-docs:\n    path: /v3/api-docs\n    enabled: true\n  group-configs:\n    - group: \'default\'\n      paths-to-match: \'/**\'\n      packages-to-scan:\n        - com.kk.mumuchat\n      display-name: ${application.title}-${secret.swagger.version}\n', '5494f738e94a26eb4083bc4ccd46c09f', '2024-03-18 09:14:31', '2026-01-15 00:29:24', 'nacos', '172.18.0.1', '', '', '通用动态多数据源配置', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (4, 'application-mq-rabbit-dev.yml', 'DEFAULT_GROUP', 'xueyi:\n  # MQ配置\n  mq:\n    # Rabbit配置\n    rabbit:\n      # 开关\n      isOpen: ${secret.rabbitmq.isOpen}\n      # 前缀标识\n      prefix: ${secret.rabbitmq.prefix}\n      # 环境标识\n      evn: ${secret.rabbitmq.evn}\n\nspring:\n  # RabbitMQ 配置\n  rabbitmq:\n    # 地址  \n    host: ${secret.rabbitmq.host}\n    # 端口\n    port: ${secret.rabbitmq.port}\n    # 账号\n    username: ${secret.rabbitmq.username}\n    # 密码\n    password: ${secret.rabbitmq.password}\n    # 消息监听器配置\n    listener:\n      # 消息监听容器类型，默认 simple\n      type: simple\n      simple:\n        # 消息确认模式，none、manual和auto\n        acknowledge-mode: none\n        # 应用启动时是否启动容器，默认true\n        auto-startup: true\n        # listener最小消费者数\n        concurrency: 10\n        # listener最大消费者数\n        max-concurrency: 100\n        # 一个消费者最多可处理的nack消息数量\n        prefetch: 10\n        # 被拒绝的消息是否重新入队,默认true\n        default-requeue-rejected: true\n        # 如果容器声明的队列不可用，是否失败；或如果在运行时删除一个或多个队列，是否停止容器,默认true\n        missing-queues-fatal: true\n        # 空闲容器事件应多久发布一次\n        idle-event-interval: 10\n        # 重试配置\n        retry:\n          # 是否开启消费者重试，默认false\n          enabled: true\n          # 尝试发送消息的时间间隔，默认1000ms\n          initial-interval: 5000ms\n          # 最大重试次数,默认3\n          max-attempts: 3\n          # 最大重试间隔，默认10000ms\n          max-interval: 10000ms\n          # 应用于前一个重试间隔的乘数，间隔时间*乘子=下一次的间隔时间，不能超过max-interval\n          # 以initial-interval=5000ms,multiplier=2为例：第一次间隔 5 秒，第二次间隔 10 秒，以此类推\n          multiplier: 1\n          # 重试是无状态还是有状态,默认true\n          stateless: true', '7c37341fcdde9b2f24b663a4c59eda52', '2024-03-18 09:14:31', '2026-01-15 00:29:29', 'nacos', '172.18.0.1', '', '', 'rabbitMQ通用连接配置', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (5, 'application-mq-mqtt-dev.yml', 'DEFAULT_GROUP', 'xueyi:\n  # MQ配置\n  mq:\n    # mqtt 配置\n    mqtt:\n      # 开关\n      isOpen: ${secret.mqtt.isOpen}\n      # 连接地址\n      hostUrl: ${secret.mqtt.hostUrl}\n      # 账号\n      username: ${secret.mqtt.username}\n      # 密码\n      password: ${secret.mqtt.password}\n      # 前缀标识\n      prefix: ${secret.mqtt.prefix}\n      # 环境标识\n      evn: ${secret.mqtt.evn}\n      # 客户端Id，同一台服务器下，不允许出现重复的客户端Id\n      clientId: ${spring.application.name}-${secret.mqtt.prefix}-${secret.mqtt.evn}', 'f0ddfe3632fb45fc5a910989ee43f834', '2024-03-18 09:14:31', '2026-01-15 00:29:36', 'nacos', '172.18.0.1', '', '', 'MQTT通用连接配置', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (6, 'xueyi-gateway-dev.yml', 'DEFAULT_GROUP', '# spring配置\nspring:\n  data:\n    redis:\n      host: ${secret.redis.host}\n      port: ${secret.redis.port}\n      password: ${secret.redis.password}\n      database: ${secret.redis.database}\n  cloud:\n    gateway:\n      server:\n        webflux:\n          discovery:\n            locator:\n              lowerCaseServiceId: true\n              enabled: true\n          routes:\n            # 认证中心\n            - id: xueyi-auth\n              uri: lb://xueyi-auth\n              predicates:\n                - Path=/auth/**\n              filters:\n                # 验证码处理\n                - CacheRequestFilter\n                - ValidateCodeFilter\n                - StripPrefix=1\n            # 代码生成\n            - id: xueyi-gen\n              uri: lb://xueyi-gen\n              predicates:\n                - Path=/code/**\n              filters:\n                - StripPrefix=1\n            # 定时任务\n            - id: xueyi-job\n              uri: lb://xueyi-job\n              predicates:\n                - Path=/schedule/**\n              filters:\n                - StripPrefix=1\n            # 系统模块\n            - id: xueyi-system\n              uri: lb://xueyi-system\n              predicates:\n                - Path=/system/**\n              filters:\n                - StripPrefix=1\n            # 租户模块\n            - id: xueyi-tenant\n              uri: lb://xueyi-tenant\n              predicates:\n                - Path=/tenant/**\n              filters:\n                - StripPrefix=1\n            # 文件服务\n            - id: xueyi-file\n              uri: lb://xueyi-file\n              predicates:\n                - Path=/file/**\n              filters:\n                - StripPrefix=1\n\n# knife4j 网关聚合\nknife4j:\n  gateway:\n    enabled: true\n    # 指定服务发现的模式聚合微服务文档，并且是默认 default 分组\n    strategy: discover\n    discover:\n      # OpenAPI 3.0 规范 \n      version: openapi3\n      enabled: true\n      # 需要排除的微服务\n      excluded-services:\n        - xueyi-auth\n        - xueyi-monitor\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: false\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: false\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /code\n      - /system/admin/login/admin/getEnterpriseByDomainName\n      - /auth/oauth2/token\n      - /auth/oauth2/logout\n      - /auth/token/register\n      - /doc.html\n      - /webjars/**\n      - /v3/api-docs/*\n      - /*/v3/api-docs\n      - /csrf\n      - /actuator/*', 'ab32185170dfadd901309fe5737e999d', '2024-03-18 09:14:31', '2026-01-15 00:41:01', 'nacos', '172.18.0.1', '', '', '网关模块', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (7, 'xueyi-auth-dev.yml', 'DEFAULT_GROUP', '# spring配置\nspring:\n  data:\n    redis:\n      host: ${secret.redis.host}\n      port: ${secret.redis.port}\n      password: ${secret.redis.password}\n      database: ${secret.redis.database}\n', '74142af8af5c8621fbefb00c2f176ba7', '2024-03-18 09:14:31', '2026-01-15 00:29:50', 'nacos', '172.18.0.1', '', '', '认证中心', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (8, 'xueyi-monitor-dev.yml', 'DEFAULT_GROUP', '# spring配置\nspring:\n  security:\n    user:\n      name: ${secret.monitor.name}\n      password: ${secret.monitor.password}\n  boot:\n    admin:\n      ui:\n        title: ${secret.security.title}\n', '810edb98c7fbbe7c0a79d006e3ff0dab', '2024-03-18 09:14:31', '2026-01-15 00:30:43', 'nacos', '172.18.0.1', '', '', '监控中心', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (9, 'xueyi-tenant-dev.yml', 'DEFAULT_GROUP', 'xueyi:\n  # 租户配置\n  tenant:\n    # 公共表配置\n    common-table:\n      - sys_menu\n      - sys_module\n    # 非租户表配置\n    exclude-table:\n      - te_tenant\n      - te_strategy\n      - te_source\n\n# 租户库必需数据表\nsource-config:\n  slave-table:\n    - sys_dept\n    - sys_post\n    - sys_user\n    - sys_user_post_merge\n    - sys_role\n    - sys_role_module_merge\n    - sys_role_menu_merge\n    - sys_role_dept_merge\n    - sys_role_post_merge\n    - sys_organize_role_merge\n    - sys_operate_log\n    - sys_login_log\n    - sys_notice\n    - sys_notice_log\n    - sys_job_log\n    - sys_file\n    - sys_file_folder\n\n# mybatis-plus配置\nmybatis-plus:\n  # 搜索指定包别名\n  typeAliasesPackage: com.kk.mumuchat.tenant\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\n  mapperLocations: classpath:mapper/**/*.xml\n\n#seata配置\nseata:\n  # 服务配置项\n  service:\n    # 虚拟组和分组的映射\n    vgroup-mapping:\n      xueyi-tenant-group: default\n      \n## Liquibase 配置\nspring:\n  liquibase:\n    # 是否启用\n    enabled: true\n    # 配置文件路径\n    change-log: classpath:/db/changelog/db.changelog-master.yaml', 'eef0f5ec24fc72c4b4bcd087788d1710', '2024-03-18 09:14:31', '2026-02-12 19:52:55', 'nacos', '172.18.0.1', '', '', '租户模块', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (10, 'xueyi-system-dev.yml', 'DEFAULT_GROUP', 'xueyi:\n  # 租户配置\n  tenant:\n    # 公共表配置\n    common-table:\n      - sys_menu\n      - sys_module\n    # 非租户表配置\n    exclude-table:\n      - te_tenant\n      - te_strategy\n      - sys_auth_group\n      - sys_auth_group_menu_merge\n      - sys_auth_group_module_merge\n      - sys_tenant_auth_group_merge\n      - sys_oauth_client\n  # MQ配置\n  mq:\n    # Rabbit配置\n    rabbit:\n      # 交换机配置\n      exchangeInfos:\n          # 交换机类型（direct直连交换机 topic主题交换机 fanout扇出交换机 headers头交换机 x-delayed-message延时消息交换机）\n        - type: x-delayed-message\n          # 交换机名称\n          name: sys_system\n          # 是否持久化\n          durable: true\n          # 自定义参数\n          params: \n            x-delayed-type: direct\n          # 队列配置\n          queueInfos:\n              # 队列路由键名（当前仅用于示例，请自定义）\n            - routingKey: sys_dict_refresh\n              # 队列名\n              name: sys_dict_refresh\n              # 是否持久化\n              durable: true\n\n# mybatis-plus配置\nmybatis-plus:\n  # 搜索指定包别名\n  typeAliasesPackage: com.kk.mumuchat.system\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\n  mapperLocations: classpath:mapper/**/*.xml\n\n#seata配置\nseata:\n  # 服务配置项\n  service:\n    # 虚拟组和分组的映射\n    vgroup-mapping:\n      xueyi-system-group: default\n## Liquibase 配置\nspring:\n  liquibase:\n    # 是否启用\n    enabled: true\n    # 配置文件路径\n    change-log: classpath:/db/changelog/db.changelog-master.yaml', '33667be8b2a1575d62f6384dde623e84', '2024-03-18 09:14:31', '2026-02-12 19:41:28', 'nacos', '172.18.0.1', '', '', '系统模块', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (11, 'xueyi-gen-dev.yml', 'DEFAULT_GROUP', 'xueyi:\n  # 租户配置\n  tenant:\n    # 非租户表配置\n    exclude-table:\n      - gen_table\n      - gen_table_column\n\n# mybatis-plus配置\nmybatis-plus:\n  # 搜索指定包别名\n  typeAliasesPackage: com.kk.mumuchat.gen\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\n  mapperLocations: classpath:mapper/**/*.xml\n  configuration:\n    jdbc-type-for-null: \'null\'\n\n# 代码生成\ngen: \n  # 作者\n  author: xueyi\n  # ui路径（空代表生成在后端主路径下，可设置为ui项目地址如：C:UsersxueyiMultiSaas-UI）\n  uiPath: \n  # 自动去除表前缀，默认是true\n  autoRemovePre: true\n  # 数据库映射\n  data-base:\n    # 字符串类型\n    type-str: [\"char\", \"varchar\", \"nvarchar\", \"varchar2\"]\n    # 文本类型\n    type-text: [\"tinytext\", \"text\", \"mediumtext\", \"longtext\"]\n    # 日期类型\n    type-date: [\"datetime\", \"time\", \"date\", \"timestamp\"]\n    # 时间类型\n    type-time: [\"datetime\", \"time\", \"date\", \"timestamp\"]\n    # 数字类型\n    type-number: [\"tinyint\", \"smallint\", \"mediumint\", \"int\", \"number\", \"integer\"]\n    # 长数字类型\n    type-long: [\"bigint\"]\n    # 浮点类型\n    type-float: [\"float\", \"double\", \"decimal\"]\n  # 字段配置\n  operate:\n    # 隐藏详情显示\n    not-view: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"]\n    # 隐藏新增显示\n    not-insert: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"]\n    # 隐藏编辑显示\n    not-edit: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"]\n    # 隐藏列表显示\n    not-list: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\", \"remark\"]\n    # 隐藏查询显示\n    not-query: [\"id\", \"sort\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\", \"remark\"]\n    # 隐藏导入显示\n    not-import: [\"id\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"]\n    # 隐藏导出显示\n    not-export: [\"id\", \"sort\", \"createBy\", \"updateBy\"]\n  # 基类配置\n  entity:\n    # 必定隐藏字段（前后端均隐藏）\n    must-hide: [\"delFlag\", \"tenantId\", \"ancestors\"]\n    # 后端基类\n    back:\n      base: [\"id\", \"name\", \"status\", \"sort\", \"remark\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\", \"delFlag\"]\n      tree: [\"parentId\", \"ancestors\", \"level\"]\n      tenant: [\"tenantId\"]\n      common: [\"isCommon\"]\n    # 前端基类\n    front:\n      base: [\"sort\", \"remark\", \"createBy\", \"createName\", \"createTime\", \"updateBy\", \"updateName\", \"updateTime\", \"delFlag\"]\n      tree: [\"parentId\", \"ancestors\", \"level\"]\n      tenant: [\"tenantId\"]\n      common: [\"isCommon\"]\n  # 表前缀（与remove-lists对应）\n  dict-type-remove: [\"sys_\", \"te_\"]\n  # 表更替配置\n  remove-lists:\n      # 表前缀（生成类名不会包含表前缀）\n    - prefix: sys_\n      # 默认生成包路径 system 需改成自己的模块名称 如 system monitor tool\n      rdPackageName: com.kk.mumuchat.system\n      fePackageName: system\n      backPackageRoute: /xueyi-modules/xueyi-system\n    - prefix: te_\n      rdPackageName: com.kk.mumuchat.tenant\n      fePackageName: tenant\n      backPackageRoute: /xueyi-modules/xueyi-tenant\n', '75caaf92986a1be75a65817eea8c53d2', '2024-03-18 09:14:31', '2026-01-15 00:30:51', 'nacos', '172.18.0.1', '', '', '代码生成', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (12, 'xueyi-job-dev.yml', 'DEFAULT_GROUP', '# mybatis-plus配置\nmybatis-plus:\n  # 搜索指定包别名\n  typeAliasesPackage: com.kk.mumuchat.job\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\n  mapperLocations: classpath:mapper/**/*.xml\n\n# seata配置\nseata:\n  # 服务配置项\n  service:\n    # 虚拟组和分组的映射\n    vgroup-mapping:\n      xueyi-job-group: default\n', '6408f8cf3d402f7824e4d5fd3d88a653', '2024-03-18 09:14:31', '2026-01-15 00:30:57', 'nacos', '172.18.0.1', '', '', '定时任务', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (13, 'xueyi-file-dev.yml', 'DEFAULT_GROUP', '# spring配置\nspring:\n  data:\n    redis:\n      host: ${secret.redis.host}\n      port: ${secret.redis.port}\n      password: ${secret.redis.password}\n      database: ${secret.redis.database}\n\n# 本地文件上传\nfile:\n  domain: http://127.0.0.1:9300\n  path: D:/xueyi/uploadPath1\n  prefix: /statics\n\n# FastDFS配置\nfdfs:\n  domain: http://8.129.231.12\n  soTimeout: 3000\n  connectTimeout: 2000\n  trackerList: 8.129.231.12:22122\n\n# Minio配置\nminio:\n  url: http://8.129.231.12:9000\n  accessKey: minioadmin\n  secretKey: minioadmin\n  bucketName: test\n\nsecurity:\n  oauth2:\n    ignore:\n      whites:\n        routine:\n          - ${file.prefix}/**\n', 'd94de5c07df97bd1dc9e9cdc82fcf789', '2024-03-18 09:14:31', '2026-01-15 00:31:02', 'nacos', '172.18.0.1', '', '', '文件服务', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (14, 'sentinel-xueyi-gateway', 'DEFAULT_GROUP', '[\n    {\n        \"resource\": \"xueyi-auth\",\n        \"count\": 500,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"xueyi-system\",\n        \"count\": 1000,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"xueyi-tenant\",\n        \"count\": 500,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"xueyi-gen\",\n        \"count\": 200,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"xueyi-job\",\n        \"count\": 300,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    }\n]', 'f4a5d3ca1c2c47c86a85fcc2acf4ebce', '2024-03-18 09:14:31', '2026-01-15 00:31:08', 'nacos', '172.18.0.1', '', '', '限流策略', '', '', 'json', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (15, 'xueyi-mix-gateway-dev.yml', 'DEFAULT_GROUP', '# spring配置\nspring:\n  cloud:\n    gateway:\n      server:\n        webflux:\n          routes:\n            # 认证中心\n            - id: xueyi-auth\n              uri: lb://xueyi-mix-service\n              predicates:\n                - Path=/auth/**\n              filters:\n                # 验证码处理\n                - CacheRequestFilter\n                - ValidateCodeFilter\n                - StripPrefix=1\n            # 代码生成\n            - id: xueyi-gen\n              uri: lb://xueyi-mix-service\n              predicates:\n                - Path=/code/**\n              filters:\n                - StripPrefix=1\n            # 定时任务\n            - id: xueyi-job\n              uri: lb://xueyi-mix-service\n              predicates:\n                - Path=/schedule/**\n              filters:\n                - StripPrefix=1\n            # 系统模块\n            - id: xueyi-system\n              uri: lb://xueyi-mix-service\n              predicates:\n                - Path=/system/**\n              filters:\n                - StripPrefix=1\n            # 租户模块\n            - id: xueyi-tenant\n              uri: lb://xueyi-mix-service\n              predicates:\n                - Path=/tenant/**\n              filters:\n                - StripPrefix=1\n            # 文件服务\n            - id: xueyi-file\n              uri: lb://xueyi-mix-service\n              predicates:\n                - Path=/file/**\n              filters:\n                - StripPrefix=1\n', NULL, '2026-01-06 14:20:51', '2026-01-15 00:31:13', 'nacos', '172.18.0.1', '', '', '融合网关模块', '', '', 'yaml', '', '');
INSERT INTO `mumuchat-config`.`config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (16, 'xueyi-mix-service-dev.yml', 'DEFAULT_GROUP', 'xueyi:\n  # 远程服务映射配置\n  remote:\n    service:\n      # 认证服务\n      auth: xueyi-mix-service\n      # 文件服务\n      file: xueyi-mix-service\n      # 系统服务\n      system: xueyi-mix-service\n      # 租管服务\n      tenant: xueyi-mix-service\n      # 定时任务服务\n      job: xueyi-mix-service\n      # 代码生成服务\n      gen: xueyi-mix-service\n      # 监控服务\n      monitor: xueyi-mix-service\n      # 服务映射（供融合服务别名转发or定时任务请求转发）\n      mapping:\n        - service-name: 认证服务\n          service-id: xueyi-auth\n          mapping-id: xueyi-mix-service\n        - service-name: 文件服务\n          service-id: xueyi-file\n          mapping-id: xueyi-mix-service\n        - service-name: 系统服务\n          service-id: xueyi-system\n          mapping-id: xueyi-mix-service\n        - service-name: 租管服务\n          service-id: xueyi-tenant\n          mapping-id: xueyi-mix-service\n        - service-name: 定时任务服务\n          service-id: xueyi-job\n          mapping-id: xueyi-mix-service\n        - service-name: 代码生成服务\n          service-id: xueyi-gen\n          mapping-id: xueyi-mix-service\n        - service-name: 监控服务\n          service-id: xueyi-monitor\n          mapping-id: xueyi-mix-service\n\n  # 租户配置\n  tenant:\n    # 公共表配置\n    common-table:\n      # xueyi-system\n      - sys_menu\n      - sys_module\n    # 非租户表配置\n    exclude-table:\n      # xueyi-tenant\n      - te_tenant\n      - te_strategy\n      - te_source\n      # xueyi-system\n      - te_tenant\n      - te_strategy\n      - sys_auth_group\n      - sys_auth_group_menu_merge\n      - sys_auth_group_module_merge\n      - sys_tenant_auth_group_merge\n      - sys_oauth_client\n      # xueyi-gen\n      - gen_table\n      - gen_table_column\n\n  # MQ配置\n  mq:\n    # Rabbit配置\n    rabbit:\n      # 交换机配置\n      exchangeInfos:\n          # 交换机类型（direct直连交换机 topic主题交换机 fanout扇出交换机 headers头交换机 x-delayed-message延时消息交换机）\n        - type: x-delayed-message\n          # 交换机名称\n          name: sys_system\n          # 是否持久化\n          durable: true\n          # 自定义参数\n          params: \n            x-delayed-type: direct\n          # 队列配置\n          queueInfos:\n              # 队列路由键名（当前仅用于示例，请自定义）\n            - routingKey: sys_dict_refresh\n              # 队列名\n              name: sys_dict_refresh\n              # 是否持久化\n              durable: true\n\n# mybatis-plus配置\nmybatis-plus:\n  # 搜索指定包别名\n  typeAliasesPackage: com.kk.mumuchat.system,com.kk.mumuchat.tenant,com.kk.mumuchat.job,com.kk.mumuchat.gen\n  # 配置mapper的扫描，找到所有的mapper.xml映射文件\n  mapperLocations: classpath:mapper/**/*.xml\n  configuration:\n    # 设置NULL的默认JDBC类型为 NULL\n    jdbc-type-for-null: \'null\'\n\n#seata配置\nseata:\n  # 服务配置项\n  service:\n    # 虚拟组和分组的映射\n    vgroup-mapping:\n      xueyi-service-group: default\n\n# 文件服务自定义配置\n# 本地文件上传\nfile:\n  domain: http://localhost:12000\n  path: D:/xueyi/uploadPath\n  prefix: /statics', '3119353df7e429eec755766eb69b19ca', '2026-01-06 14:20:51', '2026-01-15 00:31:18', 'nacos', '172.18.0.1', '', '', '融合业务模块', '', '', 'yaml', '', '');



INSERT INTO users (username, password, enabled) VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', TRUE);

INSERT INTO roles (username, role) VALUES ('nacos', 'ROLE_ADMIN');