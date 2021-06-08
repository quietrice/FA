package com.mark.pr0j3c7.Repositories;

import com.mark.pr0j3c7.Entities.Task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
