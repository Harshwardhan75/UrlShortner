package com.urlshortner.repository;

import com.urlshortner.entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("""
            Update User u
            set u.userName =:userName, u.password =:password
            where u.userId =:userId
            """)
    void updateUser(@PathVariable String userId,@PathVariable String userName,@PathVariable String password);
}
