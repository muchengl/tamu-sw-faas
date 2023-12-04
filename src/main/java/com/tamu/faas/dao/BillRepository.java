package com.tamu.faas.dao;

import com.tamu.faas.pojo.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BillRepository extends MongoRepository<Bill, String> {

    List<Bill> getBillByUserID(String uid);

    Bill getBillByBillID(String bid);
}
