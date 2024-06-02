package com.api.todolist.mapper;

import com.api.todolist.domain.Task;
import com.api.todolist.dto.TaskPostRequestBody;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public static Task toEntityTask(TaskPostRequestBody dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCategory(dto.getCategory());
        task.setDeadline(dto.getDeadline());
        return task;
    }
}