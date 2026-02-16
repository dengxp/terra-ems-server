package com.terra.ems.common.context;

import com.terra.ems.common.constant.BaseConstants;
import org.springframework.core.env.Environment;

public class PropertyFinder {

    public static String getApplicationName(Environment environment) {
        return PropertyResolver.getProperty(environment, BaseConstants.ITEM_SPRING_APPLICATION_NAME);
    }

    public static String getServerPort(Environment environment) {
        return PropertyResolver.getProperty(environment, BaseConstants.ITEM_SERVER_PORT);
    }

    public static String getSessionStoreType(Environment environment) {
        return PropertyResolver.getProperty(environment, BaseConstants.ITEM_SPRING_SESSION_STORE_TYPE);
    }
}
