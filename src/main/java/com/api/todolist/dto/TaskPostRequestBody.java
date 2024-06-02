package com.api.todolist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TaskPostRequestBody {

    @NotBlank(message = "The title cannot be empty")
    private String title;
    private String description;
    private String category;

    @NotNull(message = "The deadline cannot be null")
    @Future(message = "The deadline must be in the future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate deadline;

    public TaskPostRequestBody() {}

    public TaskPostRequestBody(String title, String description, String category, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}