package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.ToDoEntity;

@Repository

public interface ToDoRepository extends JpaRepository<ToDoEntity, String> {
	//@Query("select * from ToDo t where t.userId = ?1")
	List<ToDoEntity> findByUserId(String id);
}
