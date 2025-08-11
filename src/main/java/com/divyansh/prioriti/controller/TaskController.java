package com.divyansh.prioriti.controller;

import com.divyansh.prioriti.dto.Response;
import com.divyansh.prioriti.dto.TaskRequest;
import com.divyansh.prioriti.entity.Task;
import com.divyansh.prioriti.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Response<?>> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.createTask(taskRequest));
    }

    @PutMapping
    public ResponseEntity<Response<?>> updateTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.updateTask(taskRequest));
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @GetMapping("/status")
    public ResponseEntity<Response<?>> getTasksByCompletionStatus(@RequestParam boolean completed) {
        return ResponseEntity.ok(taskService.getTasksByCompletionStatus(completed));
    }

    @GetMapping("/priority")
    public ResponseEntity<Response<?>> getTasksByPriority(@RequestParam String priority) {
        return ResponseEntity.ok(taskService.getTasksByPriority(priority));
    }
}
