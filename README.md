# 室内装修全流程管理系统（Interior Design Management System）

一个专业的室内装修全流程管理平台，帮助设计师、装修公司和业主高效协作，完成从需求分析到竣工验收的全套装修流程。

## 📋 主要功能模块

### 1. **项目管理** 
- 项目创建与配置
- 进度跟踪与甘特图
- 预算管理与成本控制
- 时间线管理

### 2. **设计方案**
- 空间规划设计
- 效果图生成与管理
- 设计方案版本控制
- 设计稿标注与反馈

### 3. **材料管理**
- 材料库管��
- 供应商管理
- 采购计划与订单
- 库存追踪

### 4. **施工管理**
- 施工队伍管理
- 施工进度监控
- 工程日志记录
- 质量检查与验收

### 5. **成本财务**
- 预算编制
- 发票管理
- 支付追踪
- 成本分析报表

### 6. **沟通协作**
- 在线消息系统
- 工作通知提醒
- 文件共享
- 问题跟踪反馈

### 7. **数据分析**
- 项目数据统计
- 成本分析
- 进度分析
- 效率评估

## 🏗️ 技术架构

### 后端技术栈
- **框架**: Spring Boot 3.x
- **数据库**: PostgreSQL + Redis
- **消息队列**: RabbitMQ
- **文件存储**: MinIO / AWS S3
- **搜索引擎**: Elasticsearch
- **API文档**: Swagger/OpenAPI

### 前端技术栈
- **框架**: React 18 + TypeScript
- **UI库**: Ant Design / Material UI
- **图表**: ECharts / Chart.js
- **3D可视化**: Three.js / Babylon.js
- **实时通信**: WebSocket / Socket.io

### DevOps
- **容器化**: Docker + Docker Compose
- **CI/CD**: GitHub Actions
- **监控**: Prometheus + Grafana
- **日志**: ELK Stack

## 📦 项目结构

```
qlm/
├── backend/                    # 后端服务
│   ├── src/
│   │   ├── main/java/
│   │   │   └── com/qlm/
│   │   │       ├── controller/      # API 控制层
│   │   │       ├── service/         # 业务逻辑层
│   │   │       ├── repository/      # 数据访问层
│   │   │       ├── entity/          # 数据模型
│   │   │       ├── dto/             # 数据传输对象
│   │   │       └── config/          # 配置类
│   │   └── resources/
│   │       ├── application.yml      # 应用配置
│   │       └── db/migration/        # 数据库迁移脚本
│   └── pom.xml
│
├── frontend/                   # 前端应用
│   ├── src/
│   │   ├── components/         # React 组件
│   │   ├── pages/              # 页面模块
│   │   ├── services/           # API 服务
│   │   ├── store/              # 状态管理
│   │   ├── styles/             # 样式文件
│   │   └── App.tsx
│   └── package.json
│
├── mobile/                     # 移动端应用 (React Native/Flutter)
│   ├── src/
│   ├── pubspec.yaml / package.json
│   └── android/, ios/
│
├── docker-compose.yml          # 容器编排
├── .github/
│   └── workflows/              # CI/CD 工作流
├── docs/                       # 项目文档
├── tests/                      # 测试用例
└── scripts/                    # 部署脚本
```

## 🚀 快速开始

### 前置条件
- Java 17+
- Node.js 18+
- Docker & Docker Compose
- PostgreSQL 14+
- Redis 7+

### 本地开发

#### 1. 克隆项目
```bash
git clone https://github.com/qcheng557-hue/qlm.git
cd qlm
```

#### 2. 启动基础服务（Docker）
```bash
docker-compose up -d
```

#### 3. 启动后端服务
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

#### 4. 启动前端应用
```bash
cd frontend
npm install
npm start
```

#### 5. 访问应用
- 前端: http://localhost:3000
- 后端API: http://localhost:8080
- API文档: http://localhost:8080/swagger-ui.html

## 📱 核心业务流程

### 装修项目生命周期

```
1. 需求分析阶段
   ├─ 业主信息采集
   ├─ 空间勘测
   └─ 需求确认

2. 设计方案阶段
   ├─ 平面设计
   ├─ 效果图设计
   ├─ 方案评审
   └─ 方案确定

3. 招标采购阶段
   ├─ 材料清单确定
   ├─ 供应商招标
   ├─ 价格谈判
   └─ 订单下达

4. 施工阶段
   ├─ 施工队伍安排
   ├─ 日常进度跟踪
   ├─ 质量检查
   └─ 问题处理

5. 竣工验收阶段
   ├─ 最终质量检查
   ├─ 客户验收
   ├─ 竣工结算
   └─ 售后服务
```

## 💾 数据库设计

主要数据表：
- `projects` - 装修项目
- `users` - 用户账户
- `designs` - 设计方案
- `materials` - 材料信息
- `suppliers` - 供应商
- `tasks` - 施工任务
- `invoices` - 发票单据
- `payments` - 支付记录
- `inspections` - 质量检查
- `comments` - 协作评论

## 🔐 安全特性

- JWT 身份认证
- 角色权限管理 (RBAC)
- 数据加密存储
- API 速率限制
- SQL 注入防护
- CSRF 防护
- 审计日志记录

## 📊 API 端点示例

```
# 项目管理
GET    /api/v1/projects           # 获取项目列表
POST   /api/v1/projects           # 创建项目
GET    /api/v1/projects/{id}      # 获取项目详情
PUT    /api/v1/projects/{id}      # 更新项目
DELETE /api/v1/projects/{id}      # 删除项目

# 设计方案
GET    /api/v1/designs            # 获取设计方案
POST   /api/v1/designs            # 上传设计方案
GET    /api/v1/designs/{id}       # 获取方案详情

# 材料管理
GET    /api/v1/materials          # 获取材料列表
POST   /api/v1/materials          # 新增材料
GET    /api/v1/suppliers          # 获取供应商

# 施工管理
GET    /api/v1/tasks              # 获取施工任务
POST   /api/v1/tasks              # 创建任务
PUT    /api/v1/tasks/{id}         # 更新任务进度

# 财务管理
GET    /api/v1/invoices           # 获取发票列表
GET    /api/v1/payments           # 获取支付记录
POST   /api/v1/payments           # 记录支付
```

## 🔄 CI/CD 流程

- GitHub Actions 自动化测试
- 自动构建 Docker 镜像
- 自动部署到测试环境
- 代码质量检查 (SonarQube)
- 性能基准测试

## 📈 性能指标

- API 平均响应时间 < 200ms
- 并发支持 10,000+ 用户
- 数据库查询优化 (索引、缓存)
- CDN 加速静态资源
- 文件上传支持 1GB+ 大文件

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交变更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📝 许可证

MIT License - 详见 [LICENSE](LICENSE) 文件

## 📞 联系支持

- 问题反馈: [GitHub Issues](https://github.com/qcheng557-hue/qlm/issues)
- 讨论交流: [GitHub Discussions](https://github.com/qcheng557-hue/qlm/discussions)
- 邮箱: support@qlm.com

## 🎯 发展路线图

- [ ] v1.0 - 核心功能发布（2026年8月）
- [ ] v1.5 - 移动端上线（2026年10月）
- [ ] v2.0 - AI 智能推荐（2026年12月）
- [ ] v2.5 - 3D 虚拟展示（2027年2月）
- [ ] v3.0 - 行业 SaaS 平台（2027年6月）

---

**致力于让装修更简单、更透明、更高效！** 🏠✨
