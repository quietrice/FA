package com.mark.pr0j3c7.Controllers;

import com.mark.pr0j3c7.Entities.Role;
import com.mark.pr0j3c7.Services.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private final RoleService ROLE_SERVICE;

    @Autowired
    public RoleController(RoleService ROLE_SERVICE) {
        this.ROLE_SERVICE = ROLE_SERVICE;
    }

    @PostMapping(value="/api/role")
    private ResponseEntity<?> create(@RequestBody Role role){
        Role newRole = ROLE_SERVICE.create(role);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/role")
    private ResponseEntity<List<Role>> readAll(){
        final List<Role> ROLE_LIST = ROLE_SERVICE.findAll();

        return ROLE_LIST != null && !ROLE_LIST.isEmpty()
                ? new ResponseEntity<>(ROLE_LIST, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "api/role/{id}")
    public ResponseEntity<Role> getOne(@PathVariable(name = "id") Role role) {
        final Role CURRENT_ROLE = role;

        return role != null
                ? new ResponseEntity<>(CURRENT_ROLE, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/role/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") Role roleFromDB,
                                 @RequestBody Role role) {
        Role updatedRole = ROLE_SERVICE.update(role, roleFromDB);
        if (updatedRole != null) {
            return new ResponseEntity<>(updatedRole, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "api/role/{id}")
    public ResponseEntity<List<Role>> delete(@PathVariable(name = "id") Role role) {
        if (ROLE_SERVICE.delete(role)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
