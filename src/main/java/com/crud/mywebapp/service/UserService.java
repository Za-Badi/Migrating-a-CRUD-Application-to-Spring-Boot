package com.crud.mywebapp.service;
import com.crud.mywebapp.repositories.MyUserDetails;
import com.crud.mywebapp.repositories.UserRepository;
import com.crud.mywebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    public List<User> listAll(){
        return (List<User>) repository.findAll();
    }
    public User findUserByName(String email){
        return repository.getUserByUserName(email);
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


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getUserByUserName(email);
        if(user == null){
            throw new UsernameNotFoundException("Could not find user  by this name");
        }
        return new MyUserDetails(user);
    }
}
