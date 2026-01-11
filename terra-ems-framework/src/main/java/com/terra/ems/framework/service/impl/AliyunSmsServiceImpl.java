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
package com.terra.ems.framework.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teautil.models.RuntimeOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terra.ems.framework.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 阿里云短信服务实现
 * <p>
 * 使用阿里云短信服务发送验证码
 * 配置项参考 application.yml 中的 terra.sms.aliyun 配置
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@ConditionalOnProperty(prefix = "terra.sms", name = "provider", havingValue = "aliyun")
public class AliyunSmsServiceImpl implements SmsService {

    private static final Logger log = LoggerFactory.getLogger(AliyunSmsServiceImpl.class);

    private static final String SMS_CODE_PREFIX = "sms:code:";
    private static final String SMS_SEND_LIMIT_PREFIX = "sms:limit:";
    private static final int CODE_LENGTH = 6;
    private static final int CODE_EXPIRE_MINUTES = 5;
    private static final int SEND_LIMIT_SECONDS = 60;

    private final StringRedisTemplate redisTemplate;
    private final SecureRandom random = new SecureRandom();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired(required = false)
    private Client aliyunSmsClient;

    @Value("${terra.sms.aliyun.sign-name}")
    private String signName;

    @Value("${terra.sms.aliyun.template-code}")
    private String templateCode;

    public AliyunSmsServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean sendCode(String phoneNumber) {
        // 检查发送频率限制
        String limitKey = SMS_SEND_LIMIT_PREFIX + phoneNumber;
        Boolean hasLimit = redisTemplate.hasKey(limitKey);
        if (Boolean.TRUE.equals(hasLimit)) {
            log.warn("手机号 {} 发送验证码过于频繁", phoneNumber);
            throw new RuntimeException("发送验证码过于频繁，请稍后再试");
        }

        // 生成6位数字验证码
        String code = generateCode();

        // 调用阿里云短信服务发送验证码
        try {
            sendSmsViaAliyun(phoneNumber, code);
        } catch (Exception e) {
            log.error("调用阿里云短信服务失败", e);
            throw new RuntimeException("发送验证码失败，请稍后重试");
        }

        // 存储验证码到Redis，5分钟过期
        String codeKey = SMS_CODE_PREFIX + phoneNumber;
        redisTemplate.opsForValue().set(codeKey, code, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        // 设置发送频率限制，60秒内不能重复发送
        redisTemplate.opsForValue().set(limitKey, "1", SEND_LIMIT_SECONDS, TimeUnit.SECONDS);

        log.info("成功向手机号 {} 发送验证码", phoneNumber);
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
            log.info("手机号 {} 验证码验证成功", phoneNumber);
        } else {
            log.warn("手机号 {} 验证码验证失败，输入: {}, 期望: {}", phoneNumber, code, storedCode);
        }

        return isValid;
    }

    @Override
    public void clearCode(String phoneNumber) {
        String codeKey = SMS_CODE_PREFIX + phoneNumber;
        redisTemplate.delete(codeKey);
        log.info("已清除手机号 {} 的验证码", phoneNumber);
    }

    /**
     * 通过阿里云短信服务发送验证码
     *
     * @param phoneNumber 手机号
     * @param code        验证码
     */
    private void sendSmsViaAliyun(String phoneNumber, String code) throws Exception {
        if (aliyunSmsClient == null) {
            log.warn("阿里云短信客户端未初始化，验证码将仅保存到Redis");
            log.info("====================================");
            log.info("向手机号 {} 发送验证码: {}", phoneNumber, code);
            log.info("验证码有效期: {} 分钟", CODE_EXPIRE_MINUTES);
            log.info("====================================");
            return;
        }

        // 构建模板参数
        Map<String, String> templateParam = new HashMap<>();
        templateParam.put("code", code);

        // 构建发送请求
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setTemplateParam(objectMapper.writeValueAsString(templateParam));

        // 发送短信
        SendSmsResponse response = aliyunSmsClient.sendSmsWithOptions(request, new RuntimeOptions());
        SendSmsResponseBody body = response.getBody();

        // 检查响应
        String resultCode = body.getCode();
        String message = body.getMessage();

        if ("OK".equals(resultCode) && "OK".equals(message)) {
            log.info("阿里云短信发送成功: 手机号={}, 验证码={}", phoneNumber, code);
        } else {
            log.error("阿里云短信发送失败: code={}, message={}", resultCode, message);
            throw new RuntimeException("短信发送失败: " + message);
        }
    }

    /**
     * 生成随机6位数字验证码
     */
    private String generateCode() {
        int code = random.nextInt(900000) + 100000; // 生成100000-999999之间的随机数
        return String.valueOf(code);
    }
}
