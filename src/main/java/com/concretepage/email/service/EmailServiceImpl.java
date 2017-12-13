package com.concretepage.email.service;

import com.concretepage.MyApplication;
import com.concretepage.excel.ApachePOIExcelWrite;
import com.concretepage.jewelry.entity.Jewelry;
import org.apache.catalina.webresources.FileResource;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import com.concretepage.jewelry.entity.Order;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Olga on 7/15/2016.
 */
@Component
public class EmailServiceImpl implements EmailService {
    private  static Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);

    @Autowired
    public JavaMailSender emailSender;
   @Autowired
   IMailContentBuilder mailContentBuilder;

    @Override
    public String sendMessageWithAttachment(Order order ) {
        try {

            LOGGER.info("Sending Email to "+ order.getUsername());



            int totalCost = 0;

           /* String text = "Ура! Новый заказ! от контрагента: " + order.getUsername() + "\n\n\n";
            for (Jewelry i: order.getOrderContent()) {
                text += "Штрих код: " + i.getBarCode()
                        + " Артикль: " + i.getArticle()
                        + " Категория: " + i.getCategory()
                        + " Описание: " + i.getDescription()
                        + " Цена: " + i.getCost()
                        + "\n";
                totalCost += i.getCost();
            }
            text += "\n\n\n"+ "Итого: " + totalCost + " рублей";
*/
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("gimbarrwow@gmail.com");
                messageHelper.setTo("gimbarrwow@gmail.com");
                messageHelper.setSubject("Новый заказ");

                String content = mailContentBuilder.build(order);
                messageHelper.setText(content, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ApachePOIExcelWrite apachePOIExcelWrite = new ApachePOIExcelWrite(order.getOrderContent());
                apachePOIExcelWrite.containFile().write(baos);
                final InputStreamSource attachmentSource = new ByteArrayResource(baos.toByteArray());


                messageHelper.addAttachment("torg-12.xlsx", attachmentSource, "application/msexcel");
            };


            emailSender.send(messagePreparator);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Failed to send Email to "+ order.getUsername());
            return e.getMessage();
        }
        return "complete";
    }


    @Override
    public String sendOrder(Order order) {
        try {

            LOGGER.info("Sending Email to gimbarrwow@gmail.com");
            int totalCost = 0;

            String text = "Ура! Новый заказ! от контрагента: " + order.getUsername() + "\n\n\n";
            for (Jewelry i: order.getOrderContent()) {
            text += "Штрих код: " + i.getBarCode()
                  + " Артикль: " + i.getArticle()
                  + " Категория: " + i.getCategory()
                  + " Описание: " + i.getDescription()
                  + " Цена: " + i.getCost();
            totalCost += i.getCost();
            }
            text += "\n\n\n"+ "Итого: " + totalCost + " рублей";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("gimbarrwow@gmail.com");
            message.setTo("gimbarrwow@gmail.com");
            message.setSubject("Новый заказ");
            message.setText(text);
            emailSender.send(message);
            ;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Failed to send Email to gimbarrwow@gmail.com");
            return e.getMessage();
        }
        return "complete";
    }
}
