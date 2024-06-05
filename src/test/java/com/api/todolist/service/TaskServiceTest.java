package com.api.todolist.service;

import com.api.todolist.domain.Task;
import com.api.todolist.dto.TaskPostRequestBody;
import com.api.todolist.dto.TaskPutRequestBody;
import com.api.todolist.handler.exceptions.ResourceNotFoundException;
import com.api.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all tasks when successful")
    void getAll_ReturnsListOfTasks_WhenSuccessful() {
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Should return list of tasks inside page object when successful")
    void listAll_ReturnsListOfTasksInsidePageObject_WhenSuccessful() {
        Page<Task> taskPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(taskRepository.findAll(pageable)).thenReturn(taskPage);

        Page<Task> result = taskService.listAll(pageable);

        assertNotNull(result);
        assertEquals(taskPage, result);
    }

    @Test
    @DisplayName("Should return a task by ID or throw NotFoundException when successful")
    void getByIdOrThrowNotFoundException_ReturnsTask_WhenSuccessful() {
        Long id = 1L;
        Task task = new Task();

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        Task result = taskService.getByIdOrThrowNotFoundException(id);

        assertNotNull(result);
        assertEquals(task, result);
    }

    @Test
    @DisplayName("Should return a task by Title when successful")
    void getByTitle_ReturnsTaskByTitle_WhenSuccessful() {
        String title = "Estudar Java";
        when(taskRepository.findByTitleContainingIgnoreCase(title)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getByTitle(title));
    }

    @Test
    @DisplayName("Should return a task by Category when successful")
    void getByCategory_ReturnsTask_WhenSuccessful() {
        String category = "Estudo";
        when(taskRepository.findByCategoryContainingIgnoreCase(category)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getByCategory(category));
    }

    @Test
    @DisplayName("Should return a task by Deadline when successful")
    void getByDeadline_ReturnsTask_WhenSuccessful() {
        LocalDate deadline = LocalDate.now().plusDays(7);
        when(taskRepository.findByDeadline(deadline)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getByDeadline(deadline));
    }

    @Test
    @DisplayName("Should create a task when successful")
    void createTask_WhenSuccessful() {
        TaskPostRequestBody dto = mock(TaskPostRequestBody.class);
        Task task = mock(Task.class);

        when(taskRepository.save(any())).thenReturn(task);

        Task result = taskService.createTask(dto);

        assertNotNull(result);
        assertEquals(task, result);
    }

    @Test
    @DisplayName("Should update a task when successful")
    void updateTask_WhenSuccessful() {
        TaskPutRequestBody dto = mock(TaskPutRequestBody.class);

        when(dto.getId()).thenReturn(1L);
        when(dto.getTitle()).thenReturn("New Title");
        when(dto.getCategory()).thenReturn("New Category");
        when(dto.getDeadline()).thenReturn(LocalDate.now().plusDays(7));

        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        assertDoesNotThrow(() -> taskService.updateTask(dto));
    }

    @Test
    @DisplayName("Should delete a task By ID or throw NotFoundException")
    void deleteTask_WhenSuccessful() {
        Long id = 1L;
        Task task = mock(Task.class);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        assertDoesNotThrow(() -> taskService.deleteTask(id));
    }
}