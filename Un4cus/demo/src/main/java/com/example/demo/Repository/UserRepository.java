package com.example.demo.Repository;

import com.example.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByDeletedFalse();
    boolean existsByUsername(String username);  //handling duplicate username creating user

    Optional<UserEntity> findByUsername(String username); //handling username duplication for update user


}
