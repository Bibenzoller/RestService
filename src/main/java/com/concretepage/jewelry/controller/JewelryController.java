package com.concretepage.jewelry.controller;

import com.concretepage.auth.sucurity.util.CreateUserService;
import com.concretepage.email.service.EmailService;
import com.concretepage.excel.ApachePOIExcelWrite;
import com.concretepage.jewelry.entity.Jewelry;
import com.concretepage.jewelry.entity.Order;
import com.concretepage.jewelry.service.JewelryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
	@Autowired
	private EmailService emailService;
	CreateUserService create = new CreateUserService();

	public JewelryController() {
	}

	public JewelryController(JewelryService jewelryService) {
		this.jewelryService = jewelryService;

	}
	public JewelryController(JewelryService jewelryService,EmailService emailService) {
		this.emailService = emailService;
		this.jewelryService = jewelryService;
	}


	@ApiOperation(value = "Add a good in DB", response = String.class)

	@PostMapping(value = "/add",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Void> createJewelry(@RequestBody Jewelry jewelry) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		boolean flag = jewelryService.addJewelry(jewelry);
		if (flag == false) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@ApiOperation(value = "Get a good from DB by barcode", response = Jewelry.class)
	@Cacheable("jewelry")
	@GetMapping(value = "/get/{barcode}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Jewelry> getJewelry(@PathVariable("barcode") String barCode) {

		Jewelry jewelry = jewelryService.getJewelryByBarCode(barCode);

		if (jewelry == null) {
			logger.error("Jewelry with barCode " + barCode + " not found");
			return new ResponseEntity<Jewelry>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Jewelry>(jewelry, HttpStatus.OK);
	}

	@ApiOperation(value = "Get all good in DB", response = Jewelry[].class)
	@Cacheable("jewelry")
	@GetMapping(value = "/getall", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Jewelry>> getAllJewelries() {
		 List<Jewelry> result = jewelryService.getAllJewelry();

		if (result == null) {
			logger.error("jewelries not found");
			return new ResponseEntity<List<Jewelry>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Jewelry>>(result, HttpStatus.OK);
	}


	@ApiOperation(value = "Send an order", response = String.class)

	@PostMapping(value = "/sendOrder", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> sendOrder(@RequestBody Order order) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String status = emailService.sendMessageWithAttachment(order);
          if(status.equals("complete"))
		return new ResponseEntity<String>( HttpStatus.OK);
          else return new ResponseEntity<String>( HttpStatus.CONFLICT);

	}

} 