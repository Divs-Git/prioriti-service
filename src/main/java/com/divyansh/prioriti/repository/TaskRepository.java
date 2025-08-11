package com.divyansh.prioriti.repository;

import com.divyansh.prioriti.entity.Task;
import com.divyansh.prioriti.entity.User;
import com.divyansh.prioriti.enums.Priority;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user, Sort sort);
    List<Task> findByCompletedAndUser(Boolean completed, User user);
    List<Task> findByUserAndPriority(User user,Priority priority,Sort sort);
}
