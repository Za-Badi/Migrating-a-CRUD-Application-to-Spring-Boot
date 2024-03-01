package com.crud.mywebapp.repositories;
import com.crud.mywebapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


    @Query("SELECT u FROM User u WHERE u.email =:email")
    public User getUserByUserName(@Param("email") String email);
//    Optional<User> getUserByEmail(String email);
//       User getUserByEmail (String email);
}
