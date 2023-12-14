package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.User;

public interface UserService {
    User getUserById(Long id);

    User getUserByLogin(String login);

    User getCurrentUser();
}