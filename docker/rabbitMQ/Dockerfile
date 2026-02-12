# 使用官方RabbitMQ镜像作为基础镜像  
FROM registry.cn-shanghai.aliyuncs.com/multisaas/rabbitmq:3.13.6-management

# author
MAINTAINER xueyi

# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 从本地文件夹复制延迟消息插件到镜像中的/plugins目录  
COPY ./plugins/rabbitmq_delayed_message_exchange-3.13.0.ez /plugins/rabbitmq_delayed_message_exchange-3.13.0.ez

# 启用插件  
RUN rabbitmq-plugins enable rabbitmq_delayed_message_exchange