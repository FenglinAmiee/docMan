package com.fenglin.docman.repository;

import com.fenglin.docman.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);

    @Query("select u from User u " +
            "where (u.username is null or u.username like %?1% ) " +
            "and  (u.name is null or u.name like %?2% ) " +
            "and u.username <> 'admin'" +
            "order by u.id desc")
    Page<User> findAll( String username, String name,Pageable pageable);

    @Query("delete from User u where u.id = ?1")
    @Transactional
    @Modifying
    int deleteOne(Long id);
}
