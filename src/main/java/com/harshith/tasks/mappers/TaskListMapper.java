package com.harshith.tasks.mappers;

import com.harshith.tasks.dto.TaskListDto;
import com.harshith.tasks.entites.TaskList;

public interface TaskListMapper {

   TaskList fromDto(TaskListDto taskListDto);

   TaskListDto toDto(TaskList taskList);
}
