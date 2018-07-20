package com.yodean.oa.common.dict.repository;


import com.yodean.dictionary.entity.Dict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by rick on 7/17/18.
 */
public interface DictRepository extends JpaRepository<Dict, Long> {
    List<Dict> findByCategoryOrderBySeqAsc(String category);
}
