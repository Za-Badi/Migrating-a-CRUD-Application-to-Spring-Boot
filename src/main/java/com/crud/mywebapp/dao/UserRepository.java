package com.crud.mywebapp.dao;

import com.crud.mywebapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
