/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
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

/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
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
package com.terra.ems.framework.service.impl;

import com.terra.ems.framework.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * 控制台短信服务实现
 * <p>
 * 将验证码输出到控制台，不实际发送短信，用于开发和测试环境
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@ConditionalOnProperty(prefix = "terra.sms", name = "provider", havingValue = "console", matchIfMissing = true)
public class ConsoleSmsServiceImpl implements SmsService {

    private static final Logger log = LoggerFactory.getLogger(ConsoleSmsServiceImpl.class);

    private static final String SMS_CODE_PREFIX = "sms:code:";
    private static final int CODE_EXPIRE_MINUTES = 5;

    private final StringRedisTemplate redisTemplate;
    private final SecureRandom random = new SecureRandom();

    public ConsoleSmsServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean sendCode(String phoneNumber) {
        // 生成6位数字验证码
        String code = String.valueOf(random.nextInt(900000) + 100000);

        // 存储验证码到Redis，5分钟过期
        String codeKey = SMS_CODE_PREFIX + phoneNumber;
        redisTemplate.opsForValue().set(codeKey, code, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        log.info("========================================");
        log.info("[Console SMS] 向手机号 {} 发送验证码: {}", phoneNumber, code);
        log.info("[Console SMS] 验证码有效期: {} 分钟", CODE_EXPIRE_MINUTES);
        log.info("========================================");

        return true;
    }

    @Override
    public boolean verifyCode(String phoneNumber, String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }

        String codeKey = SMS_CODE_PREFIX + phoneNumber;
        String storedCode = redisTemplate.opsForValue().get(codeKey);

        if (storedCode == null) {
            log.warn("手机号 {} 的验证码不存在或已过期", phoneNumber);
            return false;
        }

        boolean isValid = storedCode.equals(code.trim());
        if (isValid) {
            // 验证通过后删除验证码，防止重复使用
            redisTemplate.delete(codeKey);
            log.info("手机号 {} 验证码验证成功", phoneNumber);
        } else {
            log.warn("手机号 {} 验证码验证失败", phoneNumber);
        }

        return isValid;
    }

    @Override
    public void clearCode(String phoneNumber) {
        String codeKey = SMS_CODE_PREFIX + phoneNumber;
        redisTemplate.delete(codeKey);
    }
}
