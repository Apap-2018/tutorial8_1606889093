package com.apap.tutorial6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tutorial6.model.UserRoleModel;

@Repository
public interface UserRoleDb extends JpaRepository<UserRoleModel, Long> {
	UserRoleModel findByUsername(String username);
}
