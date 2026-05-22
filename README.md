# 元圈 (MetaCircle) · 社交圈子平台

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Vue](https://img.shields.io/badge/Vue-3.x-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-7.x-DC382D?style=flat-square&logo=redis&logoColor=white)
![RocketMQ](https://img.shields.io/badge/RocketMQ-5.x-FF6B00?style=flat-square&logo=apacherocketmq&logoColor=white)

> 面向兴趣社群的社交平台，涵盖社区发帖互动、AI 智能助手、BPM 工作流、商城交易、会员体系、支付结算等全方位功能。后端基于 Spring Cloud 微服务架构，前端采用 Vue 3 + TypeScript 构建。

## 项目说明

本项目为兴趣圈子社交平台，采用微服务架构实现业务域分离。平台以"圈子"为核心，用户可在不同圈子中发帖、互动、交易，同时提供 AI 辅助创作、流程审批、会员成长等企业级能力。

## 技术架构

| 层级 | 技术选型 |
|------|---------|
| **后端框架** | Java 17 + Spring Boot 3.x + Spring Cloud |
| **服务治理** | Nacos（注册中心/配置中心） |
| **数据存储** | MySQL 8.0 + Redis 7 |
| **消息中间件** | RocketMQ 5.x |
| **安全认证** | Spring Security + OAuth2 + JWT |
| **ORM 框架** | MyBatis-Plus |
| **工作流引擎** | Flowable (BPM) |
| **AI 能力** | Spring AI + 大模型集成 |
| **前端** | Vue 3 + TypeScript + Element Plus |

## 模块说明

| 模块 | 职责 |
|------|------|
| `tuoke-server` | 主启动服务，聚合所有模块 |
| `tuoke-framework` | 公共框架层（拦截器、工具、注解） |
| `tuoke-dependencies` | Maven BOM 统一依赖管理 |
| `tuoke-module-system` | 系统管理（用户、权限、角色、菜单、字典） |
| `tuoke-module-infra` | 基础设施（文件存储、短信、邮件、WebSocket） |
| `tuoke-module-member` | 会员体系（等级、积分、成长值、权益） |
| `tuoke-module-community` | 社区核心（圈子、帖子、评论、点赞、收藏） |
| `tuoke-module-social` | 社交互动（关注、粉丝、动态 Feed 流） |
| `tuoke-module-mall` | 商城模块（商品、订单、购物车） |
| `tuoke-module-pay` | 支付模块（微信/支付宝、退款、结算） |
| `tuoke-module-bpm` | 流程审批（Flowable 工作流、表单设计器） |
| `tuoke-module-crm` | 客户管理（线索、商机、跟进记录） |
| `tuoke-module-erp` | 进销存（采购、销售、库存） |
| `tuoke-module-mp` | 公众号管理（菜单、自动回复、素材） |
| `tuoke-module-report` | 数据报表（图表、导出、大屏） |

## 项目结构

```
first-quanzi/
├── quanzi/                   # 后端 - Spring Cloud 微服务
│   ├── tuoke-server/        #   启动入口
│   ├── tuoke-framework/     #   公共框架
│   ├── tuoke-dependencies/  #   BOM 依赖
│   ├── tuoke-module-system/ #   系统管理
│   ├── tuoke-module-infra/  #   基础设施
│   ├── tuoke-module-member/ #   会员体系
│   ├── tuoke-module-community/ # 社区核心
│   ├── tuoke-module-social/ #   社交互动
│   ├── tuoke-module-mall/   #   商城
│   ├── tuoke-module-pay/    #   支付
│   └── ...                  #   更多模块
│
└── quanzi-ui/                # 前端 - Vue 3 + TypeScript
    ├── src/
    │   ├── api/             #   接口层（community/member/mall/ai 等）
    │   ├── components/      #   UI 组件
    │   ├── views/           #   页面视图
    │   └── router/          #   路由配置
    └── package.json
```

## 核心功能

- **圈子社区** — 创建/加入圈子，发帖、评论、点赞、收藏，Timeline Feed 流
- **AI 智能助手** — 聊天对话、AI 写作、知识库检索、思维导图生成、图片生成
- **会员体系** — 会员等级、积分获取与消耗、成长值、权益配置
- **商城交易** — 商品发布、购物车、订单流转、物流跟踪
- **支付结算** — 微信/支付宝支付、退款处理、账户结算
- **BPM 工作流** — Flowable 流程引擎，支持请假/报销等审批场景，表单设计器
- **CRM 客户管理** — 线索跟进、商机管理、客户转化漏斗
- **公众号管理** — 菜单配置、自动回复、素材管理、粉丝消息

## 本地运行

### 环境要求
- JDK 17+
- Maven 3.9+
- Node.js 18+
- MySQL 8.0
- Redis 7.x
- RocketMQ 5.x
- Nacos 2.x

### 后端

```bash
cd quanzi

# 初始化数据库（执行 SQL 脚本）
# 修改 application.yml 中的数据库连接配置

mvn clean install -DskipTests
cd tuoke-server
mvn spring-boot:run
```

### 前端

```bash
cd quanzi-ui
npm install
npm run dev
```

访问 http://localhost:5173
