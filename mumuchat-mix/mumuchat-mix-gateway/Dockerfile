# 定义基础镜像变量并设置默认值
ARG BASE_IMAGE=eclipse-temurin:17-jre
# 定义 SkyWalking agent 镜像变量并设置默认值
ARG SKYWALKING_AGENT_IMAGE=apache/skywalking-java-agent:9.5.0-java17

# 第一阶段：从 SkyWalking 官方镜像提取 agent（仅当启用时）
FROM ${SKYWALKING_AGENT_IMAGE} AS skywalking-agent

# 第二阶段：应用构建
FROM ${BASE_IMAGE}

# author
MAINTAINER xueyi

# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 从第一阶段复制 SkyWalking agent
COPY --from=skywalking-agent /skywalking/agent /skywalking/agent

# 指定路径
WORKDIR /home/xueyi
# 复制jar文件到路径
COPY ./target/xueyi-mix-gateway.jar /home/xueyi/xueyi-mix-gateway.jar

# 设置默认环境变量，可在容器启动时通过 -e 参数覆盖
ENV ENABLE_SKYWALKING=false

# 启动时根据环境变量决定是否启用 SkyWalking agent
ENTRYPOINT ["/bin/sh", "-c", "if [ \"$ENABLE_SKYWALKING\" = \"true\" ]; then java -javaagent:/skywalking/agent/skywalking-agent.jar -Djava.security.egd=file:/dev/./urandom -jar xueyi-mix-gateway.jar; else java -Djava.security.egd=file:/dev/./urandom -jar xueyi-mix-gateway.jar; fi"]
