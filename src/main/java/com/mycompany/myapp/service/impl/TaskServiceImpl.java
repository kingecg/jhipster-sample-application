package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.repository.TaskRepository;
import com.mycompany.myapp.service.TaskService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Task}.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> partialUpdate(Task task) {
        log.debug("Request to partially update Task : {}", task);

        return taskRepository
            .findById(task.getId())
            .map(existingTask -> {
                if (task.getTitle() != null) {
                    existingTask.setTitle(task.getTitle());
                }
                if (task.getDescription() != null) {
                    existingTask.setDescription(task.getDescription());
                }

                return existingTask;
            })
            .map(taskRepository::save);
    }

    @Override
    public List<Task> findAll() {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findOne(String id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }
}
