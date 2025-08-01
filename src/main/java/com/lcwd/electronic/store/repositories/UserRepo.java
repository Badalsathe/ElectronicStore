package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository <User, String> {


    Optional <User> findByEmail(String email);  // ← Add this method

    Optional <User> findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining (String keywords);
}
