package com.reljicd.service;

import com.reljicd.model.Role;
import com.reljicd.model.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User save(User user);

    Page<User> findAllOrderedByIdPageable(int page);

    Optional<User> findForId(Long id);

    Role findByRole(String role);
}
