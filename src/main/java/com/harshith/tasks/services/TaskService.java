package com.harshith.tasks.services;

import com.harshith.tasks.entites.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    List<Task> listTasks(UUID taskListId);

    Task createTask(UUID task_list_id,Task task);

    Optional<Task> getTask(UUID task_list_id,UUID task_id);

    Task updateTask(UUID task_list_id,UUID task_id,Task task);

    void  deleteTask(UUID task_list_id,UUID task_id);
}
