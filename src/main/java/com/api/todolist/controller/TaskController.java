package com.api.todolist.controller;

import com.api.todolist.domain.Task;
import com.api.todolist.dto.TaskPostRequestBody;
import com.api.todolist.dto.TaskPutRequestBody;
import com.api.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks", produces = {"application/json"})
@Tag(name = "todolist-api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Retorna todas as tarefas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas retornadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Retorna uma lista paginada de todas as tarefas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/list")
    public ResponseEntity<List<Task>> listAll(Pageable pageable) {
        return new ResponseEntity<>(taskService.listAll(pageable).getContent(), HttpStatus.OK);
    }

    @Operation(summary = "Retorna uma tarefa pelo ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getByIdOrThrowNotFoundException(id), HttpStatus.OK);
    }

    @Operation(summary = "Retorna tarefas pelo título", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/find/by-title")
    public ResponseEntity<List<Task>> getByTitle(@RequestParam String title) {
        return new ResponseEntity<>(taskService.getByTitle(title), HttpStatus.OK);
    }

    @Operation(summary = "Retorna tarefas pela categoria", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/find/by-category")
    public ResponseEntity<List<Task>> getByCategory(@RequestParam String category) {
        return new ResponseEntity<>(taskService.getByCategory(category), HttpStatus.OK);
    }

    @Operation(summary = "Retorna tarefas pelo prazo", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/find/by-deadline")
    public ResponseEntity<List<Task>> getByDeadline(@RequestParam("deadline") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate deadline) {
        return new ResponseEntity<>(taskService.getByDeadline(deadline), HttpStatus.OK);
    }

    @Operation(summary = "Cria uma nova tarefa", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid TaskPostRequestBody dto) {
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza uma tarefa existente", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid TaskPutRequestBody dto) {
        taskService.updateTask(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Exclui uma tarefa pelo ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}