package com.mark.pr0j3c7.Controllers;

import com.mark.pr0j3c7.Entities.Task;
import com.mark.pr0j3c7.Services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TaskController {

    private final TaskService TASK_SERVICE;

    @Autowired
    public TaskController(TaskService ts) {
        this.TASK_SERVICE = ts;
    }

    @PostMapping(value="/api/task")
    private ResponseEntity<?> create(@RequestBody Task task){
        Task task = TASK_SERVICE.create(task);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/task")
    private ResponseEntity<List<Task>> readAll(){
        final List<Task> TASK_LIST = TASK_SERVICE.findAll();

        return TASK_LIST != null && !TASK_LIST.isEmpty()
                ? new ResponseEntity<>(TASK_LIST, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "api/task/{id}")
    public ResponseEntity<Task> getOne(@PathVariable(name = "id") Task task) {
        final Task CURRENT_TASK = task;

        return task != null
                ? new ResponseEntity<>(CURRENT_TASK, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/task/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") Task taskFromDB,
                                 @RequestBody Task task) {
        Task updatedTask = TASK_SERVICE.update(task, taskFromDB);

        if (updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
