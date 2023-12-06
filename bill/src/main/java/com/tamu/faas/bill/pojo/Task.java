package com.tamu.faas.bill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {
    String id;
    String userID;
    String taskID;
    String funcName;
    String taskFuncPath;
    String taskInput;

    String output;
    String beginTime;
    String endTime;
    String duration;
    String host;

    String paymentStatus;
}
