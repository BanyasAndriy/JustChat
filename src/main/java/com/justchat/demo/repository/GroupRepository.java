package com.justchat.demo.repository;

import com.justchat.demo.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface GroupRepository  extends JpaRepository<Group,Long> {
}
