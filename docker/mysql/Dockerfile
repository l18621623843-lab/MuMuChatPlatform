# 基础镜像
FROM mysql:8.0

# author
MAINTAINER kevin

# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置docker默认编码db初始化数据乱码
ENV LANG C.UTF-8

# 执行sql脚本
ADD ./db/mumuchat_config.sql /docker-entrypoint-initdb.d/
ADD ./db/mumuchat_platform.sql /docker-entrypoint-initdb.d/

# 增加执行权限，解决启动mysql容器后，不自动执行sql脚本的问题
RUN chmod a+x /docker-entrypoint-initdb.d