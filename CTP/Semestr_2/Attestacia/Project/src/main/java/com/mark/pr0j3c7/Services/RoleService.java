package com.mark.pr0j3c7.Services;

import com.mark.pr0j3c7.Entities.Role;
import com.mark.pr0j3c7.Repositories.RoleRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public List<Role> findAll(){
        return roleRepository.findAll();
    }


    public Role create(Role role){
        return roleRepository.save(role);
    }

    public Role update(Role role, Role roleFromDB) {
        BeanUtils.copyProperties(role, roleFromDB, "id");
        return roleRepository.save(roleFromDB);
    }

    public boolean delete(Role role) {
        if (role != null){
            roleRepository.delete(role);
            return true;
        }
        return false;
    }
}

