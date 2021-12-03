package com.olx.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Advertise;
import com.olx.repository.AdvertiseRepository;
import com.olx.service.AdvertiseService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/olx/advertise")
public class AdvertiseController {

	@Autowired
	private AdvertiseRepository advertiseRepository;

	@Autowired
	private AdvertiseService advertiseService;

	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "This REST endpoint returns all advertise")
	public List<Advertise> getAllAdvertise() {
		log.info("Entering into Advertise controller");
		return this.advertiseService.getAllAdvertise();
	}

	// 7 API - Creates a new user
	@PostMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "This REST endpoint creates a new advertisment")
	public ResponseEntity<Advertise> createNewAdvertise(@RequestHeader("auth-token") String authToken,
			@RequestBody Advertise advertise) {
		log.info("Entering into create a new user controller");
		Advertise advertiseDto = this.advertiseService.createNewAdvertise(authToken, advertise);
		return new ResponseEntity<Advertise>(advertiseDto, HttpStatus.OK);
	}

	// 8 API - Updating the advertisement
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "This REST endpoint updates the existing advertisment")
	public ResponseEntity<Advertise> updateAdvertise(@PathVariable("id") int id, @RequestBody Advertise advertise,
			@RequestHeader("auth-token") String authToken) {
		log.info("Entering into updating the advertisement controller");
		Advertise advertiseDto = this.advertiseService.updateAdvertise(id, advertise, authToken);
		return new ResponseEntity<Advertise>(advertiseDto, HttpStatus.CREATED);
	}

	// 9 API
	@GetMapping(value = "/user/advertise", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "This REST endpointreads all the advertisement posted by logged in user")
	public ResponseEntity<List<Advertise>> getAllAdvertisesByUser(@RequestHeader("auth-token") String authToken) {
		return new ResponseEntity<List<Advertise>>(this.advertiseService.getAllAdvertisesByUser(authToken),
				HttpStatus.OK);
	}

	// 10 API
	@GetMapping(value = "/user/advertise/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="The REST endpoint giving you advertise by user id")
	public ResponseEntity<Advertise> getAdvertiseByUserById(@RequestHeader("auth-token") String authToken, @PathVariable("id") int id) {
		Advertise advertise = this.advertiseService.getAdvertiseByUserById(authToken, id);
		return new ResponseEntity<Advertise>(advertise, HttpStatus.OK);
	}

	// 11 API - Deleting specific
	@DeleteMapping(value = "/user/advertise/{id}")
	@ApiOperation(value = "This REST endpoint deletes the specific advertisement posted by logged in user")
	public boolean deleteAdvertiseById(@PathVariable("id") int advertiseId,
			@RequestHeader("auth-token") String authToken) {
		return advertiseService.deleteAdvertiseById(advertiseId, authToken);
	}

	// 12 API
	@GetMapping(value = "/search/filtercriteria", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "This REST End Point API will give Advertise based on filterCriteria from Database")
	public ResponseEntity<List<Advertise>> getAdvertiseByFilter(@RequestParam("searchText") String searchText,
			@RequestParam("category") int category, @RequestParam("postedBy") String postedBy,
			@RequestParam("dateCondition") String dateCondition, @RequestParam("onDate") LocalDate onDate,
			@RequestParam("fromDate") LocalDate fromDate, @RequestParam("toDate") LocalDate toDate,
			@RequestParam("sortBy") String sortBy, @RequestParam("startIndex") int startIndex,
			@RequestParam("records") int records) {

		log.info("Entering into search filter criterion");
		List<Advertise> advertises = this.advertiseService.getAdvertiseByFilter(searchText, category, postedBy,
				dateCondition, onDate, fromDate, toDate, sortBy, startIndex, records);
		return new ResponseEntity<List<Advertise>>(advertises, HttpStatus.OK);

	}

	// 13 API
	@GetMapping(value = "/search", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Advertise>> getAdvertiseBySearch(@RequestParam("searchText") String searchText) {
		log.info("Entering into search text controller");
		List<Advertise> advertises = this.advertiseService.getAdvertiseBySearch(searchText);
		return new ResponseEntity<List<Advertise>>(advertises, HttpStatus.OK);
	}

	// 14 API
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "This REST endpoints gives advertisement details based on advertisment id")
	public ResponseEntity<Advertise> getAdvertiseById(@RequestHeader("auth-token") String authToken,
			@PathVariable("id") int advertiseId) {
		Advertise advertise = this.advertiseService.getAdvertiseById(authToken, advertiseId);
		return new ResponseEntity<Advertise>(advertise, HttpStatus.OK);
	}

}
