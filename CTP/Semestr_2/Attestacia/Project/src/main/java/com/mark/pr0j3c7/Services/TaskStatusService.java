package com.mark.pr0j3c7.Services;

import com.mark.pr0j3c7.Entities.TaskStatus;
import com.mark.pr0j3c7.Repositories.TaskStatusRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository taskStatusRepository;


    public List<TaskStatus> findAll(){
        return taskStatusRepository.findAll();
    }


    public TaskStatus create(TaskStatus taskStatus){
        return taskStatusRepository.save(taskStatus);
    }

    public TaskStatus update(TaskStatus taskStatus, TaskStatus taskStatusFromDB) {
        BeanUtils.copyProperties(taskStatus, taskStatusFromDB, "id");
        return taskStatusRepository.save(taskStatusFromDB);
    }

    public boolean delete(TaskStatus taskStatus) {
        if (taskStatus != null){
            taskStatusRepository.delete(taskStatus);
            return true;
        }
        return false;
    }
}


