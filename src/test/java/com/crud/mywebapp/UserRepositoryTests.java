package com.crud.mywebapp;
import com.crud.mywebapp.dao.UserRepository;
import com.crud.mywebapp.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import java.util.Arrays;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)

public class UserRepositoryTests {

    @Autowired private UserRepository repo;
    @Test
    public void testAddNewUser(){
        User user = new User();
        user.setEmail("Rak@gmail.com");
        user.setFirstName("Marchmello");
        user.setLastName("crossedEyes");
        User savedUser = repo.save(user);
        Assertions.assertNotNull(savedUser);
        Assertions.assertNotEquals(savedUser.getId(),0);
    }
    @Test
    public void testListAll(){
       Iterable<User> users = repo.findAll();
        Assertions.assertNotEquals((Arrays.asList(users)).size(), 0);
        System.out.println("zahazaha "+  (Arrays.asList(users)).size());
//        for (User user : users){
//            System.out.println(user);
//        }

    }
    @Test
    public void testUpdate() {
        Integer userId = 206;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setFirstName("Zeina");
        repo.save(user);

        User updateduser = repo.findById(userId).get();
        Assertions.assertEquals(updateduser.getFirstName(), "Zeina");
    }

    @Test
    public void getUser() {
        Integer userId = 206;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        Assertions.assertNotNull(user);
        System.out.println("getUser " +user.getFirstName());
    }
    @Test
    public void deleteUser() {
        Integer userId = 204;
        repo.deleteById(userId);
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        Assertions.assertNotNull(user);
        System.out.println("getUser " +user.getFirstName());
    }


}
