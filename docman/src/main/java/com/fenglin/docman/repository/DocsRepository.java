package com.fenglin.docman.repository;

import com.fenglin.docman.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DocsRepository extends JpaRepository<Document, Long> {

    @Query("select d from Document d where d.keyword like %?1% order by d.id desc")
    Page<Document> findAll(String keyword, Pageable pageable);

    @Query("select d from Document d where d.keyword like %?1% and d.level = 0 order by d.id desc")
    Page<Document> findAllPublic(String keyword,Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Document docs where docs.id = ?1")
    int deleteOne(Long id);

    @Query("select d from Document d where d.id = ?1")
    Document findDocumentById(Long id);
}
