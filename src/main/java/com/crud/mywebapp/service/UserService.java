package com.crud.mywebapp.service;
import com.crud.mywebapp.dao.UserRepository;
import com.crud.mywebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> listAll(){
        return (List<User>) repository.findAll();
    }

    public void save(User user) {
        repository.save(user);
    }

    public User getUser(Integer id) throws UserNotFoundException {
        Optional<User> userById =  repository.findById(id);
        if (userById.isPresent()){
            return userById.get();
        }
        throw new UserNotFoundException("Could not found user with this ID");

    }

    public void removeUserById(Integer id) {
        repository.deleteById(id);
    }
}
