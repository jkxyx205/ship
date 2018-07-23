package com.yodean.platform.api.util;


import com.yodean.platform.domain.BaseEntity;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Convert;
import javax.persistence.Transient;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by rick on 5/8/18.
 */
public class EntityBeanUtils {

    private static final transient Logger logger = LoggerFactory.getLogger(EntityBeanUtils.class);

    private static final ThreadLocal<Set<Object>> mergeObject = new ThreadLocal<>();

    /**
     * 对象是POJO，不能是集合对象
     * 合并对象属性，将obj的非null值合并到src，
     * 合并:基本数据类型+ String + 枚举 + Date
     *
     * @param src
     * @param obj
     */
    public static void  merge(Object src, Object obj) {

        if (mergeObject.get() == null) {
            mergeObject.set(new HashSet<>());
        }

        if (!(mergeObject.get().contains(src))) { //防止循环引用
            mergeObject.get().add(src);
        } else {
            return;
        }

        PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
        PropertyDescriptor[] propertyDescriptorsOfSrc = propertyUtilsBean.getPropertyDescriptors(src.getClass());

        Set<String> propertyNames = setterNames(src.getClass()); //new HashSet<>(propertyDescriptorsOfObj.length);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptorsOfSrc) {
            String name = propertyDescriptor.getName();

            if (!propertyNames.contains(name)) continue;

            Class<?> type = propertyDescriptor.getPropertyType();

            if (isIgnoreProperty(src.getClass(), name)) { //忽略字段
                continue;
            }

            try {
                Object srcPropertyValue = propertyUtilsBean.getProperty(src, name);
                Object objPropertyValue = propertyUtilsBean.getProperty(obj, name);

                // 普通属性
                if (isMappingJavaType(type)) { //"基本"属性类型
                    if( Objects.nonNull(objPropertyValue))
                        PropertyUtils.setProperty(src, name, objPropertyValue);

                    continue;

                }

                // 处理对象（集合）
                if (srcPropertyValue == null || objPropertyValue == null) {
                    PropertyUtils.setProperty(src, name, objPropertyValue);
                    continue;
                }

                if (isConverter(src.getClass(), name)) { //hibernate自定义对象

                    merge(srcPropertyValue, objPropertyValue);

                } else {
                    if (Collection.class.isAssignableFrom(type)) { //集合对象
                        Collection srcCollection = (Collection)srcPropertyValue;
                        Collection objCollection = (Collection)objPropertyValue;

                        Map<Long, Object> srcMapping = new HashMap<>(srcCollection.size());

                        for (Object objSrc : srcCollection) {
                            srcMapping.put(((BaseEntity)(objSrc)).getId(), objSrc); //主键id
                            srcMapping.put((long) objSrc.hashCode(), objSrc); //hashCode
                        }

                        for (Object objSub : objCollection) {
                            if (srcCollection.contains(objSub)) { //存在合并

                                Long id = ((BaseEntity)(objSub)).getId();

                                if (Objects.isNull(id))
                                    id = (long)objSub.hashCode();

                                merge(srcMapping.get(id), objSub);

                            }  else {//不存在直接添加
                                srcCollection.add(objSub);
                                logger.info("Add entity [{}] property [{}] with [{}]", src.getClass(), name, objSub);
                            }

                        }

                        Iterator<Object> iterator = srcCollection.iterator();

                        while (iterator.hasNext()) {
                            Object objSrc = iterator.next();
                            if (!objCollection.contains(objSrc)) {
                                iterator.remove();
                                logger.info("Remove entity [{}] property [{}] with [{}]", src.getClass(), name, objSrc);
                            }
                        }
                    } else if (BaseEntity.class.isAssignableFrom(type)){// Entity Object
                        //modify at 20180720
                        if (Objects.equals(((BaseEntity)srcPropertyValue).getId(), ((BaseEntity)objPropertyValue).getId())) { //合并实体对象
                            merge(srcPropertyValue, objPropertyValue);
                        } else { //如id发生变化，则不需要合并，直接返回新的obj
                            PropertyUtils.setProperty(src, name, objPropertyValue);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean isMappingJavaType(Class<?> type) {
        return type == Boolean.class || type == Character.class || type == Byte.class || type == Short.class
                || type == Integer.class || type == Long.class || type == Float.class || type == Double.class
                || type == Date.class || type == String.class || type == BigInteger.class || type == BigDecimal.class
                || type == Byte[].class || type == Blob.class || type == Clob.class || type == Timestamp.class
                || type.isEnum();
    }


    private static Set<String> setterNames(Class<?> c) {
        Set<String> set = new HashSet<>();

        Method[] methods = c.getMethods();
        for (Method method : methods) {
            if (isSetter(method)) {
                set.add(unCapitalize(method.getName().substring(3)));
            }
        }

        return set;
    }

    private static String unCapitalize(Object target) {
        if(target == null) {
            return null;
        } else {
            StringBuilder result = new StringBuilder(target.toString());
            if(result.length() > 0) {
                result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
            }

            return result.toString();
        }
    }

    private static boolean isConverter(Class<?> aClass, String name){
        Field field;
        try {
            field = aClass.getDeclaredField(name);
            return field.isAnnotationPresent(Convert.class) ;
        } catch (NoSuchFieldException e) {
            return false;
        }

    }

    private static boolean isIgnoreProperty(Class<?> aClass, String name) {
        Field field;
        try {
            field = aClass.getDeclaredField(name);
            return field.isAnnotationPresent(Transient.class);
        } catch (NoSuchFieldException e) {
            return true;
        }
    }

    public static boolean isSetter(Method method){
        if(!method.getName().startsWith("set")) return false;
        if(method.getParameterTypes().length != 1) return false;
        return true;
    }
}