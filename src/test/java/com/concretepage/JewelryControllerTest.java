package com.concretepage;


import com.concretepage.email.service.EmailService;
import com.concretepage.jewelry.controller.JewelryController;
import com.concretepage.jewelry.entity.Jewelry;
import com.concretepage.jewelry.entity.Order;
import com.concretepage.jewelry.service.JewelryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertSame;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(JewelryController.class)
@ContextConfiguration
public class JewelryControllerTest {

    @Autowired
    private MockMvc mvc;
    JewelryService jewelryService = mock(JewelryService.class);

    @MockBean
    private JewelryController jewelryController;


    @Test
    public void getAllJewelry() {
        jewelryController = new JewelryController(jewelryService);
        List<Jewelry> expectedSpittles =
                asList(new Jewelry(), new Jewelry(), new Jewelry());
        when(jewelryService.getAllJewelry()).
                thenReturn(expectedSpittles);
        jewelryController =
                new JewelryController(jewelryService);
        ResponseEntity<List<Jewelry>> entity = jewelryController.getAllJewelries();
        assertSame(expectedSpittles, entity.getBody());
        assertSame(entity.getStatusCode(), HttpStatus.OK);
        verify(jewelryService).getAllJewelry();
    }

    @Test
    public void getAllJewelryEmpty() {
        jewelryController = new JewelryController(jewelryService);
        when(jewelryService.getAllJewelry()).
                thenReturn(null);
        jewelryController =
                new JewelryController(jewelryService);
        ResponseEntity<List<Jewelry>> entity = jewelryController.getAllJewelries();
        assertSame(null, entity.getBody());
        assertSame(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(jewelryService).getAllJewelry();
    }

    @Test
    public void getJewelry() {
        jewelryController = new JewelryController(jewelryService);
        Jewelry jewelry = new Jewelry();
        jewelry.setBarCode("test");
        when(jewelryService.getJewelryByBarCode("test")).
                thenReturn(jewelry);
        jewelryController =
                new JewelryController(jewelryService);
        ResponseEntity<Jewelry> entity = jewelryController.getJewelry("test");
        assertSame(jewelry, entity.getBody());
        assertSame(jewelry.getBarCode(), entity.getBody().getBarCode());
        assertSame(entity.getStatusCode(), HttpStatus.OK);
        verify(jewelryService).getJewelryByBarCode("test");
    }

    @Test
    public void notFoundJewelry() {
        jewelryController = new JewelryController(jewelryService);

        when(jewelryService.getJewelryByBarCode("test")).
                thenReturn(null);
        jewelryController =
                new JewelryController(jewelryService);
        ResponseEntity<Jewelry> entity = jewelryController.getJewelry("test");
        assertSame(null, entity.getBody());
        assertSame(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(jewelryService).getJewelryByBarCode("test");
    }

    @Test
    public void sendJewelry() {
        EmailService emailService = mock(EmailService.class);
        jewelryController = new JewelryController(jewelryService, emailService);
        Order order = new Order();
        when(emailService.sendMessageWithAttachment(order)).
                thenReturn("complete");
        jewelryController =
                new JewelryController(jewelryService, emailService);
        ResponseEntity<String> entity = jewelryController.sendOrder(order);
        assertSame(entity.getStatusCode(), HttpStatus.OK);
        verify(emailService).sendMessageWithAttachment(order);
    }

    @Test
    public void failedSendJewelry() {
        EmailService emailService = mock(EmailService.class);
        jewelryController = new JewelryController(jewelryService, emailService);
        Order order = new Order();
        when(emailService.sendMessageWithAttachment(order)).
                thenReturn("null");
        jewelryController =
                new JewelryController(jewelryService, emailService);
        ResponseEntity<String> entity = jewelryController.sendOrder(order);
        assertSame(entity.getStatusCode(), HttpStatus.CONFLICT);
        verify(emailService).sendMessageWithAttachment(order);
    }

    @Test
    public void createJewelry() {
        Jewelry jewelry = new Jewelry();
        when(jewelryService.addJewelry(jewelry)).
                thenReturn(true);
        jewelryController =
                new JewelryController(jewelryService);
        ResponseEntity<Void> entity = jewelryController.createJewelry(jewelry);
        assertSame(entity.getStatusCode(), HttpStatus.CREATED);
        verify(jewelryService).addJewelry(jewelry);
    }

    @Test
    public void failedCreateJewelry() {
        Jewelry jewelry = new Jewelry();
        when(jewelryService.addJewelry(jewelry)).
                thenReturn(false);
        jewelryController =
                new JewelryController(jewelryService);
        ResponseEntity<Void> entity = jewelryController.createJewelry(jewelry);
        assertSame(entity.getStatusCode(), HttpStatus.CONFLICT);
        verify(jewelryService).addJewelry(jewelry);
    }


}