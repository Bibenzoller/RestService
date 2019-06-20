package com.concretepage.auth.controller;

import com.concretepage.auth.entity.Login;
import com.concretepage.auth.entity.User;
import com.concretepage.auth.service.IUserService;
import com.concretepage.auth.service.UserService;
import com.concretepage.auth.sucurity.util.CreateUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
@Api(value="user", description="Operations to interact with users")

public class UsersController {

	@Autowired
	private IUserService userService;
	CreateUserService create = new CreateUserService();

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(value = "Add user to DB",response = String.class)
	@PostMapping(value = "/add",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Void> createUser(@RequestBody User user) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		boolean flag = userService.addUser(user);
		if (flag == false) {
			headers.add("result", "already exists");
			return new ResponseEntity<>( HttpStatus.CONFLICT);
		}
		headers.add("result", "complete");
		return new ResponseEntity<>(HttpStatus.CREATED);

	}
	@ApiOperation(value = "Get user by username",response = User.class)
	@GetMapping(value = "/user/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CreateUserService> getUser(@PathVariable("username") String username) {
		CreateUserService user = create.loadUserByUsername(
				userService.getUserByUsername(username));

		if (user == null) {
			return new ResponseEntity<CreateUserService>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<CreateUserService>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "Login",response = String.class)
	@PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> login (@RequestBody Login login) {

        if(!userService.userExists(login.getUsername(),login.getPassword()))
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        else if(userService.userExists(login.getUsername(),login.getPassword())
                &&!userService.getUserByUsername(login.getUsername()).isEnabled())
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        else return new ResponseEntity<Void>( HttpStatus.OK);
    }
} 