package ar.edu.iua.sat.web.services;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController

@PropertySource({ "classpath:version.properties" })
public class CoreRestService extends BaseRestService {

	@GetMapping(value = Constants.URL_TOKEN)
	public ResponseEntity<Object> getToken(@RequestParam("username") String username,
			@RequestParam("diasvalido") int diasvalido) {
		return genToken(username, diasvalido);
	}

}
