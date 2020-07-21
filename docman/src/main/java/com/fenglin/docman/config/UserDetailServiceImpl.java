package com.fenglin.docman.config;

import com.fenglin.docman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service(value = "userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.fenglin.docman.model.User user = userService.findByUsername(username);

        return new User(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin"));
    }
}
