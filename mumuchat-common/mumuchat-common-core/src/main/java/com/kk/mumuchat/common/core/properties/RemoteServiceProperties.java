package com.kk.mumuchat.common.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 远程服务配置属性类
 * 用于定义和管理各个微服务模块的服务ID名称
 * 支持通过配置文件进行自定义配置
 *
 * @author xueyi
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "xueyi.remote.service")
public class RemoteServiceProperties {

    /** 认证服务的serviceId */
    private String Auth = "xueyi-auth";

    /** 文件服务的serviceId */
    private String File = "xueyi-file";

    /** 系统服务的serviceId */
    private String System = "xueyi-system";

    /** 租管服务的serviceId */
    private String Tenant = "xueyi-tenant";

    /** 定时任务服务的serviceId */
    private String Job = "xueyi-job";

    /**
     * 远程服务映射列表
     * 用于配置各个微服务模块的服务ID、名称和映射路径
     */
    private List<RemoteService> mapping = new ArrayList<>();

    /**
     * 远程服务映射配置类
     * 用于定义单个远程服务的配置信息，包括服务ID、服务名称和服务映射路径
     */
    @Data
    public static class RemoteService {

        /**
         * 服务唯一标识
         * 微服务架构中服务的注册名称，用于服务发现和调用
         */
        private String serviceId;

        /**
         * 服务显示名称
         * 用于前端展示和日志输出的友好名称
         */
        private String serviceName;

        /**
         * 服务映射标识
         * 网关路由配置中的路径，用于聚合服务请求转发等场景
         */
        private String mappingId;
    }
}