package com.task2.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

//    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<User> users;


//    public Role(String role) {
//        this.role = role;
//    }
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

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

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUser(List<User> users) {
//        this.users = users;
//    }
}

/*
s
*/