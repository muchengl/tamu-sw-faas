package com.tamu.faas.bill.controller;


import com.tamu.faas.bill.dao.BillRepository;
import com.tamu.faas.bill.dao.FuncRepository;
import com.tamu.faas.bill.dao.TaskRepository;
import com.tamu.faas.bill.dao.UserRepository;
import com.tamu.faas.bill.pojo.Bill;
import com.tamu.faas.bill.pojo.Task;
import com.tamu.faas.bill.pojo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
public class BillController {

    @Autowired
     BillRepository billRepository;

    @Autowired
    FuncRepository funcRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/user/balance")
    public double getBalance(String uid){
        User user=userRepository.findByUserId(uid);
        return user.getBalance();
    }

    @RequestMapping("/user/add/balance")
    public String getBalance(double money, String uid){
        System.out.println("ADD:"+money);

        //String uid=(String)request.getSession().getAttribute("userID");
        User user=userRepository.findByUserId(uid);
        user.setBalance(
                user.getBalance()+money
        );
        userRepository.save(user);


        Bill bill=new Bill();
        bill.setBillID(UUID.randomUUID().toString());
        bill.setDate(new Date().toString());
        bill.setMoney(money);
        bill.setStatus("success");
        bill.setUserID(uid);
        bill.setType("ADD");

        billRepository.save(bill);

        return "ok";
    }


    @RequestMapping("/bills")
    public List<Bill> getBills(String uid){
        System.out.println("GET BILLS");
        //String uid=(String)request.getSession().getAttribute("userID");

        List<Bill> list=billRepository.getBillByUserID(uid);

//        System.out.println(JSON.toJSONString(list));
        return  list;
    }

    @RequestMapping("/update/bills")
    public Bill updateBills(String uid){
        //String uid=(String)request.getSession().getAttribute("userID");

        List<Task> tasks=taskRepository.findByUserID(uid);

        Bill bill=new Bill();
        bill.setUserID(uid);
        bill.setTaskList(new LinkedList<>());
        bill.setDate(new Date().toString());
        bill.setType("PAY");
        bill.setStatus("in process");
        bill.setMoney(0);
        bill.setBillID(UUID.randomUUID().toString());

        for (Task task : tasks) {
            if(task.getPaymentStatus()!=null && task.getPaymentStatus().equals("ok")){
                continue;
            }
            task.setPaymentStatus("ok");
            taskRepository.save(task);
            bill.getTaskList().add(task);
            bill.setMoney(
                    bill.getMoney()+Double.valueOf(task.getDuration())*0.000001
            );
        }

        if(bill.getMoney()==0) return bill;

        billRepository.save(bill);

        return bill;
    }

    @RequestMapping("/pay/bill")
    public String updateBills(String bid,String uid){
        System.out.println(bid);
        //String uid=(String)request.getSession().getAttribute("userID");

        Bill bill=billRepository.getBillByBillID(bid);
        bill.setStatus("success");

        double money=bill.getMoney();
        User user=userRepository.findByUserId(uid);
        user.setBalance(
                user.getBalance()-money
        );
        userRepository.save(user);
        billRepository.save(bill);

        return "ok";
    }
}
