/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
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

package com.terra.ems.system.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.terra.ems.common.constant.CacheConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 验证码服务实现
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
public class CaptchaService {

    private final StringRedisTemplate redisTemplate;

    public CaptchaService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 生成验证码
     *
     * @param identity 验证码标识，如果为空则自动生成
     * @param category 验证码类型，如"default", "login"等
     * @return 包含验证码图片和标识的Map
     */
    public Map<String, Object> generate(String identity, String category) {
        // 如果没有提供identity，生成一个UUID
        if (StringUtils.isBlank(identity)) {
            identity = UUID.randomUUID().toString();
        }

        // 根据category生成不同类型的验证码（目前使用默认实现）
        // 后续可根据category值生成不同难度或类型的验证码：
        // - "default": 4位数字字母组合
        // - "high": 6位数字字母组合，带干扰线
        // - "math": 算术题验证码

        // 定义图形验证码的宽高（180x88，匹配前端90px*44px的2倍比例）
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(180, 88, 4, 100);
        lineCaptcha.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 42));

        // 获取验证码
        String code = lineCaptcha.getCode();
        // 获取Base64格式的图片
        String imageBase64 = lineCaptcha.getImageBase64Data();

        // 存入Redis - Key 只使用 identity（UUID已全局唯一，不需要 category）
        String cacheKey = CacheConstants.CAPTCHA_CODE_KEY + identity;
        redisTemplate.opsForValue().set(cacheKey, code, Duration.ofMinutes(CacheConstants.CAPTCHA_EXPIRATION));

        // 返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("graphicImageBase64", imageBase64);
        result.put("uuid", identity);

        return result;
    }

    /**
     * 验证验证码
     *
     * @param identity 验证码标识
     * @param code     用户输入的验证码
     * @return 验证是否通过
     */
    public boolean validate(String identity, String code) {
        if (StringUtils.isBlank(identity) || StringUtils.isBlank(code)) {
            return false;
        }

        String cacheKey = CacheConstants.CAPTCHA_CODE_KEY + identity;
        String cachedCode = redisTemplate.opsForValue().get(cacheKey);

        if (StringUtils.isBlank(cachedCode)) {
            return false;
        }

        // 验证成功后立即删除验证码，防止重复使用
        boolean isValid = cachedCode.equalsIgnoreCase(code);
        if (isValid) {
            redisTemplate.delete(cacheKey);
        }

        return isValid;
    }

    /**
     * 删除验证码
     *
     * @param identity 验证码标识
     */
    public void remove(String identity) {
        if (StringUtils.isNotBlank(identity)) {
            String cacheKey = CacheConstants.CAPTCHA_CODE_KEY + identity;
            redisTemplate.delete(cacheKey);
        }
    }
}
