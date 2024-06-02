package com.api.todolist.service;

import com.api.todolist.domain.Task;
import com.api.todolist.dto.TaskPostRequestBody;
import com.api.todolist.mapper.TaskMapper;
import com.api.todolist.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task createTask(TaskPostRequestBody dto) {
        return taskRepository.save(TaskMapper.toEntityTask(dto));
    }
}