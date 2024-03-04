package com.crud.mywebapp.service;

import com.crud.mywebapp.repositories.RoleRepository;
import com.crud.mywebapp.repositories.UserRepository;
import com.crud.mywebapp.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Roles> listAll() {
        return (List<Roles>) repository.findAll();
    }

    public Roles getRoleById(Integer id) {
        if(repository.findById(id).isPresent()){
            return repository.findById(id).get();
        }
        else {
            return null;
        }
    }

    public void save(Roles role) {
        repository.save(role);
    }


    public void removeUserById(Integer id) {
        repository.deleteById(id);
    }

    public Roles findUserById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Roles getRoleByName(String role) {
        return repository.getRoleByName(role);

    }
}


