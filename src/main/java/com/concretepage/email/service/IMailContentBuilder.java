package com.concretepage.email.service;

import com.concretepage.jewelry.entity.Order;

public interface IMailContentBuilder {

    public String build(Order order);
}
