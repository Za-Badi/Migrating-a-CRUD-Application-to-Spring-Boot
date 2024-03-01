package com.crud.mywebapp.model;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;


@Entity
@Table(name = "roles")
@Component
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "role")
    private String role;



    public Roles() {}

    public Roles(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString(){
        return "User{"+
                "id= "+id+
                " , role = "+role+ '\''+
                '}';
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


}
