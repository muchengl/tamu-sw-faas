package com.tamu.faas.bill.dao;


import com.tamu.faas.bill.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByUserId(String id);
}
