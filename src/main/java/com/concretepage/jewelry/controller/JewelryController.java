package com.concretepage.jewelry.controller;

import com.concretepage.auth.sucurity.util.CreateUserService;
import com.concretepage.jewelry.entity.Jewelry;
import com.concretepage.jewelry.service.JewelryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("jewelry")
@Api(value="jewelry", description="Operations to interact with goods")

public class JewelryController {

	private Logger logger = LoggerFactory.getLogger(JewelryController.class);

	@Autowired
	private JewelryService jewelryService;
	CreateUserService create = new CreateUserService();

	@ApiOperation(value = "Add a good in DB", response = String.class)

	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> createUser(@RequestBody Jewelry jewelry, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Jewelry " + jewelry.getArticle());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		boolean flag = jewelryService.addJewelry(jewelry);
		if (flag == false) {
			return new ResponseEntity<String>("already exsist", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String >("complete", HttpStatus.CREATED);

	}

	@ApiOperation(value = "Get a good from DB by barcode", response = Jewelry.class)

	@RequestMapping(value = "/get/{barcode}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Jewelry> getJewelry(@PathVariable("barcode") String barCode) {

		Jewelry jewelry = jewelryService.getJewelryByBarCode(barCode);

		if (jewelry == null) {
			System.out.println("Jewelry with barCode " + barCode + " not found");
			return new ResponseEntity<Jewelry>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Jewelry>(jewelry, HttpStatus.OK);
	}

	@ApiOperation(value = "Get all good in DB", response = Jewelry[].class)

	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<Jewelry>> getJewelry() {
		 List<Jewelry> result = jewelryService.getAllJewelry();

		if (result == null) {
			System.out.println(" not found");
			return new ResponseEntity<List<Jewelry>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Jewelry>>(result, HttpStatus.OK);
	}

} 