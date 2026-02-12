-- liquibase formatted sql

-- changeset Kevin:1
-- comment 初始化表数据
-- 1、存储每一个已配置的 jobDetail 的详细信息
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
CREATE TABLE QRTZ_JOB_DETAILS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    JOB_NAME             VARCHAR(200)    NOT NULL            COMMENT '任务名称',
    JOB_GROUP            VARCHAR(200)    NOT NULL            COMMENT '任务组名',
    DESCRIPTION          VARCHAR(250)    NULL                COMMENT '相关介绍',
    JOB_CLASS_NAME       VARCHAR(250)    NOT NULL            COMMENT '执行任务类名称',
    IS_DURABLE           VARCHAR(1)      NOT NULL            COMMENT '是否持久化',
    IS_NONCONCURRENT     VARCHAR(1)      NOT NULL            COMMENT '是否并发',
    IS_UPDATE_DATA       VARCHAR(1)      NOT NULL            COMMENT '是否更新数据',
    REQUESTS_RECOVERY    VARCHAR(1)      NOT NULL            COMMENT '是否接受恢复执行',
    JOB_DATA             BLOB            NULL                COMMENT '存放持久化job对象',
    PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
) ENGINE=INNODB COMMENT = '任务详细信息表';

-- ----------------------------
-- 2、 存储已配置的 Trigger 的信息
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
CREATE TABLE QRTZ_TRIGGERS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT '触发器的名字',
    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT '触发器所属组的名字',
    JOB_NAME             VARCHAR(200)    NOT NULL            COMMENT 'qrtz_job_details表job_name的外键',
    JOB_GROUP            VARCHAR(200)    NOT NULL            COMMENT 'qrtz_job_details表job_group的外键',
    DESCRIPTION          VARCHAR(250)    NULL                COMMENT '相关介绍',
    NEXT_FIRE_TIME       BIGINT(13)      NULL                COMMENT '下一次触发时间（毫秒）',
    PREV_FIRE_TIME       BIGINT(13)      NULL                COMMENT '上一次触发时间（默认为-1表示不触发）',
    PRIORITY             INTEGER         NULL                COMMENT '优先级',
    TRIGGER_STATE        VARCHAR(16)     NOT NULL            COMMENT '触发器状态',
    TRIGGER_TYPE         VARCHAR(8)      NOT NULL            COMMENT '触发器的类型',
    START_TIME           BIGINT(13)      NOT NULL            COMMENT '开始时间',
    END_TIME             BIGINT(13)      NULL                COMMENT '结束时间',
    CALENDAR_NAME        VARCHAR(200)    NULL                COMMENT '日程表名称',
    MISFIRE_INSTR        SMALLINT(2)     NULL                COMMENT '补偿执行的策略',
    JOB_DATA             BLOB            NULL                COMMENT '存放持久化job对象',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP) REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME, JOB_NAME, JOB_GROUP)
) ENGINE=INNODB COMMENT = '触发器详细信息表';

-- ----------------------------
-- 3、 存储简单的 Trigger，包括重复次数，间隔，以及已触发的次数
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_name的外键',
    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_group的外键',
    REPEAT_COUNT         BIGINT(7)       NOT NULL            COMMENT '重复的次数统计',
    REPEAT_INTERVAL      BIGINT(12)      NOT NULL            COMMENT '重复的间隔时间',
    TIMES_TRIGGERED      BIGINT(10)      NOT NULL            COMMENT '已经触发的次数',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = '简单触发器的信息表';

-- ----------------------------
-- 4、 存储 Cron Trigger，包括 Cron 表达式和时区信息
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
CREATE TABLE QRTZ_CRON_TRIGGERS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_name的外键',
    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_group的外键',
    CRON_EXPRESSION      VARCHAR(200)    NOT NULL            COMMENT 'cron表达式',
    TIME_ZONE_ID         VARCHAR(80)                         COMMENT '时区',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = 'Cron类型的触发器表';

-- ----------------------------
-- 5、 Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
CREATE TABLE QRTZ_BLOB_TRIGGERS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_name的外键',
    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_group的外键',
    BLOB_DATA            BLOB            NULL                COMMENT '存放持久化Trigger对象',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = 'Blob类型的触发器表';

-- ----------------------------
-- 6、 以 Blob 类型存储存放日历信息， quartz可配置一个日历来指定一个时间范围
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_CALENDARS;
CREATE TABLE QRTZ_CALENDARS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    CALENDAR_NAME        VARCHAR(200)    NOT NULL            COMMENT '日历名称',
    CALENDAR             BLOB            NOT NULL            COMMENT '存放持久化calendar对象',
    PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
) ENGINE=INNODB COMMENT = '日历信息表';

-- ----------------------------
-- 7、 存储已暂停的 Trigger 组的信息
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_group的外键',
    PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = '暂停的触发器表';

-- ----------------------------
-- 8、 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
CREATE TABLE QRTZ_FIRED_TRIGGERS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    ENTRY_ID             VARCHAR(95)     NOT NULL            COMMENT '调度器实例id',
    TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_name的外键',
    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_group的外键',
    INSTANCE_NAME        VARCHAR(200)    NOT NULL            COMMENT '调度器实例名',
    FIRED_TIME           BIGINT(13)      NOT NULL            COMMENT '触发的时间',
    SCHED_TIME           BIGINT(13)      NOT NULL            COMMENT '定时器制定的时间',
    PRIORITY             INTEGER         NOT NULL            COMMENT '优先级',
    STATE                VARCHAR(16)     NOT NULL            COMMENT '状态',
    JOB_NAME             VARCHAR(200)    NULL                COMMENT '任务名称',
    JOB_GROUP            VARCHAR(200)    NULL                COMMENT '任务组名',
    IS_NONCONCURRENT     VARCHAR(1)      NULL                COMMENT '是否并发',
    REQUESTS_RECOVERY    VARCHAR(1)      NULL                COMMENT '是否接受恢复执行',
    PRIMARY KEY (SCHED_NAME, ENTRY_ID)
) ENGINE=INNODB COMMENT = '已触发的触发器表';

-- ----------------------------
-- 9、 存储少量的有关 Scheduler 的状态信息，假如是用于集群中，可以看到其他的 Scheduler 实例
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
CREATE TABLE QRTZ_SCHEDULER_STATE (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    INSTANCE_NAME        VARCHAR(200)    NOT NULL            COMMENT '实例名称',
    LAST_CHECKIN_TIME    BIGINT(13)      NOT NULL            COMMENT '上次检查时间',
    CHECKIN_INTERVAL     BIGINT(13)      NOT NULL            COMMENT '检查间隔时间',
    PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
) ENGINE=INNODB COMMENT = '调度器状态表';

-- ----------------------------
-- 10、 存储程序的悲观锁的信息(假如使用了悲观锁)
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_LOCKS;
CREATE TABLE QRTZ_LOCKS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    LOCK_NAME            VARCHAR(40)     NOT NULL            COMMENT '悲观锁名称',
    PRIMARY KEY (SCHED_NAME, LOCK_NAME)
) ENGINE=INNODB COMMENT = '存储的悲观锁信息表';

-- ----------------------------
-- 11、 Quartz集群实现同步机制的行锁表
-- ----------------------------
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
CREATE TABLE QRTZ_SIMPROP_TRIGGERS (
    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
    TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_name的外键',
    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggers表trigger_group的外键',
    STR_PROP_1           VARCHAR(512)    NULL                COMMENT 'String类型的trigger的第一个参数',
    STR_PROP_2           VARCHAR(512)    NULL                COMMENT 'String类型的trigger的第二个参数',
    STR_PROP_3           VARCHAR(512)    NULL                COMMENT 'String类型的trigger的第三个参数',
    INT_PROP_1           INT             NULL                COMMENT 'int类型的trigger的第一个参数',
    INT_PROP_2           INT             NULL                COMMENT 'int类型的trigger的第二个参数',
    LONG_PROP_1          BIGINT          NULL                COMMENT 'long类型的trigger的第一个参数',
    LONG_PROP_2          BIGINT          NULL                COMMENT 'long类型的trigger的第二个参数',
    DEC_PROP_1           NUMERIC(13,4)   NULL                COMMENT 'decimal类型的trigger的第一个参数',
    DEC_PROP_2           NUMERIC(13,4)   NULL                COMMENT 'decimal类型的trigger的第二个参数',
    BOOL_PROP_1          VARCHAR(1)      NULL                COMMENT 'Boolean类型的trigger的第一个参数',
    BOOL_PROP_2          VARCHAR(1)      NULL                COMMENT 'Boolean类型的trigger的第二个参数',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = '同步机制的行锁表';

COMMIT;
