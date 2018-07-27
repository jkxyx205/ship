package com.yodean.oa.common.plugin.document.service;

import com.yodean.oa.common.plugin.document.entity.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by rick on 7/25/18.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;


    @Test
    public void findDocumentPath() throws Exception {
        List<Document> path = documentService.findParentsDocuments2(153250938699373L);
        path.forEach(document -> {
            System.out.println(document.getName());
        });
    }

}