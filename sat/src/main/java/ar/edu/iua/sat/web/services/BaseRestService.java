package ar.edu.iua.sat.web.services;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.edu.iua.sat.business.BusinessException;
import ar.edu.iua.sat.business.IAuthTokenBusiness;
import ar.edu.iua.sat.model.AuthToken;

public class BaseRestService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${app.session.token.timeout}")
	private int sessionTimeout;

	@Autowired
	private IAuthTokenBusiness authTokenService;

	protected ResponseEntity<Object> genToken(String username, int diasvalido) {
		try {
			AuthToken token = buildToken(username, diasvalido);
			return new ResponseEntity<Object>("{" + "\"username\":\"" + token.getUsername() + "\","
					+ "\"token\":\"" + token.encodeCookieValue() + "\"}", HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
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
