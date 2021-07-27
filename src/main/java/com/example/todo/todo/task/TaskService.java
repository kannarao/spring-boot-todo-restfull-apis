package com.example.todo.todo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getActiveTasks() {
        return taskRepository.findAllByStatus(true);
    }

    public Task closeTask(Task task) {
        task.setStatus(false);
        taskRepository.save(task);
        return task;
    }
}
