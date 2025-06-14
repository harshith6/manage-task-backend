package com.harshith.tasks.services;

import com.harshith.tasks.entites.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {

    List<TaskList> listTaskList();

    TaskList createTaskList(TaskList taskList);

    Optional<TaskList> getTaskList(UUID id);

    TaskList updateTaskList(UUID id, TaskList taskList);

    void  deleteTaskList(UUID taskListId);
}
