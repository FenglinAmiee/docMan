package com.fenglin.docman.service.impl;

import com.fenglin.docman.model.User;
import com.fenglin.docman.repository.UserRepository;
import com.fenglin.docman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAll(String username, String name,Pageable pageable) {
        return userRepository.findAll(username ,name,pageable);
    }

    @Override
    public Map save(User user) {
        Map map = new HashMap();

        User _user = userRepository.save(user);

        if (_user == null) {
            map.put("status", "failed");
        } else {
            map.put("status", "succeed");
            map.put("user", _user);
        }

        return map;
    }

    @Override
    public Map delete(Long id) {
        Map map = new HashMap();

        int cnt = userRepository.deleteOne(id);

        if (cnt == 0) {
            map.put("status", "failed");
        } else {
            map.put("status", "succeed");
        }

        return map;
    }
}
