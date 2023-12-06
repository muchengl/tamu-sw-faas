package com.tamu.faas.bill.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bills")
public class Bill {
    String id;
    String userID;
    String billID;

    String type;

    double money;

    String status;

    String date;

    LinkedList<Task> taskList;
}
