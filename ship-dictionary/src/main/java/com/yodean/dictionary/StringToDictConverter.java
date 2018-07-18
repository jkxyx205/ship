package com.yodean.dictionary;


import com.yodean.dictionary.entity.Dict;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by rick on 7/17/18.
 */
public class StringToDictConverter implements Converter<Long, Dict> {
    @Override
    public Dict convert(Long id) {
        return DictionaryUtils.idConvert2Dict(id);
    }
}
