package com.example.multitenant.repository;

import com.example.multitenant.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
