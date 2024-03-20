package com.crud.mywebapp.service;
import com.crud.mywebapp.repositories.MyUserDetails;
import com.crud.mywebapp.repositories.UserRepository;
import com.crud.mywebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<List<User>> listAll() {
        try {
            return new ResponseEntity<>((List<User>) repository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
    }
    public User findUserByName(String email){
        return repository.getUserByUserName(email);
    }


    public ResponseEntity<String> save(User user) {
        try{
            repository.save(user);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e){
            return  new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<User> getUser(Integer id) {
        Optional<User> userById =  repository.findById(id);
        return userById;
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
