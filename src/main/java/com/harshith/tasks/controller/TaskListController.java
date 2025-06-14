package com.harshith.tasks.controller;


import com.harshith.tasks.dto.TaskListDto;
import com.harshith.tasks.entites.TaskList;
import com.harshith.tasks.mappers.TaskListMapper;
import com.harshith.tasks.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping(path = "/api/task-lists")
public class TaskListController {

    private  final TaskListService  taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists(){

            return taskListService.listTaskList()
                    .stream()
                    .map(taskListMapper::toDto)
                    .toList();
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){
        TaskList createdTaskList =taskListService.createTaskList(taskListMapper.fromDto(taskListDto));

        return taskListMapper.toDto(createdTaskList);
    }

    @GetMapping(path="/{task_list_id}")
    public Optional<TaskListDto> getTaskListById(@PathVariable("task_list_id") UUID task_list_id){
        return taskListService.getTaskList(task_list_id).map(taskListMapper::toDto);

    }

    @PutMapping(path="/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID task_list_id,@RequestBody TaskListDto taskListDto){

        TaskList updatedTaskList = taskListService.updateTaskList(
                                            task_list_id,
                                            taskListMapper.fromDto(taskListDto)
        );

        return taskListMapper.toDto(updatedTaskList);
    }

    @DeleteMapping(path ="/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID task_list_id){

        taskListService.deleteTaskList(task_list_id);
    }

}
