package com.divyansh.prioriti.service.impl;

import com.divyansh.prioriti.dto.Response;
import com.divyansh.prioriti.dto.TaskRequest;
import com.divyansh.prioriti.entity.Task;
import com.divyansh.prioriti.entity.User;
import com.divyansh.prioriti.enums.Priority;
import com.divyansh.prioriti.exceptions.NotFoundException;
import com.divyansh.prioriti.repository.TaskRepository;
import com.divyansh.prioriti.service.TaskService;
import com.divyansh.prioriti.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public Response<Task> createTask(TaskRequest taskRequest) {
        log.info("Inside createTask");

        User user = userService.getCurrentLoggedInUser();

        Task taskToSave = new Task();
        taskToSave.setCreatedAt(LocalDate.now());
        taskToSave.setUpdatedAt(LocalDate.now());
        taskToSave.setTitle(taskRequest.getTitle());
        taskToSave.setCompleted(false);
        taskToSave.setPriority(taskRequest.getPriority());
        taskToSave.setDescription(taskRequest.getDescription());
        taskToSave.setDueDate(taskRequest.getDueDate());
        taskToSave.setUser(user);

        Task savedTask = taskRepository.save(taskToSave);

        return Response.<Task>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task Created Successfully")
                .data(savedTask)
                .build();
    }

    @Override
    @Transactional
    public Response<List<Task>> getAllTasks() {
        log.info("Inside getAllTasks");

        User currentLoggedInUser = userService.getCurrentLoggedInUser();
        List<Task> tasks = taskRepository.findByUser(currentLoggedInUser, Sort.by(Sort.Direction.ASC,
                "id"));

        return Response.<List<Task>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tasks fetched successfully")
                .data(tasks)
                .build();
    }

    @Override
    public Response<Task> getTaskById(Long taskId) {
        log.info("Inside getTaskById");

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));

        return Response.<Task>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task fetched successfully")
                .data(task)
                .build();
    }

    @Override
    public Response<Task> updateTask(TaskRequest taskRequest) {
        log.info("Inside updateTask");

        Task task = taskRepository.findById(taskRequest.getId()).orElseThrow(() -> new NotFoundException("Task not found"));

        if (taskRequest.getTitle() != null) task.setTitle(taskRequest.getTitle());
        if (taskRequest.getDescription() != null) task.setDescription(taskRequest.getDescription());
        if (taskRequest.getDueDate() != null) task.setDueDate(taskRequest.getDueDate());
        if (taskRequest.getPriority() != null) task.setPriority(taskRequest.getPriority());
        if(taskRequest.getCompleted() != null) task.setCompleted(taskRequest.getCompleted());
        task.setUpdatedAt(LocalDate.now());

        // update the task
        Task updatedTask =  taskRepository.save(task);

        return Response.<Task>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task fetched successfully")
                .data(updatedTask)
                .build();
    }

    @Override
    public Response<Void> deleteTask(Long taskId) {
        log.info("Inside deleteTask");

        taskRepository.deleteById(taskId);

        return Response.<Void>builder()
                .message("Task deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    @Transactional
    public Response<List<Task>> getTasksByCompletionStatus(boolean completed) {
        log.info("Inside getTasksByCompletionStatus");

        User user = userService.getCurrentLoggedInUser();

        List<Task> tasks = taskRepository.findByCompletedAndUser(completed, user);

        return Response.<List<Task>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task fetched successfully for completion status")
                .data(tasks)
                .build();

    }

    @Override
    @Transactional
    public Response<List<Task>> getTasksByPriority(String priority) {
        log.info("Inside getTasksByPriority");

        User user = userService.getCurrentLoggedInUser();
        Priority priorityEnum = Priority.valueOf(priority.toUpperCase());

        List<Task> tasks = taskRepository.findByUserAndPriority(user, priorityEnum,
                Sort.by(Sort.Direction.DESC, "id"));

        return Response.<List<Task>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task fetched successfully for priority")
                .data(tasks)
                .build();
    }
}
