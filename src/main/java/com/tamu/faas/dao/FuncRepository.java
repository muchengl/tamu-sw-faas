package com.tamu.faas.dao;

import com.tamu.faas.pojo.Function;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface FuncRepository extends MongoRepository<Function, String> {
    List<Function> findByUserId(String userId);

    Function findByFuncId(String funcId);

//    void updateByFuncId(Function function);
}

