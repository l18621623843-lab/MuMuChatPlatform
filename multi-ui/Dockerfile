# 定义基础镜像变量并设置默认值
# 使用官方nginx Alpine镜像（轻量级且适合作为基础镜像）
ARG BASE_IMAGE=nginx:1.27.3-alpine

# 使用变量作为基础镜像
FROM ${BASE_IMAGE}

# author
MAINTAINER xueyi

# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 指定路径
WORKDIR /home/xueyi/projects/xueyi-ui

# 复制构建产物到标准路径
COPY ./dist .

# 复制 Nginx 配置文件
COPY ./nginx/conf/nginx.conf /etc/nginx/nginx.conf

# 暴露端口
EXPOSE 80

# 将/home/xueyi/projects/xueyi-ui/assets/index.js 和/home/xueyi/projects/xueyi-ui/_app.config.js中的"$vg_base_url"替换为环境变量中的VG_BASE_URL,$vg_sub_domain 替换成VG_SUB_DOMAIN，$vg_default_user替换成VG_DEFAULT_USER，$vg_default_password替换成VG_DEFAULT_PASSWORD 而后启动nginx
CMD sed -i "s|__vg_base_url|$VG_BASE_URL|g" /home/xueyi/projects/xueyi-ui/assets/entry/index-*.js /home/xueyi/projects/xueyi-ui/_app.config.js && \
    nginx -g 'daemon off;'
RUN echo "🎉 架 🎉 设 🎉 成 🎉 功 🎉"

# 可通过 "docker build -t 版本号 --build-arg BASE_IMAGE=自定义镜像 ." 自定义镜像打包镜像
