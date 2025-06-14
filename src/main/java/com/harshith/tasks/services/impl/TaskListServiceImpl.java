package com.harshith.tasks.services.impl;

import com.harshith.tasks.entites.TaskList;
import com.harshith.tasks.repositories.TaskListRepo;
import com.harshith.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepo taskListRepo;
    public TaskListServiceImpl(TaskListRepo taskListRepo) {
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<TaskList> listTaskList() {
        return taskListRepo.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null !=taskList.getId()){
            throw new IllegalArgumentException("Task list already has an ID!");
        }

        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title must be present!");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepo.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepo.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID task_list_id, TaskList taskList) {
        if(null == taskList.getId()){
            throw new IllegalArgumentException("Task list id must be present!");
        }
        if(!Objects.equals(taskList.getId(),task_list_id)){
            throw new IllegalArgumentException("Attamping to change tasklist id, that is not permitted!");
        }

        TaskList existingTaskList = taskListRepo.findById(task_list_id).orElseThrow(()-> new IllegalArgumentException("TaskList Not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdatedDate(LocalDateTime.now());

        return taskListRepo.save(existingTaskList);

    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepo.deleteById(taskListId);
    }
}
