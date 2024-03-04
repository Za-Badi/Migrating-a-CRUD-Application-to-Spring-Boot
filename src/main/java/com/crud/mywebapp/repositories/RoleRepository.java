package com.crud.mywebapp.repositories;
import com.crud.mywebapp.model.Roles;
import com.crud.mywebapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Roles, Integer> {
    @Query("SELECT u FROM Roles u WHERE u.role =:role")
    public Roles getRoleByName( String role);



}
