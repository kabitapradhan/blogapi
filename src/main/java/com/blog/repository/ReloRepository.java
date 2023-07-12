package com.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Role;

public interface ReloRepository extends JpaRepository<Role, Integer> {

}
