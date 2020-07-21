package com.fenglin.docman.controller;

import com.fenglin.docman.model.User;
import com.fenglin.docman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/search")
    @ResponseBody
    public Map search(@RequestParam(name = "username", defaultValue = "") String username,
                      @RequestParam(name = "name", defaultValue = "") String name,
                      @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                      @RequestParam(name = "offset", defaultValue = "1") Integer offset) {

        Pageable pageable = PageRequest.of(offset, limit);

        Page<User> page = userService.findAll(username, name, pageable);

        Map map = new HashMap();

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map save(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userService.save(user);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Map remove(@RequestParam Long id) {
        return userService.delete(id);
    }

    @RequestMapping("/get")
    @ResponseBody
    public User get(@RequestParam String username) {
        return userService.findByUsername(username);
    }

}
