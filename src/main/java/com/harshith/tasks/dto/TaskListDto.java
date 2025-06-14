package com.harshith.tasks.dto;

import com.harshith.tasks.entites.Task;

import java.util.List;
import java.util.UUID;

public record TaskListDto(

        UUID id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks


) {
}
