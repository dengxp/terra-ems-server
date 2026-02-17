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
package com.terra.ems.system.repository;

import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.system.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户Repository
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Long> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Optional<SysUser> findByUsername(String username);

    /**
     * 根据手机号查找用户
     *
     * @param phone 手机号
     * @return 用户信息
     */
    Optional<SysUser> findByPhone(String phone);

    /**
     * 根据用户名或手机号查找用户
     *
     * @param username 用户名
     * @param phone    手机号
     * @return 用户信息
     */
    Optional<SysUser> findByUsernameOrPhone(String username, String phone);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    boolean existsByPhone(String phone);
}
