package com.terra.ems.framework.cache.redis.condition;

import com.terra.ems.common.context.PropertyFinder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class RedisSessionSharingCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String property = PropertyFinder.getSessionStoreType(context.getEnvironment());
        boolean result = StringUtils.isNotBlank(property) && StringUtils.equalsIgnoreCase(property, "redis");
        log.debug("[Terra] |- Condition [Redis Session Sharing] value is [{}]", result);
        return result;
    }
}
