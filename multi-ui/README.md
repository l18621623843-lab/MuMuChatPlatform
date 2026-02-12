## 安装使用

- 获取项目代码

```bash
git clone https://gitee.com/xueyitiantang/MultiSaas.git
```

- 安装依赖

```bash
cd multi-ui

pnpm install
```

- 运行

```bash
vite
```

- 打包

```bash
cross-env NODE_ENV=production vite build && esno ./build/script/postBuild.ts
```

## GitLab 流水线配置
代码中存在对应的流水线打包内容，如需使用，先需在GitLab中配置好对应的变量，然后使用流水线即可。
```bash
CI_IMAGE_REGISTRY（必须）（镜像仓库地址：如registry.cn-shanghai.aliyuncs.com）
CI_IMAGE_NAMESPACE（必须）（镜像仓库命名空间：如xueyi）
CI_DOCKER_USERNAME（必须）（镜像空间登录用户名）
CI_DOCKER_PASSWORD（必须）（镜像仓库登录密码）
CI_IMAGE_TAG（镜像版本，如未指定则默认 latest）
CI_DOCKER_HOST（docker远程地址，如未指定则默认 tcp://docker:2375）（Pass: 本人用的远程docker打包，具体可自己改造打包部分）
NODE_IMAGE（Node.js镜像，如未指定则默认 node:22.13.1-alpine）（Pass：此处建议基于项目中/docker/node下的Dockerfile构建带pnpm的镜像，然后使用该镜像进行打包）
CI_NGINX_IMAGE（Nginx镜像，如未指定则默认使用dockerFile中的nginx:xxxx-alpine镜像）
```
