package com.mark.pr0j3c7.Services;

import com.mark.pr0j3c7.Entities.Task;
import com.mark.pr0j3c7.Repositories.TaskRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public List<Task> findAll(){
        return taskRepository.findAll();
    }


    public Task create(Task task){
        return taskRepository.save(task);
    }

    public Task update(Task task, Task taskFromDB) {
        BeanUtils.copyProperties(task, taskFromDB, "id");
        return taskRepository.save(taskFromDB);
    }

    public boolean delete(Task task) {
        if (task != null){
            taskRepository.delete(task);
            return true;
        }
        return false;
    }
}


