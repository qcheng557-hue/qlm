# 开发指南

## 本地开发环境设置

### 1. 前置条件

#### 系统要求
- OS: macOS, Windows 10+, Linux (Ubuntu 20.04+)
- RAM: 至少 8GB，建议 16GB
- Disk: 至少 20GB 可用空间

#### 必需软件
```bash
# Java 17+
java -version
# 应该显示: openjdk version "17.x.x"

# Node.js 18+
node -v
# 应该显示: v18.x.x

# npm
npm -v
# 应该显示: 9.x.x

# Docker & Docker Compose
docker -v
docker-compose -v

# Git
git -v
```

### 2. 克隆与初始化项目

```bash
# 克隆仓库
git clone https://github.com/qcheng557-hue/qlm.git
cd qlm

# 创建开发分支
git checkout -b develop

# 创建功能分支
git checkout -b feature/your-feature-name
```

### 3. 启动基础服务

```bash
# 启动所有依赖服务（PostgreSQL, Redis, RabbitMQ 等）
docker-compose up -d

# 验证服务状态
docker ps

# 查看日志
docker-compose logs -f postgres
docker-compose logs -f redis
docker-compose logs -f rabbitmq
```

### 4. 后端开发

#### 4.1 导入项目到 IDE

**IntelliJ IDEA:**
1. 打开 IntelliJ IDEA
2. 选择 File > Open，选择 `backend` 文件夹
3. 等待 Maven 项目同步完成
4. 标记 `src/main/java` 为 Sources Root
5. 标记 `src/main/resources` 为 Resources Root
6. 标记 `src/test/java` 为 Test Sources Root

**Eclipse:**
1. 右击项目 > Configure > Convert to Maven Project
2. 或者 Import > Maven > Existing Maven Projects

#### 4.2 启动后端服���

```bash
cd backend

# 清理并编译
mvn clean install

# 启动应用
mvn spring-boot:run

# 或者使用 IDE 的 Run 按钮
# 通常在 Application 类中右击选择 Run
```

后端应该运行在 `http://localhost:8080`

#### 4.3 创建新的 API 端点

1. **创建 Entity 类** (`src/main/java/com/qlm/entity/`)
```java
@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    private String description;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

2. **创建 Repository** (`src/main/java/com/qlm/repository/`)
```java
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByNameContainingIgnoreCase(String keyword);
    Page<Project> findAll(Pageable pageable);
}
```

3. **创建 DTO** (`src/main/java/com/qlm/dto/`)
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
}
```

4. **创建 Service** (`src/main/java/com/qlm/service/`)
```java
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    
    public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable)
            .map(this::convertToDTO);
    }
    
    private ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName(), project.getDescription());
    }
}
```

5. **创建 Controller** (`src/main/java/com/qlm/controller/`)
```java
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    
    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }
}
```

### 5. 前端开发

#### 5.1 启动前端应用

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm start

# 应该自动打开 http://localhost:3000
```

#### 5.2 项目结构说明

```
src/
├── components/           # React 组件
│   ├── common/          # 通用组件
│   ├── layout/          # 布局组件
│   └── business/        # 业务组件
├── pages/               # 页面容器
│   ├── dashboard/
│   ├── projects/
│   ├── designs/
│   └── ...
├── services/            # API 服务
│   ├── api.ts           # API 配置
│   ├── projectService.ts
│   └── ...
├── store/               # Zustand 状态管理
│   ├── projectStore.ts
│   └── userStore.ts
├── styles/              # 全局样式
├── utils/               # 工具函数
└── App.tsx
```

#### 5.3 创建新的 React 组件

**功能组件示例:**
```typescript
import React from 'react';
import { Button, Form, Input } from 'antd';
import { useProjectStore } from '@/store/projectStore';

interface ProjectFormProps {
  onSubmit?: (values: ProjectFormData) => void;
}

interface ProjectFormData {
  name: string;
  description: string;
}

export const ProjectForm: React.FC<ProjectFormProps> = ({ onSubmit }) => {
  const [form] = Form.useForm();
  const { loading } = useProjectStore();

  const handleFinish = (values: ProjectFormData) => {
    onSubmit?.(values);
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={handleFinish}
    >
      <Form.Item
        label="项目名称"
        name="name"
        rules={[{ required: true, message: '请输入项目名称' }]}
      >
        <Input placeholder="请输入项目名称" />
      </Form.Item>

      <Form.Item
        label="项目描述"
        name="description"
        rules={[{ required: true, message: '请输入项目描述' }]}
      >
        <Input.TextArea rows={4} placeholder="请输入项目描述" />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" loading={loading}>
          提交
        </Button>
      </Form.Item>
    </Form>
  );
};

export default ProjectForm;
```

**Zustand 状态管理示例:**
```typescript
import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

interface ProjectState {
  projects: Project[];
  loading: boolean;
  
  setProjects: (projects: Project[]) => void;
  setLoading: (loading: boolean) => void;
  addProject: (project: Project) => void;
}

export const useProjectStore = create<ProjectState>()(
  immer((set) => ({
    projects: [],
    loading: false,
    
    setProjects: (projects) => set({ projects }),
    setLoading: (loading) => set({ loading }),
    addProject: (project) => set((state) => {
      state.projects.push(project);
    }),
  }))
);
```

### 6. 数据库管理

#### 6.1 创建数据库迁移脚本

创建文件 `backend/src/main/resources/db/migration/V1__create_tables.sql`:

```sql
-- 创建项目表
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'pending',
    budget DECIMAL(12, 2),
    spent DECIMAL(12, 2) DEFAULT 0,
    progress INTEGER DEFAULT 0,
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建用户表
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 更多表定义...
CREATE INDEX idx_projects_status ON projects(status);
CREATE INDEX idx_projects_created_at ON projects(created_at DESC);
```

#### 6.2 运行迁移

```bash
cd backend
mvn flyway:migrate

# 验证迁移
mvn flyway:info
```

### 7. 测试

#### 7.1 后端单元测试

```bash
cd backend

# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=ProjectServiceTest

# 生成覆盖率报告
mvn test jacoco:report
# 报告位置: target/site/jacoco/index.html
```

**编写单元测试示例:**
```java
@SpringBootTest
public class ProjectServiceTest {
    @MockBean
    private ProjectRepository projectRepository;
    
    @InjectMocks
    private ProjectService projectService;
    
    @Test
    public void testGetAllProjects() {
        // Arrange
        Project project = Project.builder()
            .name("Test Project")
            .description("Test Description")
            .build();
        Page<Project> page = new PageImpl<>(List.of(project));
        
        when(projectRepository.findAll(any(Pageable.class)))
            .thenReturn(page);
        
        // Act
        Page<ProjectDTO> result = projectService.getAllProjects(PageRequest.of(0, 10));
        
        // Assert
        assertEquals(1, result.getContent().size());
        assertEquals("Test Project", result.getContent().get(0).getName());
    }
}
```

#### 7.2 前端单元测试

```bash
cd frontend

# 运行所有测试
npm test

# 监视模式
npm test -- --watch

# 生成覆盖率报告
npm test -- --coverage
```

**编写 React 测试示例:**
```typescript
import { render, screen } from '@testing-library/react';
import { ProjectForm } from '@/components/ProjectForm';

describe('ProjectForm', () => {
  it('should render form fields', () => {
    render(<ProjectForm />);
    
    expect(screen.getByLabelText(/项目名称/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/项目描述/i)).toBeInTheDocument();
  });
});
```

### 8. 代码规范

#### 8.1 Java 代码规范
- 使用 4 个空格进行缩进
- 使用驼峰命名法
- 类名使用 PascalCase
- 方法和变量名使用 camelCase
- 常量使用 UPPER_SNAKE_CASE

#### 8.2 TypeScript 代码规范
- 使用 2 个空格进行缩进
- 使用分号结尾
- 显式指定类型
- 组件文件使用 PascalCase
- 普通文件使用 camelCase

#### 8.3 代码检查

```bash
# 后端
cd backend
mvn checkstyle:check

# 前端
cd frontend
npm run lint
npm run format
```

### 9. 提交代码

#### 9.1 分支策略

- `main`: 生产环境分支，只接受 Release PR
- `develop`: 开发分支，集成各功能分支
- `feature/*`: 功能分支
- `bugfix/*`: 修复分支
- `release/*`: 发布分支

#### 9.2 提交流程

```bash
# 创建功能分支
git checkout -b feature/add-new-module

# 编写代码，定期提交
git add .
git commit -m "feat: add new module for project management"

# 推送到远程仓库
git push origin feature/add-new-module

# 在 GitHub 上创建 Pull Request
# 等待代码审查并 merge
```

#### 9.3 提交消息规范

遵循 Conventional Commits:
```
<type>(<scope>): <subject>
<blank line>
<body>
<blank line>
<footer>
```

**Types:**
- `feat`: 新功能
- `fix`: 修复 bug
- `docs`: 文档更新
- `style`: 代码格式（不影响功能）
- `refactor`: 重构
- `test`: 添加测试
- `chore`: 构建、依赖管理等

**Example:**
```
feat(projects): add project listing page

- Implement project list component
- Add filtering and pagination
- Integrate with API

Closes #123
```

### 10. 调试技巧

#### 10.1 后端调试

**IntelliJ IDEA:**
1. 在代码中设置断点（点击行号左侧）
2. 右击项目 > Debug 'Application'
3. 程序会在断点处暂停

**远程调试:**
```bash
mvn clean spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```

#### 10.2 前端调试

**Chrome DevTools:**
1. 按 F12 打开开发者工具
2. 在 Sources 标签中设置断点
3. 执行相关操作，程序会在断点处暂停

**React Developer Tools:**
```bash
# 安装 Chrome 扩展
# 在 Chrome 中搜索 "React Developer Tools"
# 在 Components 和 Profiler 标签中调试
```

---

更新日期: 2024-02-01
