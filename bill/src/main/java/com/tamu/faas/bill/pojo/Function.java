package com.tamu.faas.bill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "function")
public class Function {
    private String id;
    private String funcId;
    private String userId;
    private String funcName;
    private String funcArgs; // JSON 字符串，例如: {"num1":int,"num2":int}
    private String funcPath;
    private String funcExplanation;
    private int funcOutput; // 0 表示同步，1 表示异步
    private String funcResource; // JSON 字符串，例如: {"CPU":4,"MEM
}
