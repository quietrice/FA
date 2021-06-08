package com.mark.pr0j3c7.Controllers;

import com.mark.pr0j3c7.Entities.Status;
import com.mark.pr0j3c7.Services.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StatusController {

    private final StatusService STATUS_SERVICE;

    @Autowired
    public StatusController(StatusService STATUS_SERVICE) {
        this.STATUS_SERVICE = STATUS_SERVICE;
    }

    @PostMapping(value="/api/status")
    private ResponseEntity<?> create(@RequestBody Status status){
        Status newStatus = STATUS_SERVICE.create(status);
        return new ResponseEntity<>(newStatus, HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/status")
    private ResponseEntity<List<Status>> readAll(){
        final List<Status> STATUS_LIST = STATUS_SERVICE.findAll();

        return STATUS_LIST != null && !STATUS_LIST.isEmpty()
                ? new ResponseEntity<>(STATUS_LIST, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "api/status/{id}")
    public ResponseEntity<Status> getOne(@PathVariable(name = "id") Status status) {
        final Status CURRENT_STATUS = status;

        return status != null
                ? new ResponseEntity<>(CURRENT_STATUS, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/status/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") Status statusFromDB,
                                 @RequestBody Status status) {
        Status updatedStatus = STATUS_SERVICE.update(status, statusFromDB);
        if (updatedStatus != null) {
            return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "api/status/{id}")
    public ResponseEntity<List<Status>> delete(@PathVariable(name = "id") Status status) {
        if (STATUS_SERVICE.delete(status)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

