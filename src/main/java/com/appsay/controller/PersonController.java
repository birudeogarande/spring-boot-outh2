package com.appsay.controller;

import java.security.Principal;
import java.util.Collection;

import com.appsay.errorhandling.AppException;
import com.appsay.errorhandling.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.appsay.model.Party;
import com.appsay.model.Person;
import com.appsay.repository.PersonRepository;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private PersonRepository personRepo;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Person>> getPeople() throws Exception {
		Collection<Person>  personCollection=personRepo.findAll();
		personCollection = null;
		if (personCollection!=null){
			return new ResponseEntity<>(personCollection, HttpStatus.OK);
		}
		throw new Exception("No people available.");


	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Person> getPerson(@PathVariable long id) throws AppException {
		Person person = personRepo.findOne(id);

		if (person != null) {
			return new ResponseEntity<Person>(person,HttpStatus.OK);
		} else {
			throw new AppException("Person with id does not exist","Person with id does not exist");
		}
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(AppException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.PRECONDITION_FAILED.value(),3000,ex.getMessage(),ex.getDeveloperMessage(),null);
		ResponseEntity<ErrorResponse> responseEntity= new ResponseEntity<ErrorResponse>(error, HttpStatus.PRECONDITION_FAILED);
		return responseEntity;
	}
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addPerson(@RequestBody Person person) {
		return new ResponseEntity<>(personRepo.save(person), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePerson(@PathVariable long id, Principal principal) {
		Person currentPerson = personRepo.findByUsername(principal.getName());
		
		if (currentPerson.getId() == id) {
			personRepo.delete(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value = "/{id}/parties", method = RequestMethod.GET)
	public ResponseEntity<Collection<Party>> getPersonParties(@PathVariable long id) {
		Person person = personRepo.findOne(id);

		if (person != null) {
			return new ResponseEntity<>(person.getParties(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
