package com.boblob.blog.repo;

import com.boblob.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ADivaev on 11.11.2020.
 */
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
