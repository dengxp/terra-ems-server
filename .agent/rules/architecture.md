---
trigger: always_on
---

# 架构规范

## 项目分层结构

```
terra-ems-v3/
├── terra-ems-admin      # 启动模块、Controller 层
├── terra-ems-system     # 系统模块（Entity、Repository、Service）
├── terra-ems-ems        # 业务模块（能源管理）
├── terra-ems-framework  # 框架层（基类、工具类）
└── terra-ems-common     # 公共模块（通用定义）
```

## Controller 继承体系

```
Controller                     # 基础定义，提供统一响应转换方法
    └── ReadableController     # 只读操作（查询、分页）
            └── WritableController    # 读写操作（增删改查）
                    └── BaseController     # 完整 CRUD 操作
```

## API 接口设计规范

### 1. RESTful 路径规范

| 操作 | HTTP 方法 | 路径示例 | 说明 |
|------|-----------|----------|------|
| 条件分页查询 | GET | `/system/user` | 对应 `findByPage`，支持条件过滤 |
| 不分页条件查询 | GET | `/system/user/list` | 对应 `findList`，返回列表无分页 |
| 查询单个 | GET | `/system/user/{id}` | 按 ID 查询 |
| 新增/修改 | POST/PUT | `/system/user` | 请求体为实体 |
| 删除 | DELETE | `/system/user/{id}` | 按 ID 删除 |
| 批量删除 | DELETE | `/system/user` | 请求体为 ID 列表，对应 `deleteBatch` |

| 移动节点 | PATCH | `/ems/resource/{id}/move` | 变更父节点，对应 `move` |
| 修改状态 | PATCH | `/ems/resource/{id}/status` | 简单属性变更 |

### 2. 下拉选项标准 (Selection Options)

为统一前端下拉组件（Select/Cascader）的数据源，必须遵循以下规范：

1. **DTO 定义**：使用 `com.terra.ems.common.domain.Option<T>`（包含 `label` 和 `value`）。
2. **Service 层**：定义 `findOptions()` 方法，返回 `List<Option<T>>`。
3. **Controller 层**：暴露 `GET /options` 接口。
4. **前端交互**：通过该接口获取 ID (`value`) 与 名称 (`label`) 的映射，禁止直接使用实体列表。

### 3. RESTful 命名规则
- **命名规则**：使用小写字母和连字符 `-`；模块前缀如 `/system/`、`/ems/`；资源名使用单数（如 `/user` 而非 `/users`）。

## 命名规范 (Naming Conventions)

项目强制使用以下命名约定，以保证代码的一致性和可预测性。

### 1. Controller 层

| 操作类型 | 推荐前缀/方法名 | 禁止前缀 | 示例 | 备注 |
|---|---|---|---|---|
| **单条/列表检索** | `find` | `get` | `findById`, `findByStatus` | `get` 仅保留用于 POJO 属性访问；允许简单条件的 List 返回接口 |
| **条件分页查询** | `findByPage` | `list`, `search` | `findByPage(Pager, @RequestParam...)` 或 `findByPage(Pager, QueryParam)` | 统一映射 `GET /`；参数可用 `@RequestParam` 或 Param DTO 类接收 |
| **不分页条件查询** | `findList` | - | `findList(@RequestParam...)` 或 `findList(QueryParam)` | 统一映射 `GET /list`；返回 `List<E>`；参数可用 `@RequestParam` 或 Param DTO 类接收 |
| **创建** | `create` | `add`, `insert` | `create(@RequestBody E)` | |
| **更新** | `update` | `modify`, `edit` | `update(@PathVariable ID, @RequestBody E)` | |
| **单条删除** | `delete` | `remove` | `delete(@PathVariable ID)` | 映射 `DELETE /{id}` |
| **批量删除** | `deleteBatch` | - | `deleteBatch(@RequestBody List<ID> ids)` | 统一映射 `DELETE /`；必须使用 `@RequestBody` |
| **Service注入** | `xxxService` | `service` | `private final SysUserService sysUserService` | 禁止使用通用的 `service` 变量名 |

### 2. 枚举参数绑定规范 (Enum Handling)

Controller 在接收枚举类型的查询参数时，应确保入参转换的鲁棒性。建议通过全局转换器或在方法内显式调用枚举解析工具，防止因前端传参（如数值字符串）不匹配导致的空指针或转换异常。

### 3. 数据排序与展示策略 (Sorting & ID Policy)

1. **默认排序**：Service 层查询应默认按业务编码 (Code) 或主键 (ID) 进行排序，保证接口输出的幂等性。
2. **隐藏技术列**：除非业务严需，否则应避免在前端展示技术主键 ID，推荐优先展示业务层面的“名称”或“编码”。
3. **简化排序逻辑**：应尽量利用后端自动排序策略，避免引入高维护成本的手动排序字段。

### 2. Service 层

Service 层命名应与 Spring Data JPA 及 Controller 层保持语义一致。

| 操作类型 | 命名规范 | 返回值示例 | 备注 |
|---|---|---|---|
| **主键查询** | `findById` | `E` (Nullable) 或 `Optional<E>` | 框架默认返回 Nullable E |
| **条件查询** | `find[By...]` | `List<E>` 或 `E` | 如 `findByCode`, `findEnabled` |
| **分页查询** | `findByPage` | `Page<E>` | 对应 Controller 的 `search` |
| **统计** | `count[By...]` | `long` | |
| **保存/更新** | `saveOrUpdate` / `create` / `update` | `E` | 简单 CRUD 用 `saveOrUpdate`，业务复杂则分拆 |
| **删除** | `delete` / `deleteById` | `void` | |
| **业务动词** | 动词开头 | 业务对象 | 如 `calculateCost`, `importExcel`, `syncData` |

## Entity 规范

所有实体类必须：
1. 继承 `com.terra.ems.framework.jpa.entity.Entity`
2. 使用 `@Entity` 和 `@Table` 注解
3. 表名使用单数形式，如 `sys_user`

## 交互模式与安全规范 (Entity 模式)

### 1. 查询 (Query)
- **推荐模式**：使用 **QueryParam DTO** 或 **Map<String, Object>** 接收查询条件。
- **Entity 使用**：**不推荐** 使用 Entity 作为 `@GetMapping` 的查询对象。

### 2. 增删改 (Command) - 敏捷模式
为提高效率，默认允许使用 **Entity** 作为 `@RequestBody`。

**必须遵守的安全红线**：
- **响应安全 (Response)**：
  敏感字段（密码、盐值等）**必须**使用 `@JsonIgnore` 或 `@JsonProperty(access = Access.WRITE_ONLY)` 进行物理屏蔽。
- **入参安全 (Request)**：
  防止“批量赋值”攻击。在 Service 更新逻辑中，**禁止** 直接保存 Controller 传入的 Entity。需先查库获取 `existing` 对象，再手动 Copy 允许修改的字段。

## Repository 规范

所有 Repository 必须：
1. 继承 `com.terra.ems.framework.jpa.repository.BaseRepository<E, ID>`
2. 放置于对应模块的 `repository` 包下
3. 使用 `@Repository` 注解

### 1. 动态查询规范

- **强制**：通用动态查询（如多条件组合过滤）必须使用 JPA `Specification` 实现。
- **模式 (findByPage 实现规范)**：
  - **路径与命名**：统一映射为类级别的 `GET` 路径，方法命名为 `findByPage`。
  - **实现逻辑**：查询逻辑（Specification 构建）应在 Controller 层**显式**实现，以便于根据不同的请求参数（QueryParam DTO 或 Map）进行灵活定制。
  - **代码示例**：
    ```java
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, XxxQueryParam param) {
        return findByPage(pager, buildSpecification(param));
    }

    private Specification<Xxx> buildSpecification(XxxQueryParam param) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(param.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            // ... 其他过滤条件
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    ```
- **禁止**：禁止在 Service/Repository 层定义 `findByConditions` 等 Ad-hoc 方法使用 `@Query` 手动拼接 JPQL 实现通用过滤。
- **简单列表模式**：对于单一维度的简单过滤（如按状态、类型），允许显式定义 `findBy...` 接口（如 `/status/{status}`），直接返回 `List<E>`，无需 Specification。

## 异常处理规范 (Exception Handling)

项目采用“中心化异常解析 + 全局处理”模式：
- 业务异常抛出 `TerraException(ErrorCodes.XXX)`。
- 全局异常处理器统一拦截并封装 `Result`。
- **强制约束**：Controller 层 **禁止** 使用 `try-catch` 捕获业务异常，必须交由全局处理器处理。

## 统一响应格式 (Result)

所有 API 接口必须统一返回 `Result<T>` 对象。

## 层次结构（Tree）管理规范

### 1. 数据映射模式 (Data Mapping)

对于 `@ManyToOne` 关联（如 `parent` 字段），推荐使用 **`@JsonProperty` 桥接模式** 实现前端 ID 与后端实体对象的无缝转换：

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "parent_id")
private EnergyUnit parent;

/**
 * 获取父节点ID（用于前端展示和反序列化桥接）
 */
@JsonProperty("parentId")
public Long getParentId() {
    return parent != null ? parent.getId() : null;
}

/**
 * 设置父节点ID（用于接收前端扁平数据并自动转为对象存根）
 */
@JsonProperty("parentId")
public void setParentId(Long parentId) {
    if (parentId != null) {
        this.parent = new EnergyUnit();
        this.parent.setId(parentId);
    } else {
        this.parent = null;
    }
}
```

**优势**：
- **解耦**：前端只需传递扁平的 `parentId`，无需构造嵌套对象。
- **透明**：Jackson 反序列化时自动调用 `setParentId`，JPA 保存时直接使用 `parent` 关联。
- **双向**：序列化响应时，`getParentId` 自动输出扁平 ID，避免循环引用。

### 2. 移动逻辑安全性 (Move Safety)
- **非法路径校验**：移动节点时必须校验：
    - 目标父节点不能是节点自身。
    - 目标父节点不能是该节点的子孙节点（防止成环）。
- **级联更新**：若存在冗余字段（如 `level`, `path`），移动后必须递归更新所有下级节点。

## Swagger 注解规范

Controller 必须添加以下注解以生成清晰文档：

```java
@Tag(name = "模块名称", description = "模块描述")
@RestController
@RequestMapping("/prefix/resource")
public class XxxController {

    @Operation(summary = "接口简述", description = "详细说明")
    @GetMapping("/{id}")
    public Result<Xxx> findById(@PathVariable Long id) {
        // ...
    }
}
```
