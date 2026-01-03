---
description: 架构优化实施计划
---

# Terra EMS v3 架构优化实施计划

## 📋 优化清单

### 短期优化（已开始）

#### 1. DTO 转换层
- [ ] 创建 DTO 包结构
- [ ] 创建 UserDTO, RoleDTO, PermissionDTO
- [ ] 添加 MapStruct 依赖到 pom.xml
- [ ] 创建 Mapper 接口
- [ ] 修改 BaseController 支持 DTO
- [ ] 修改 SysUserController 使用 DTO

#### 2. 数据权限
- [ ] 创建 DataScope 枚举
- [ ] 修改 SysRole 实体添加数据权限字段
- [ ] 创建 DataPermissionHandler
- [ ] 修改 BaseService 支持数据权限过滤
- [ ] 在 SysUserService 中应用数据权限

#### 3. 权限缓存
- [ ] 添加 Redis 配置
- [ ] 创建 PermissionCacheService
- [ ] 修改 SysUserService 使用缓存
- [ ] 添加缓存更新机制

### 中期优化（已开始）

#### 4. 细粒度权限
- [ ] 创建 ResourceType 和 ActionType 枚举
- [ ] 修改 SysPermission 实体
- [ ] 创建权限表达式解析器
- [ ] 实现 PermissionEvaluator
- [ ] 添加 @PreAuthorize 支持

#### 5. 批量操作优化
- [ ] BaseService 添加 batchSave 方法
- [ ] BaseService 添加 batchDelete 方法
- [ ] BaseController 添加批量操作端点

#### 6. 动态查询
- [ ] 创建 SpecificationBuilder
- [ ] BaseService 添加动态查询方法
- [ ] 创建查询参数封装类

## 🚀 执行步骤

### Step 1: DTO 层 (当前步骤)
1. 创建 DTO 包和类
2. 添加 MapStruct 依赖
3. 创建 Mapper 接口
4. 修改 Controller 使用 DTO

### Step 2: 数据权限
1. 创建枚举和配置
2. 修改实体
3. 实现数据权限过滤
4. 应用到查询

### Step 3: 缓存优化
1. 配置 Redis
2. 实现缓存服务
3. 应用到权限查询

### Step 4: 细粒度权限
1. 扩展权限模型
2. 实现权限评估器
3. 集成到 Spring Security

### Step 5: 批量操作
1. 扩展 BaseService
2. 添加批量端点

### Step 6: 动态查询
1. 实现 Specification Builder
2. 集成到 BaseService

## 📊 预期收益

- **性能**: 缓存+批量操作，提升 50%+
- **安全**: DTO 层+数据权限，防止信息泄露
- **灵活**: 动态查询+细粒度权限，适应复杂需求
- **可维护**: 统一的 Base 层，减少重复代码

## ⚠️ 注意事项

1. 确保向后兼容
2. 充分测试数据权限逻辑
3. 缓存失效策略要完善
4. 批量操作注意事务管理
