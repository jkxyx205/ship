package com.yodean.dictionary;


import com.yodean.dictionary.entity.Dict;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.Objects;

/**
 * Created by rick on 7/17/18.
 */
@Convert
public class DictJpaConverter implements AttributeConverter<Dict, Long> {


    @Override
    public Long convertToDatabaseColumn(Dict dict) {
        if (Objects.isNull(dict)) return null;

        return dict.getId();
    }

    @Override
    public Dict convertToEntityAttribute(Long id) {
        return DictionaryUtils.idConvert2Dict(id);
    }
}
