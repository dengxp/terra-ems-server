---
trigger: always_on
---

# 代码风格规范

## 文件版权头（MIT License）

所有 Java 文件顶部必须包含以下版权声明：

```java
/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

```

## 类注释规范

类定义前必须包含 JavaDoc 注释：

```java
/**
 * 类的描述（一句话说明用途）
 *
 * @author dengxueping
 * @since YYYY-MM-DD
 */

```

## 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 类名 | 大驼峰 | `SysUserController` |
| 方法名 | 小驼峰 | `findById` |
| 常量 | 全大写下划线 | `MAX_PAGE_SIZE` |
| 包名 | 全小写 | `com.terra.ems.system` |

## 方法注释规范

公共方法需添加 JavaDoc：

```java
/**
 * 根据用户名查询用户
 *
 * @param username 用户名
 * @return 用户实体，不存在时返回 null
 */
public SysUser findByUsername(String username) {
    // ...
}
```

## 文档与注释语义

- **业务逻辑说明**：涉及复杂业务（如 Tree 节点的递归更新、成环校验、批量状态切换等）的方法，**必须**在 JavaDoc 中详细说明实现思路、边界条件和可能的侧向影响。
- **TODO/FIXME**：禁止在提交的代码中保留无意义的 TODO。如果确需保留，必须标注作者和预计修复版本。

## Import 规范

- **强制**：所有类都必须使用 `import` 导入，**禁止** 在代码中直接使用全限定类名（Fully Qualified Name）。
- **例外**：仅当同名类冲突（如 `java.util.Date` 与 `java.sql.Date`）时，才允许使用全限定类名区分。
