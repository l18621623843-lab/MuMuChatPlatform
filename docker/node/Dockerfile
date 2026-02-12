# 定义基础镜像变量并设置默认值
# 使用官方Node.js Alpine镜像（轻量级且适合作为基础镜像）
ARG BASE_IMAGE=node:22.13.1-alpine

# 使用变量作为基础镜像
FROM ${BASE_IMAGE}


# 环境变量配置（下游可覆盖） | 国外镜像源
#ENV PNPM_VERSION=latest \
#    NODE_ENV=production \
#    CI=true \
#    PNPM_CONFIG_REGISTRY=https://registry.npmjs.org/ \
#    NPM_CONFIG_REGISTRY=https://registry.npmjs.org/

# 环境变量配置（下游可覆盖） | 国内阿里镜像源
ENV PNPM_VERSION=latest \
    NODE_ENV=production \
    CI=true \
    PNPM_CONFIG_REGISTRY=https://registry.npmmirror.com/ \
    NPM_CONFIG_REGISTRY=https://registry.npmmirror.com/

# 安装系统依赖（按需取消注释）
# RUN apk add --no-cache git

# 配置全局npm registry后安装pnpm
RUN npm config set registry ${NPM_CONFIG_REGISTRY} && \
    npm install -g pnpm@${PNPM_VERSION} && \
    pnpm config set store-dir .pnpm-store && \
    pnpm config set registry ${PNPM_CONFIG_REGISTRY} && \
    pnpm -v && \
    npm cache clean --force

# 创建专用系统用户和组
RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup -h /app && \
    mkdir -p /app/.pnpm-store && \
    chown -R appuser:appgroup /app

# 设置工作目录并确保权限
WORKDIR /app
RUN chown appuser:appgroup /app

# 声明数据卷
VOLUME ["/app/.pnpm-store"]

# 切换到非root用户
USER appuser

# 验证命令
CMD ["pnpm", "-v"]

# 可通过 "docker build -t 版本号 --build-arg BASE_IMAGE=自定义镜像 ." 自定义镜像打包镜像