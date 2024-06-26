package com.api.todolist.repository;

import com.api.todolist.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitleContainingIgnoreCase(String title);
    List<Task> findByCategoryContainingIgnoreCase(String category);
    List<Task> findByDeadline(LocalDate deadline);
}