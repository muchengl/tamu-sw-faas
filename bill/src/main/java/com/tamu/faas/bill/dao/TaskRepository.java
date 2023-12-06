package com.tamu.faas.bill.dao;

import com.tamu.faas.bill.pojo.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserID(String userId);
}
