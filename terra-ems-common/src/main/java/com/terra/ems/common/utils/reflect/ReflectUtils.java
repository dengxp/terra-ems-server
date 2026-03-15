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

package com.terra.ems.common.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.terra.ems.common.core.text.Convert;
import com.terra.ems.common.utils.DateUtils;

/**
 * 反射工具类. 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.
 * 
 * @author terra
 */
@SuppressWarnings("rawtypes")
public class ReflectUtils {
    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";
    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * 调用Getter方法.
     * 支持多级，如：对象名.对象名.方法
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeGetter(Object obj, String propertyName) {
        if (obj == null || StringUtils.isBlank(propertyName)) {
            return null;
        }
        Object object = obj;
        for (String name : StringUtils.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
            if (object == null) {
                break;
            }
        }
        return (E) object;
    }

    /**
     * 调用Setter方法, 仅匹配方法名。
     * 支持多级，如：对象名.对象名.方法
     * 自动补全路径：如果中间路径为null，则尝试根据字段类型自动实例化。
     */
    public static <E> void invokeSetter(Object obj, String propertyName, E value) {
        if (obj == null || StringUtils.isBlank(propertyName)) {
            return;
        }
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                Object nextObject = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});

                // 如果中间对象为空，尝试自动实例化
                if (nextObject == null) {
                    try {
                        Field field = getAccessibleField(object, names[i]);
                        if (field != null) {
                            nextObject = field.getType().getDeclaredConstructor().newInstance();
                            String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                            invokeMethodByName(object, setterMethodName, new Object[] { nextObject });
                        }
                    } catch (Exception e) {
                        logger.error("自动实例化属性路径中间节点 [{}] 失败", names[i], e);
                        return;
                    }
                }
                object = nextObject;
                if (object == null) {
                    return;
                }
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[] { value });
            }
        }
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    @SuppressWarnings("unchecked")
    public static <E> E getFieldValue(final Object obj, final String fieldName) {
        if (obj == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            logger.debug("在 [{}] 中，没有找到 [{}] 字段 ", obj.getClass().getName(), fieldName);
            return null;
        }
        try {
            return (E) field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("访问字段 [{}] 失败: {}", fieldName, e.getMessage());
            return null;
        }
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static <E> void setFieldValue(final Object obj, final String fieldName, final E value) {
        if (obj == null || StringUtils.isBlank(fieldName)) {
            return;
        }
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            logger.debug("在 [{}] 中，没有找到 [{}] 字段 ", obj.getClass().getName(), fieldName);
            return;
        }
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("设置字段 [{}] 失败: {}", fieldName, e.getMessage());
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
            final Object[] args) {
        if (obj == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            logger.debug("在 [{}] 中，没有找到 [{}] 方法 ", obj.getClass().getName(), methodName);
            return null;
        }
        try {
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked("调用方法 [" + methodName + "] 失败", e);
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符，只匹配函数名。
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        if (obj == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        int argsNum = (args == null) ? 0 : args.length;
        Method method = getAccessibleMethodByName(obj, methodName, argsNum);
        if (method == null) {
            logger.debug("在 [{}] 中，没有找到 [{}] 方法 ", obj.getClass().getName(), methodName);
            return null;
        }
        try {
            Class<?>[] cs = method.getParameterTypes();
            if (args != null) {
                for (int i = 0; i < cs.length; i++) {
                    if (args[i] != null && !cs[i].isAssignableFrom(args[i].getClass())) {
                        if (cs[i] == String.class) {
                            args[i] = Convert.toStr(args[i]);
                            if (StringUtils.endsWith((String) args[i], ".0")) {
                                args[i] = StringUtils.substringBefore((String) args[i], ".0");
                            }
                        } else if (cs[i] == Integer.class || cs[i] == int.class) {
                            args[i] = Convert.toInt(args[i]);
                        } else if (cs[i] == Long.class || cs[i] == long.class) {
                            args[i] = Convert.toLong(args[i]);
                        } else if (cs[i] == Double.class || cs[i] == double.class) {
                            args[i] = Convert.toDouble(args[i]);
                        } else if (cs[i] == Float.class || cs[i] == float.class) {
                            args[i] = Convert.toFloat(args[i]);
                        } else if (cs[i] == Date.class) {
                            if (args[i] instanceof String) {
                                args[i] = DateUtils.parseDate(args[i]);
                            } else if (args[i] instanceof Double) {
                                args[i] = org.apache.poi.ss.usermodel.DateUtil.getJavaDate((Double) args[i]);
                            }
                        } else if (cs[i] == boolean.class || cs[i] == Boolean.class) {
                            args[i] = Convert.toBool(args[i]);
                        } else if (cs[i].isEnum()) {
                            Object val = args[i];
                            if (val instanceof String || val instanceof Integer) {
                                try {
                                    Method fromValue = cs[i].getMethod("fromValue", Integer.class);
                                    args[i] = fromValue.invoke(null, Convert.toInt(val));
                                } catch (Exception e) {
                                    try {
                                        Method get = cs[i].getMethod("get", Integer.class);
                                        args[i] = get.invoke(null, Convert.toInt(val));
                                    } catch (Exception e2) {
                                        try {
                                            args[i] = Enum.valueOf((Class<Enum>) cs[i], Convert.toStr(val));
                                        } catch (Exception e3) {
                                            // 忽略
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked("调用方法 [" + methodName + "] 失败", e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        if (obj == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
            final Class<?>... parameterTypes) {
        if (obj == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName, int argsNum) {
        if (obj == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == argsNum) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 改变private/protected的方法为public
     */
    public static void makeAccessible(Method method) {
        if (method != null) {
            method.setAccessible(true);
        }
    }

    /**
     * 改变private/protected的成员变量为public
     */
    public static void makeAccessible(Field field) {
        if (field != null) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射, 获得Class定义中声明的泛型参数的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型
     */
    public static Class getClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static Class<?> getUserClass(Object instance) {
        if (instance == null) {
            return null;
        }
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(String msg, Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(msg, e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(msg, ((InvocationTargetException) e).getTargetException());
        }
        return new RuntimeException(msg, e);
    }
}
