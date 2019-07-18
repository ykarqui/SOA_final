package ar.edu.iua.sat.web.services;

import java.util.Calendar;
import java.util.Date;

import javax.security.auth.message.AuthException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.edu.iua.sat.business.BusinessException;
import ar.edu.iua.sat.business.CheckBasicAuthorization;
import ar.edu.iua.sat.business.IAuthTokenBusiness;
import ar.edu.iua.sat.business.NotFoundException;
import ar.edu.iua.sat.model.AuthToken;

public class BaseRestService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${app.session.token.timeout}")
	private int sessionTimeout;

	@Autowired
	private IAuthTokenBusiness authTokenService;
	
	@Autowired
	private CheckBasicAuthorization cba;

	protected ResponseEntity<Object> genToken(String username, int diasvalido, String auth) {
		try {
			cba.check(auth);
			AuthToken token = buildToken(username, diasvalido);
			return new ResponseEntity<Object>("{\n" + "	\"username\":\"" + token.getUsername() + "\",\n"
					+ "	\"token\":\"" + token.encodeCookieValue() + "\"\n}", HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (AuthException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	protected ResponseEntity<Object> checkToken(String username, String token) {
		try {
			authTokenService.check(username, token);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (BusinessException e) {
			log.error("Business");
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException nf) {
			log.error("NotFound");
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	protected AuthToken buildToken(String username, int diasvalido) throws BusinessException {
		Calendar f = Calendar.getInstance();
		Calendar t = Calendar.getInstance();
		f.setTime(new Date());
		t.setTime(new Date());
		t.add(Calendar.DATE, diasvalido);
		AuthToken token = new AuthToken(username, f.getTime(), t.getTime());
		authTokenService.save(token);
		return token;
	}

}
