package com.concretepage.email.service;
import com.concretepage.jewelry.entity.Order;


/**
 * Created by Olga on 8/22/2016.
 */
public interface EmailService {


    String sendMessageWithAttachment(Order order);
    String sendOrder(Order order);


}
