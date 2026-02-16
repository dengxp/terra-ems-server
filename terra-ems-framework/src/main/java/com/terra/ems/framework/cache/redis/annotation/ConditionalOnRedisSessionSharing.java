package com.terra.ems.framework.cache.redis.annotation;

import com.terra.ems.framework.cache.redis.condition.RedisSessionSharingCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(RedisSessionSharingCondition.class)
public @interface ConditionalOnRedisSessionSharing {
}
