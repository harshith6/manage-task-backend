package com.harshith.tasks.mappers;

import com.harshith.tasks.dto.TaskDto;
import com.harshith.tasks.entites.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
