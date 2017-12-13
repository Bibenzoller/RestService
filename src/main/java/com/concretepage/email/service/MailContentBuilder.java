package com.concretepage.email.service;

import com.concretepage.jewelry.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder implements IMailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String build(Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        return templateEngine.process("simple", context);
    }

}