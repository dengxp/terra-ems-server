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

package com.terra.ems.common.constant;

public interface BaseConstants {

    String OPEN_API_SECURITY_SCHEME_BEARER_NAME = "TERRA_AUTH";

    String DEFAULT_TENANT_ID = "master";
    String DEFAULT_TREE_ROOT_ID = "0";

    /* ---------- 配置属性通用常量 ---------- */
    String PROPERTY_ENABLED = ".enabled";

    String PROPERTY_PREFIX_SPRING = "spring";
    String PROPERTY_PREFIX_FEIGN = "feign";
    String PROPERTY_PREFIX_SERVER = "server";
    String PROPERTY_PREFIX_TERRA = "terra";

    /* ---------- Spring 家族配置属性 ---------- */
    String ITEM_SPRING_APPLICATION_NAME = PROPERTY_PREFIX_SPRING + ".application.name";
    String ITEM_SPRING_SESSION_STORE_TYPE = PROPERTY_PREFIX_SPRING + ".session.store-type";
    String ITEM_SERVER_PORT = PROPERTY_PREFIX_SERVER + ".port";

    /* ---------- Terra 自定义配置属性 ---------- */
    String PROPERTY_PREFIX_PLATFORM = PROPERTY_PREFIX_TERRA + ".platform";
    String PROPERTY_PREFIX_SECURITY = PROPERTY_PREFIX_TERRA + ".security";
    String PROPERTY_PREFIX_REST = PROPERTY_PREFIX_TERRA + ".rest";
    String PROPERTY_PREFIX_SWAGGER = PROPERTY_PREFIX_TERRA + ".swagger";
    String PROPERTY_PREFIX_EVENT = PROPERTY_PREFIX_TERRA + ".event";

    String PROPERTY_SPRING_CLOUD = PROPERTY_PREFIX_SPRING + ".cloud";
    String PROPERTY_SPRING_JPA = PROPERTY_PREFIX_SPRING + ".jpa";
    String PROPERTY_SPRING_DATA = PROPERTY_PREFIX_SPRING + ".data";
    String PROPERTY_SPRING_REDIS = PROPERTY_SPRING_DATA + ".redis";

    String ANNOTATION_PREFIX = "${";
    String ANNOTATION_SUFFIX = "}";

    /* ---------- 通用缓存常量 ---------- */

    String CACHE_PREFIX = "cache:";

    String CACHE_SIMPLE_BASE_PREFIX = CACHE_PREFIX + "simple:";
    String CACHE_TOKEN_BASE_PREFIX = CACHE_PREFIX + "token:";

    String AREA_PREFIX = "data:";

    /* ---------- Oauth2 和 Security 通用缓存常量 ---------- */

    /**
     * Oauth2 模式类型
     */
    String AUTHORIZATION_CODE = "authorization_code";
    String IMPLICIT = "implicit";
    String PASSWORD = "password";
    String CLIENT_CREDENTIALS = "client_credentials";
    String REFRESH_TOKEN = "refresh_token";
    String SOCIAL_CREDENTIALS = "social_credentials";

    String BEARER_TYPE = "Bearer";
    String BEARER_TOKEN = BEARER_TYPE + SymbolConstants.SPACE;

    String DEFAULT_AUTHORIZATION_ENDPOINT = "/oauth2/authorize";
    String DEFAULT_TOKEN_ENDPOINT = "/oauth2/token";
    String DEFAULT_JWK_SET_ENDPOINT = "/oauth2/jwks";
    String DEFAULT_TOKEN_REVOCATION_ENDPOINT = "/oauth2/revoke";
    String DEFAULT_TOKEN_INTROSPECTION_ENDPOINT = "/oauth2/introspect";
    String DEFAULT_OIDC_CLIENT_REGISTRATION_ENDPOINT = "/connect/register";
    String DEFAULT_OIDC_USER_INFO_ENDPOINT = "/userinfo";

    String AUTHORITIES = "authorities";
    String AVATAR = "avatar";
    String EMPLOYEE_ID = "employeeId";
    String LICENSE = "license";
    String OPEN_ID = "openid";
    String PRINCIPAL = "principal";
    String ROLES = "roles";
    String PERMISSIONS = "permissions";
    String SOURCE = "source";
    String USERNAME = "username";

}
