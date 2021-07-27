package com.example.todo.todo.task;

import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getActiveTasks();
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        task.setId(UUID.randomUUID());
        return taskRepository.save(task);
    }

    @GetMapping("/tasks/{id}")
    public Optional<Task> getTaskById(@PathVariable(value="id") UUID id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        return task;
    }

    @PutMapping("tasks/{id}/close")
    public Task updateTaskById(@PathVariable(value="id") UUID id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        Task closeTask = task.get();
        if (!closeTask.getStatus()) {
            throw new TaskBadRequestException("Task already closed");
        }
        return taskService.closeTask(task.get());
    }
}
