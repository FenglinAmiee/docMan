package com.fenglin.docman.service;

import com.fenglin.docman.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface DocsSerivce {

    Page<Document> findAll(String keyword,Pageable pageable);

    Page<Document> findAllPublic(String keyword, Pageable pageable);

    Map save(Document document);

    Map delete(Long id);

    Document findDocumentById(Long id);
}
