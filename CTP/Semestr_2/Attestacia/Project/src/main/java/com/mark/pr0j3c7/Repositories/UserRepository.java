package com.mark.pr0j3c7.Repositories;

import com.mark.pr0j3c7.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
