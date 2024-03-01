package com.crud.mywebapp.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;


@Entity
@Table(name = "users")
@Component
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private Integer age;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Roles> roles = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, Integer age, String email, String password) {
        this.firstName = firstName;
        this.age = age;
        this.lastName = lastName;
        this.email = email;
        this.password =password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    public String getPassword() {
        return password;
    }





    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id= " + id +
                " , name = " + firstName + '\'' +
                " , lastName = " + lastName + '\'' +
                " , email = " + email + '\'' +
                '}';
    }

    public Set<Roles> getRoles() {
//      for(Roles role : roles){
//
        System.out.println("zaina 2 "+ (roles.toArray()[0]));
        return roles;

    }

    public Set<String> getRoleUser() {
        Set<String> auth = new HashSet<>();
        String authorities ;
        for (Roles role : roles) {
            authorities = String.valueOf(new SimpleGrantedAuthority(role.getRole()));
            auth.add(authorities.toString());
        }
        return auth;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

}
