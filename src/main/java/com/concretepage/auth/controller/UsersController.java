package com.concretepage.auth.controller;
import java.util.*;

import com.concretepage.auth.entity.User;
import com.concretepage.auth.service.IUserService;
import com.concretepage.auth.sucurity.util.CreateUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("user")
@Api(value="user", description="Operations to interact with users")

public class UsersController {

	@Autowired
	private IUserService userService;
	CreateUserService create = new CreateUserService();

	@ApiOperation(value = "Add user to DB",response = String.class)
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating User " + user.getUsername());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		boolean flag = userService.addUser(user);
		if (flag == false) {
			headers.add("result", "already exists");
			return new ResponseEntity<String>("already exsist", HttpStatus.CONFLICT);
		}
		headers.add("result", "complete");
		return new ResponseEntity<String >("complete", HttpStatus.CREATED);

	}
	@ApiOperation(value = "Get user by username",response = User.class)
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<CreateUserService> getUser(@PathVariable("username") String username) {
		System.out.println("Fetching User with name " + username);
		CreateUserService user = create.loadUserByUsername(userService.getUserByUsername(username));

		if (user == null) {
			System.out.println("User with name " + username + " not found");
			return new ResponseEntity<CreateUserService>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<CreateUserService>(user, HttpStatus.OK);
	}
} 