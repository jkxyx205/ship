package com.yodean.dictionary;

import com.yodean.dictionary.entity.Dict;
import com.yodean.dictionary.service.DictService;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.context.ApplicationContext;

import javax.transaction.Transactional;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by rick on 5/17/18.
 */
public final class DictionaryUtils {
    private static  ApplicationContext applicationContext;


    private static Dict parse(String category, String name) {
        DictService dictService = applicationContext.getBean(DictService.class);

        Dict Dict = dictService.findByCategoryAndName(category, name);
        return Dict;
    }

    public static void parse(Object obj) {
        if (Objects.isNull(obj)) return;

        if (obj instanceof Iterable) {
            Iterable iterable = (Iterable)obj;
            Iterator it =  iterable.iterator();
            while(it.hasNext()) {
                parse(it.next());
            }

        } else if(obj instanceof Map) {
            Map map = (Map)obj;

            Set<Map.Entry> set = map.entrySet();

            for(Map.Entry en : set) {
                parse(en.getKey());
                parse(en.getValue());
            }

        } else if(!isMappingJavaType(obj.getClass()) &&
                obj.getClass() != ClassLoader.class &&
                obj.getClass() != Class.class) {

            PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
            PropertyDescriptor[] propertyDescriptors = propertyUtilsBean.getPropertyDescriptors(obj.getClass());

            for (PropertyDescriptor p : propertyDescriptors) {
                try {
                    String propertyName = p.getName();
                    Object value =  propertyUtilsBean.getProperty(obj, propertyName);
                    if (p.getPropertyType() == Dict.class) { //符合转换条件
                        Dict _word = (Dict)value;
                        PropertyUtils.setProperty(obj, p.getName(), parse(_word.getCategory(), _word.getName()));
                    } else {
                        parse(value);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("字典转换失败！");
                }

            }

        }

    }

    private static boolean isMappingJavaType(Class<?> type) {
        return type == Boolean.class || type == Character.class || type == Byte.class || type == Short.class
                || type == Integer.class || type == Long.class || type == Float.class || type == Double.class
                || type == Date.class || type == java.sql.Date.class || type == String.class || type == BigInteger.class || type == BigDecimal.class
                || type == Byte[].class || type == Blob.class || type == Clob.class || type == Timestamp.class
                || type.isEnum();
    }

    @Transactional
    public static Dict idConvert2Dict(Long id) {
        if (Objects.isNull(id)) return null;

        DictService dictService = applicationContext.getBean(DictService.class);
        Dict dict = dictService.findById(id);

        return dict;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        DictionaryUtils.applicationContext = applicationContext;
    }
}

