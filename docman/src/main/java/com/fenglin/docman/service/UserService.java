package com.fenglin.docman.service;

import com.fenglin.docman.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserService {
    User findByUsername(String username);

    Page<User> findAll(String username, String name, Pageable pageable);

    Map save(User user);

    Map delete(Long id);
}
