package com.justchat.demo.repository;

import com.justchat.demo.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser,Long> {


    CustomUser findByLogin(String login);

    boolean existsByLogin(String login);


}
