# 贡献指南

感谢您对室内装修全流程管理系统的贡献！

## 开始前

### 了解项目
- 阅读 [README.md](../README.md) 了解项目概况
- 阅读 [DEVELOPMENT.md](../docs/DEVELOPMENT.md) 了解开发指南
- 查看 [API.md](../docs/API.md) 了解 API 设计

### 环境要求
- Java 17+
- Node.js 18+
- Docker & Docker Compose
- Git

## 贡献工作流

### 1. Fork 项目
点击 GitHub 页面右上角的 "Fork" 按钮。

### 2. 克隆您的 Fork
```bash
git clone https://github.com/your-username/qlm.git
cd qlm
```

### 3. 创建功能分支
```bash
# 更新 main 分支
git checkout main
git pull upstream main

# 创建新分支
git checkout -b feature/your-feature-name
```

### 4. 编写代码
遵循项目的代码规范和最佳实践。

### 5. 测试您的更改
```bash
# 后端测试
cd backend
mvn clean test

# 前端测试
cd frontend
npm test
```

### 6. 提交代码
遵循 Conventional Commits 规范：
```bash
git commit -m "feat(module): add awesome feature

This is the detailed description of the change.

Closes #123"
```

### 7. 推送到您的 Fork
```bash
git push origin feature/your-feature-name
```

### 8. 创建 Pull Request
在 GitHub 上创建 PR，使用提供的模板，清晰描述：
- 做了什么改变
- 为什么做这个改变
- 如何测试这个改变
- 关联的 Issue

## 代码规范

### Java 代码规范
- **命名约定**
  - 类名: PascalCase
  - 方法/变量: camelCase
  - 常量: UPPER_SNAKE_CASE
  
- **代码格式**
  - 4 个空格缩进
  - 最大行长 120 字符
  - 使用 Lombok 减少样板代码

- **最佳实践**
  - 优先使用 Optional 而不是 null 检查
  - 使用 Lombok 的 @Data, @Slf4j 等注解
  - 添加 JavaDoc 注释

**例子:**
```java
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    
    /**
     * 根据用户 ID 获取用户信息
     * @param userId 用户 ID
     * @return 用户信息
     */
    public Optional<UserDTO> getUserById(Long userId) {
        log.info("Fetching user with id: {}", userId);
        return userRepository.findById(userId)
            .map(this::convertToDTO);
    }
}
```

### TypeScript/React 代码规范
- **命名约定**
  - 组件: PascalCase
  - 文件名: PascalCase (组件), camelCase (其他)
  - 变量/函数: camelCase
  - 常量: UPPER_SNAKE_CASE

- **代码格式**
  - 2 个空格缩进
  - 使用分号结尾
  - 使用单引号（除非必须用双引号）

- **最佳实践**
  - 使用 TypeScript 类型注解
  - 将逻辑提取到自定义 Hooks
  - 使用 Zustand 管理全局状态
  - 遵循单一职责原则

**例子:**
```typescript
import React, { useState, useCallback } from 'react';

interface UserCardProps {
  userId: number;
  onDelete?: (id: number) => void;
}

export const UserCard: React.FC<UserCardProps> = ({ userId, onDelete }) => {
  const [loading, setLoading] = useState(false);

  const handleDelete = useCallback(async () => {
    setLoading(true);
    try {
      await deleteUser(userId);
      onDelete?.(userId);
    } finally {
      setLoading(false);
    }
  }, [userId, onDelete]);

  return (
    <div className="user-card">
      <button onClick={handleDelete} disabled={loading}>
        {loading ? 'Deleting...' : 'Delete'}
      </button>
    </div>
  );
};
```

## 提交消息规范

遵循 [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/):

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Type
- `feat`: 新功能
- `fix`: Bug 修复
- `docs`: 文档更新
- `style`: 代码格式调整（不影响功能）
- `refactor`: 代码重构
- `perf`: 性能优化
- `test`: 添加/修改测试
- `chore`: 构建、依赖管理等

### Scope
功能作用域，例如：
- `auth`: 认证模块
- `projects`: 项目管理
- `ui`: UI 组件

### Subject
- 使用命令式语气("add" 而不是 "added")
- 首字母小写
- 不以句号结尾
- 长度不超过 50 字符

### Body
- 详细说明做了什么以及为什么
- 每行不超过 72 字符
- 说明代码变更的动机

### Footer
- 关联相关的 Issue: `Closes #123`
- 提及任何 Breaking Changes

### 例子
```
feat(projects): add project creation wizard

Implement a multi-step wizard for creating new decoration projects:
- Step 1: Basic information (name, location, budget)
- Step 2: Project timeline
- Step 3: Team assignment

This improves user experience by guiding users through the process.

Closes #456
```

## 测试要求

### 后端测试
- 单元测试覆盖率 ≥ 70%
- 集成测试覆盖关键业务流程
- 模拟外部依赖（数据库、API 等）

```bash
cd backend
mvn clean test
mvn jacoco:report  # 生成覆盖率报告
```

### 前端测试
- React 组件测试
- 集成测试
- 目标覆盖率 ≥ 60%

```bash
cd frontend
npm test
npm run test:coverage
```

## 文档更新
- 如果添加了新功能，请更新相关文档
- 如果改变了 API，请更新 API 文档
- 添加必要的代码注释和 JavaDoc

## 代码审查

### 审查期望
- PR 应由至少一个核心贡献者审查和批准
- 所有检查（CI/CD、代码质量等）必须通过
- 讨论和修改应该是建设性的

### 审查要点
- 代码是否遵循项目规范
- 是否有测试覆盖
- 文档是否完整
- 是否引入安全问题

## 报告 Bug

如果您发现 Bug，请：

1. **检查现有 Issues**：确保 Bug 还未被报告
2. **创建新 Issue**：使用 Bug 报告模板
3. **提供详细信息**：
   - 清晰的标题
   - 详细的描述
   - 复现步骤
   - 预期行为 vs 实际行为
   - 环境信息
   - 截图/日志

## 功能请求

要提议新功能：

1. **使用 Feature Request 模板**创建 Issue
2. **描述用例**：为什么需要这个功能
3. **提供例子**：如何使用这个功能
4. **讨论**：等待反馈和讨论

## 行为准则

### 我们的承诺
我们致力于建立一个开放、友好和尊重的社区。

### 我们的标准
- 使用欢迎的、包容的语言
- 尊重不同的观点和经验
- 接受建设性的批评
- 关注对社区最有利的事

### 不可接受的行为
- 骚扰、辱骂或歧视性语言
- 发布他人隐私信息
- 其他不专业的行为

## 获取帮助

- **文档**: 查看 [docs/](../docs/) 文件夹
- **讨论**: 在 [GitHub Discussions](https://github.com/qcheng557-hue/qlm/discussions) 中提问
- **Issues**: 查看现有或创建新的 [Issue](https://github.com/qcheng557-hue/qlm/issues)

## 许可证

通过贡献代码，您同意您的贡献将在 MIT License 下发布。

---

感谢您的贡献！🎉
