package com.yodean.oa.common.dict.service;

import com.google.common.collect.Maps;
import com.rick.db.service.JdbcService;
import com.yodean.dictionary.entity.Dict;
import com.yodean.dictionary.service.DictService;
import com.yodean.oa.common.dict.repository.DictRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rick on 7/17/18.
 */
@Service
public class DictServiceImpl implements DictService {

    private static final String QUERY_SQL = "select category, name, description, remark from sys_dictionary where category = :category and name = :name";

    private static final String QUERY_ID_SQL = "select id, category, name, description from sys_dictionary where id = :id";

    @Resource
    private JdbcService sharpService;

    @Autowired
    private DictRepository dictRepository;

    public Dict save(Dict Dict) {
        return dictRepository.save(Dict);
    }

    public void delete(Long id) {
        dictRepository.deleteById(id);
    }

    public List<Dict> findByCategory(String category) {
        return dictRepository.findByCategoryOrderBySeqAsc(category);
    }

    public Dict findByCategoryAndName(String category, String name) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("category", category);
        params.put("name", name);
        List<Dict> list = sharpService.query(QUERY_SQL, params, Dict.class);
        if (CollectionUtils.isEmpty(list)) return null;


        return list.get(0);
    }

    @Override
    public Dict findById(Long id) {
//        return dictRepository.getOne(id);
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put("id", id);
        List<Dict> list = sharpService.query(QUERY_ID_SQL, params, Dict.class);
        if (CollectionUtils.isEmpty(list))
            return null;

        return list.get(0);
    }

}
