package com.mark.pr0j3c7.Services;

import com.mark.pr0j3c7.Entities.User;
import com.mark.pr0j3c7.Repositories.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> findAll(){
        return userRepository.findAll();
    }


    public User create(User user){
        return userRepository.save(user);
    }

    public User update(User user, User userFromDB) {
        BeanUtils.copyProperties(user, userFromDB, "id");
        return userRepository.save(userFromDB);
    }

    public boolean delete(User user) {
        if (user != null){
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}

