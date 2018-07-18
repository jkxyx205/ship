package com.yodean.dictionary.service;

import com.yodean.dictionary.entity.Dict;

import java.util.List;

/**
 * Created by rick on 7/17/18.
 */

public interface DictService {

    Dict save(Dict Dict);

    void delete(Long id);

    List<Dict> findByCategory(String category);

    Dict findByCategoryAndName(String category, String name);

    Dict findById(Long id);
}
