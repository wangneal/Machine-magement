package com.bcfou.repository;

import com.bcfou.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 编程否
 * @Date: 2018/8/26 14:27
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
