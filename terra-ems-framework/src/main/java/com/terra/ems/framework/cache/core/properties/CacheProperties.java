package com.terra.ems.framework.cache.core.properties;

import com.terra.ems.framework.cache.core.constants.CacheConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = CacheConstants.PROPERTY_PREFIX_CACHE)
public class CacheProperties extends Expire {

    /**
     * 是否允许存储空值
     */
    private Boolean allowNullValues = true;

    /**
     * 缓存名称转换分割符。默认值，"-"
     * <p>
     * 默认缓存名称采用 Redis Key 格式（使用 ":" 分割），使用 ":" 分割的字符串作为Map的Key，":"会丢失。
     * 指定一个分隔符，用于 ":" 分割符的转换
     */
    private String separator = "-";

    private Map<String, Expire> expires = new HashMap<>();

    public Boolean getAllowNullValues() {
        return allowNullValues;
    }

    public void setAllowNullValues(Boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public Map<String, Expire> getExpires() {
        return expires;
    }

    public void setExpires(Map<String, Expire> expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return "CacheProperties{" +
                "allowNullValues=" + allowNullValues +
                ", separator='" + separator + '\'' +
                ", expires=" + expires +
                '}';
    }
}
