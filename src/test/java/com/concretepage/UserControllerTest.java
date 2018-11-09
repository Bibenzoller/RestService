package com.concretepage;


import com.concretepage.auth.controller.UsersController;
import com.concretepage.auth.entity.Login;
import com.concretepage.auth.entity.User;
import com.concretepage.auth.service.UserService;
import com.concretepage.auth.sucurity.util.CreateUserService;
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
@WebMvcTest(UsersController.class)
@ContextConfiguration
public class UserControllerTest {

    UserService userService = mock(UserService.class);
    CreateUserService createUserService = mock(CreateUserService.class);
    @MockBean
    private UsersController usersController;


    @Test
    public void addUser() {
        User user = new User();
        when(userService.addUser(user)).
                thenReturn(true);
        usersController =
                new UsersController(userService);
        ResponseEntity<Void> entity = usersController.createUser(user);
        assertSame(entity.getStatusCode(), HttpStatus.CREATED);
        verify(userService).addUser(user);
    }

    @Test
    public void failedAddUser() {
        User user = new User();
        when(userService.addUser(user)).
                thenReturn(false);
        usersController =
                new UsersController(userService);
        ResponseEntity<Void> entity = usersController.createUser(user);
        assertSame(entity.getStatusCode(), HttpStatus.CONFLICT);
        verify(userService).addUser(user);
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setUsername("test");
        when(userService.getUserByUsername("test")).
                thenReturn(user);
        usersController =
                new UsersController(userService);
        ResponseEntity<CreateUserService> entity = usersController.getUser("test");
        assertSame(entity.getStatusCode(), HttpStatus.OK);
        assertSame(entity.getBody().getUsername(), user.getUsername());
        verify(userService).getUserByUsername("test");
    }

    @Test
    public void failedGetUser() {
        User user = new User();
        user.setUsername("test");
        when(userService.getUserByUsername("test")).
                thenReturn(null);
        usersController =
                new UsersController(userService);
        ResponseEntity<CreateUserService> entity = usersController.getUser("test");
        assertSame(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(userService).getUserByUsername("test");
    }

    @Test
    public void login() {
        Login login = new Login();
        login.setUsername("test");
        login.setPassword("test");
        when(userService.userExists(login.getUsername(),login.getPassword())).
                thenReturn(true);
        usersController =
                new UsersController(userService);
        ResponseEntity<Void> entity = usersController.login(login);
        assertSame(entity.getStatusCode(), HttpStatus.OK);
        verify(userService).userExists(login.getUsername(),login.getPassword());
    }

    @Test
    public void failedLogin() {
        Login login = new Login();
        login.setUsername("test");
        login.setPassword("test");
        when(userService.userExists(login.getUsername(),login.getPassword())).
                thenReturn(false);
        usersController =
                new UsersController(userService);
        ResponseEntity<Void> entity = usersController.login(login);
        assertSame(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(userService).userExists(login.getUsername(),login.getPassword());
    }

}