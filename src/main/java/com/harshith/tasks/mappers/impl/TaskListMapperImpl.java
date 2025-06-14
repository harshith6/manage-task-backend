package com.harshith.tasks.mappers.impl;

import com.harshith.tasks.dto.TaskListDto;
import com.harshith.tasks.entites.Task;
import com.harshith.tasks.entites.TaskList;
import com.harshith.tasks.entites.TaskStatus;
import com.harshith.tasks.mappers.TaskListMapper;
import com.harshith.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }
    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList()).orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(task -> task.stream()
                                .map(taskMapper::toDto).toList())
                        .orElse(null)

        );
    }

    private Double calculateTaskListProgress(List<Task> tasks) {

        if(tasks==null || tasks.isEmpty()) {
            return 0.0;
        }
        long closedTasks = tasks.stream().filter(task ->
                TaskStatus.CLOSED == task.getStatus()).count();

        return closedTasks / (double)tasks.size();
    }
}
