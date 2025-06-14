package com.harshith.tasks.services.impl;

import com.harshith.tasks.entites.Task;
import com.harshith.tasks.entites.TaskList;
import com.harshith.tasks.entites.TaskPriority;
import com.harshith.tasks.entites.TaskStatus;
import com.harshith.tasks.repositories.TaskListRepo;
import com.harshith.tasks.repositories.TaskRepo;
import com.harshith.tasks.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private  final TaskRepo  taskRepo;
    private  final TaskListRepo  taskListRepo;
    public TaskServiceImpl(TaskRepo taskRepo, TaskListRepo taskListRepo) {
        this.taskRepo = taskRepo;
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<Task> listTasks(UUID task_list_id) {
        return taskRepo.findByTaskListId(task_list_id);
    }

    @Override
    public Task createTask(UUID task_list_id,Task task) {

        if(null != task.getId()){
            throw new IllegalArgumentException("Id alreday exist!");
        }
        if(null == task.getTitle() || task.getTitle().isBlank()){
            throw new IllegalArgumentException("title must be present!");
        }
        TaskPriority taskPriority= Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList=taskListRepo.findById(task_list_id).orElseThrow(()->new IllegalArgumentException("task list id not found!"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );

        return taskRepo.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID task_list_id, UUID task_id) {
        return taskRepo.findByTaskListIdAndId(task_list_id,task_id);
    }

    @Transactional
    @Override
    public Task updateTask(UUID task_list_id, UUID task_id, Task task) {
        if(null == task.getId()){
            throw new IllegalArgumentException("id alreday exist!");
        }
        if(!task.getId().equals(task_id)){
            throw new IllegalArgumentException("Update not permitted!");
        }

        if(null == task.getPriority()){
            throw new IllegalArgumentException("priority must be present!");
        }
        if(null == task.getStatus()){
            throw new IllegalArgumentException("status must be present!");
        }

        Task existingTask = taskRepo.findByTaskListIdAndId(task_list_id,task_id).orElseThrow(()->new IllegalArgumentException("task list id not found!"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdatedDate(LocalDateTime.now());

        return taskRepo.save(existingTask);

    }

    @Transactional
    @Override
    public void deleteTask(UUID task_list_id, UUID task_id) {
         taskRepo.deleteByTaskListIdAndId(task_list_id,task_id);
    }
}
