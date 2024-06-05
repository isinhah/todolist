package com.api.todolist.controller;

import com.api.todolist.domain.Task;
import com.api.todolist.dto.TaskPostRequestBody;
import com.api.todolist.dto.TaskPutRequestBody;
import com.api.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all tasks when successful")
    void getAll_ReturnsTasks_WhenSuccessful() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getAll()).thenReturn(tasks);

        List<Task> result = taskController.getAll().getBody();

        verify(taskService, times(1)).getAll();
        assertEquals(tasks, result);
    }

    @Test
    @DisplayName("Should return a page of tasks when successful")
    void listAll_ReturnsTasks_WhenSuccessful() {
        Pageable pageable = mock(Pageable.class);

        when(taskService.listAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));

        taskController.listAll(pageable);

        verify(taskService, times(1)).listAll(pageable);
    }

    @Test
    @DisplayName("Should return a task by ID when successful")
    void getById_ReturnsTask_WhenSuccessful() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        when(taskService.getByIdOrThrowNotFoundException(taskId)).thenReturn(task);

        ResponseEntity<Task> response = taskController.getById(taskId);

        verify(taskService, times(1)).getByIdOrThrowNotFoundException(taskId);

        assertEquals(task, response.getBody());
    }

    @Test
    @DisplayName("Should return a task by Title when successful")
    void getByTitle_ReturnsTask_WhenSuccessful() {
        String title = "Title";
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        when(taskService.getByTitle(title)).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getByTitle(title);

        verify(taskService, times(1)).getByTitle(title);

        assertEquals(tasks, response.getBody());
    }

    @Test
    @DisplayName("Should return a task by Category when successful")
    void getByCategory_ReturnsTask_WhenSuccessful() {
        String category = "Category";
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        when(taskService.getByCategory(category)).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getByCategory(category);

        verify(taskService, times(1)).getByCategory(category);

        assertEquals(tasks, response.getBody());
    }

    @Test
    @DisplayName("Should return a task by Deadline when successful")
    void getByDeadline_ReturnsTask_WhenSuccessful() {
        LocalDate deadline = LocalDate.now().plusDays(7);
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        when(taskService.getByDeadline(deadline)).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getByDeadline(deadline);

        verify(taskService, times(1)).getByDeadline(deadline);

        assertEquals(tasks, response.getBody());
    }

    @Test
    @DisplayName("Should create a task when successful")
    void create_SaveATask_WhenSuccessful() {
        TaskPostRequestBody dto = new TaskPostRequestBody("Title", "Description", "Category", LocalDate.now().plusDays(7));

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle(dto.getTitle());
        savedTask.setDescription(dto.getDescription());
        savedTask.setCategory(dto.getCategory());

        when(taskService.createTask(dto)).thenReturn(savedTask);

        ResponseEntity<Task> response = taskController.create(dto);

        verify(taskService, times(1)).createTask(dto);

        assertEquals(savedTask, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should update a task when successful")
    void update_ReplaceATask_WhenSuccessful() {
        TaskPutRequestBody dto = new TaskPutRequestBody();

        dto.setId(1L);
        dto.setTitle("Title");
        dto.setDescription("Description");
        dto.setCategory("Category");

        ResponseEntity<Void> response = taskController.update(dto);

        verify(taskService, times(1)).updateTask(dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete a movie by ID when successful")
    void delete() {
        Long taskId = 1L;

        ResponseEntity<Void> response = taskController.delete(taskId);

        verify(taskService, times(1)).deleteTask(taskId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}