package com.mark.pr0j3c7.Repositories;

import com.mark.pr0j3c7.Entities.TaskStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
}