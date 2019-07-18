package ar.edu.iua.sat.web.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.sat.model.dto.UserDTO;


@RestController
public class CoreRestService extends BaseRestService {
	
	@PostMapping(value = Constants.URL_TOKEN)
	public ResponseEntity<Object> getToken(@RequestBody UserDTO user, @RequestHeader("Authorization") String auth) {
		return genToken(user.getUsername(), user.getDays(), auth);
	}
	
	@GetMapping(value = Constants.URL_TOKEN)
	public ResponseEntity<Object> verifyToken(@RequestHeader("username") String username,
			@RequestHeader("auth-token") String token) {
		return checkToken(username, token);
	}

}
