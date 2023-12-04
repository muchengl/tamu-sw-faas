package com.tamu.faas.dao;

import com.tamu.faas.pojo.Function;
import com.tamu.faas.pojo.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserID(String userId);
}
