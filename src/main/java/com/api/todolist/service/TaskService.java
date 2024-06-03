package com.api.todolist.service;

import com.api.todolist.domain.Task;
import com.api.todolist.dto.TaskPostRequestBody;
import com.api.todolist.exceptions.NotFoundException;
import com.api.todolist.mapper.TaskMapper;
import com.api.todolist.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task getByIdOrThrowNotFoundException(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with this id."));
    }

    public List<Task> getByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Task> getByCategory(String category) {
        return taskRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<Task> getByDeadline(LocalDate deadline) {
        return taskRepository.findByDeadline(deadline);
    }

    @Transactional
    public Task createTask(TaskPostRequestBody dto) {
        return taskRepository.save(TaskMapper.toEntityTask(dto));
    }
}
