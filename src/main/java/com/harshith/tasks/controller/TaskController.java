package com.harshith.tasks.controller;


import com.harshith.tasks.dto.TaskDto;
import com.harshith.tasks.entites.Task;
import com.harshith.tasks.mappers.TaskMapper;
import com.harshith.tasks.repositories.TaskRepo;
import com.harshith.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService  taskService;
    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskRepo taskRepo, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskRepo = taskRepo;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {

        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();

    }

    @PostMapping
    public  TaskDto createTask(@PathVariable("task_list_id") UUID task_list_id,
                               @RequestBody TaskDto taskDto) {

        Task taskCreated  = taskService.createTask(task_list_id,taskMapper.fromDto(taskDto));

        return taskMapper.toDto(taskCreated);
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID task_list_id, @PathVariable("task_id") UUID task_id) {
        return  taskService.getTask(task_list_id,task_id).map(taskMapper::toDto);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updatedTask(@PathVariable("task_list_id") UUID task_list_id,
                               @PathVariable("task_id") UUID task_id ,
                               @RequestBody TaskDto taskDto){

        Task updatedTask = taskService.updateTask(task_list_id,task_id,taskMapper.fromDto(taskDto));

        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteTask(@PathVariable("task_list_id") UUID task_list_id,
                           @PathVariable("task_id") UUID task_id ){

        taskService.deleteTask(task_list_id,task_id);
    }


}
