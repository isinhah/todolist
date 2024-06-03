package com.api.todolist.controller;

import com.api.todolist.domain.Task;
import com.api.todolist.dto.TaskPostRequestBody;
import com.api.todolist.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getByIdOrThrowNotFoundException(id), HttpStatus.OK);
    }

    @GetMapping("/find/by-title")
    public ResponseEntity<List<Task>> getByTitle(@RequestParam String title) {
        return new ResponseEntity<>(taskService.getByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/find/by-category")
    public ResponseEntity<List<Task>> getByCategory(@RequestParam String category) {
        return new ResponseEntity<>(taskService.getByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/find/by-deadline")
    public ResponseEntity<List<Task>> getByDeadline(@RequestParam("deadline") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate deadline) {
        return new ResponseEntity<>(taskService.getByDeadline(deadline), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid TaskPostRequestBody dto) {
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.CREATED);
    }
}