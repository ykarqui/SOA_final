package ar.edu.iua.soa.cse.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.soa.cse.business.BusinessException;
import ar.edu.iua.soa.cse.business.IUserBusiness;
import ar.edu.iua.soa.cse.business.NotFoundException;
import ar.edu.iua.soa.cse.business.UnauthorizedException;
import ar.edu.iua.soa.cse.model.User;
import ar.edu.iua.soa.cse.model.dto.LoginDTO;

@RestController
@RequestMapping(Constants.URL_USER)
@CrossOrigin
public class UserRESTController {
	@Autowired
	private IUserBusiness userBusiness;

	@GetMapping("")
	public ResponseEntity<List<User>> list() {
		try {
			return new ResponseEntity<List<User>>(userBusiness.list(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> load(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<User>(userBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody LoginDTO user) {
		try {
			return new ResponseEntity<User>(userBusiness.check(user), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/gentoken")
	public ResponseEntity<User> genToken(@RequestParam String username) {
		try {
			userBusiness.generateToken(username);
			return new ResponseEntity<User>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/token")
	public ResponseEntity<User> checkToken(@RequestParam String username) {
		try {
			userBusiness.checkToken(username);
			return new ResponseEntity<User>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		} catch (UnauthorizedException e) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("")
	public ResponseEntity<User> add(@RequestBody User user) {
		try {
			userBusiness.add(user);
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", "/user/" + user.getIdUser());
			return new ResponseEntity<User>(responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("")
	public ResponseEntity<User> update(@RequestBody User user) {
		try {
			return new ResponseEntity<User>(userBusiness.update(user), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		try {
			userBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
