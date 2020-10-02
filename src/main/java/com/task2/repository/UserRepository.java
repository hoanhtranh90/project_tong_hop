package com.task2.repository;
import com.task2.model.User;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("select u from User u where u.username like %:username%")
    Page<User> findByFirstnameEndsWith(String username, final Pageable pageable);


    @Query("select u from User u")
    Page<User> showAllUser(final Pageable pageable);

    @Query("select u from User u where u.id = :id")
    User findUById(Long id);

//    @Query("DELETE from User u where u.id = :id")
//    @Cascade(CascadeType.DELETE)
//    void deleteU(Long id);
}