package com.divyansh.prioriti.dto;

import com.divyansh.prioriti.enums.Priority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskRequest {

    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @Size(max = 500, message = "Title must be less than 500 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Completed status is required")
    private Boolean completed;

    @FutureOrPresent(message = "Due date must be today or in future")
    private LocalDate dueDate;

}
