package com.task2.model;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity(name = "role")
public class Role{
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String role;

    @OneToMany(mappedBy="role")
    private Set<User> users;


    public Role(String role) {
        this.role = role;
    }

    public Role() {

    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

/*
s
*/