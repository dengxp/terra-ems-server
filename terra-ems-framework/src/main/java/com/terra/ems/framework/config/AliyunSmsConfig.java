/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.terra.ems.framework.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Name: AliyunSmsConfig.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 阿里云短信服务配置
 * <p>
 * 只有在启用阿里云短信时才会加载此配置
 *
 * @author dengxueping
 */
@Configuration
@ConditionalOnProperty(prefix = "terra.sms", name = "provider", havingValue = "aliyun")
public class AliyunSmsConfig {

    @Value("${terra.sms.aliyun.access-key-id}")
    private String accessKeyId;

    @Value("${terra.sms.aliyun.access-key-secret}")
    private String accessKeySecret;

    @Value("${terra.sms.aliyun.endpoint:dysmsapi.aliyuncs.com}")
    private String endpoint;

    /**
     * 创建阿里云短信客户端
     */
    @Bean("aliyunSmsClient")
    public Client createAliyunSmsClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = endpoint;
        return new Client(config);
    }
}
