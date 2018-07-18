package com.yodean.oa.common.plugin.document.dao;


import com.yodean.oa.common.plugin.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 2018/3/22.
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
