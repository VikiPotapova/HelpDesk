package com.potapova.helpdesk.repository;

import com.potapova.helpdesk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
