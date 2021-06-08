package com.mark.pr0j3c7.Controllers;

import com.mark.pr0j3c7.Entities.TaskStatus;
import com.mark.pr0j3c7.Services.TaskStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskStatusController {

    private final TaskStatusService TASK_STATUS_SERVICE;

    @Autowired
    public TaskStatusController(TaskStatusService TASK_STATUS_SERVICE) {
        this.TASK_STATUS_SERVICE = TASK_STATUS_SERVICE;
    }

    @PostMapping(value="/api/task_status")
    private ResponseEntity<?> create(@RequestBody TaskStatus taskStatus){
        TaskStatus newTaskStatus = TASK_STATUS_SERVICE.create(taskStatus);

        return new ResponseEntity<>(newTaskStatus, HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/task_status")
    private ResponseEntity<List<TaskStatus>> readAll(){
        final List<TaskStatus> TASK_STATUS_LIST = TASK_STATUS_SERVICE.findAll();

        return TASK_STATUS_LIST != null && !TASK_STATUS_LIST.isEmpty()
                ? new ResponseEntity<>(TASK_STATUS_LIST, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "api/task_status/{id}")
    public ResponseEntity<TaskStatus> getOne(@PathVariable(name = "id") TaskStatus taskStatus) {
        final TaskStatus CURRENT_TASK_STATUS = taskStatus;

        return taskStatus != null
                ? new ResponseEntity<>(CURRENT_TASK_STATUS, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/task_status/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") TaskStatus taskStatusFromDB,
                                 @RequestBody TaskStatus taskStatus) {
        TaskStatus updatedTaskStatus = TASK_STATUS_SERVICE.update(taskStatus, taskStatusFromDB);

        if (updatedTaskStatus != null) {
            return new ResponseEntity<>(updatedTaskStatus, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "api/task_status/{id}")
    public ResponseEntity<List<TaskStatus>> delete(@PathVariable(name = "id") TaskStatus taskStatus) {
        if (TASK_STATUS_SERVICE.delete(taskStatus)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

