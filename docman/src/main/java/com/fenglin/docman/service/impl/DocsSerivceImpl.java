package com.fenglin.docman.service.impl;

import com.fenglin.docman.model.Document;
import com.fenglin.docman.repository.DocsRepository;
import com.fenglin.docman.service.DocsSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("docsService")
public class DocsSerivceImpl implements DocsSerivce {

    @Autowired
    private DocsRepository docsRepository;

    @Override
    public Page<Document> findAll(String keyword, Pageable pageable) {
        return docsRepository.findAll(keyword, pageable);
    }

    @Override
    public Page<Document> findAllPublic(String keyword, Pageable pageable) {
        return docsRepository.findAllPublic(keyword, pageable);
    }

    @Override
    public Map save(Document document) {
        Map map = new HashMap();

        Document _document = docsRepository.save(document);

        if (_document == null) {
            map.put("status", "failed");
        } else {
            map.put("status", "succeed");
            map.put("document", _document);
        }

        return map;
    }

    @Override
    public Map delete(Long id) {
        Map map = new HashMap();

        int cnt = docsRepository.deleteOne(id);

        if (cnt == 0) {
            map.put("status", "failed");
        } else {
            map.put("status", "succeed");
        }

        return map;
    }

    @Override
    public Document findDocumentById(Long id) {
        return docsRepository.findDocumentById(id);
    }
}
