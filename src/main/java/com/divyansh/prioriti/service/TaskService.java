package com.divyansh.prioriti.service;


import com.divyansh.prioriti.dto.Response;
import com.divyansh.prioriti.dto.TaskRequest;
import com.divyansh.prioriti.entity.Task;
import com.divyansh.prioriti.enums.Priority;

import java.util.List;

public interface TaskService {

    Response<Task> createTask(TaskRequest taskRequest);
    Response<List<Task>> getAllTasks();
    Response<Task> getTaskById(Long taskId);
    Response<Task> updateTask(TaskRequest taskRequest);
    Response<Void> deleteTask(Long taskId);
    Response<List<Task>> getTasksByCompletionStatus(boolean completed);
    Response<List<Task>> getTasksByPriority(String priority);

}
