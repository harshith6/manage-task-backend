package com.harshith.tasks.dto;

import com.harshith.tasks.entites.TaskPriority;
import com.harshith.tasks.entites.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(

        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {


}
