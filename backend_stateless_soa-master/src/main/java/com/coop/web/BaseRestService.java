package com.coop.web;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.coop.business.BusinessException;
import com.coop.business.IAuthTokenBusiness;
import com.coop.model.AuthToken;
import com.coop.model.Usuario;

public class BaseRestService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	protected Usuario getUserLogged() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (Usuario) auth.getPrincipal();
	}

	@Value("${app.session.token.timeout}")
	private int sessionTimeout;

	protected JSONObject userToJsonObject(Usuario usuario) {
		AuthToken token = new AuthToken(sessionTimeout, usuario.getUsername());
		String tokenValue=null;
		try {
			authTokenService.save(token);
			tokenValue=token.encodeCookieValue();
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			
		}
		JSONObject r = new JSONObject();
		r.put("username", usuario.getUsername());
		r.put("firstname", usuario.getFirstName());
		r.put("lastname", usuario.getLastName());
		r.put("email", usuario.getEmail());
		r.put("roles", usuario.getAuthorities());
		r.put("authtoken",tokenValue );
		return r;
	}

	@Autowired
	private IAuthTokenBusiness authTokenService;

	protected ResponseEntity<Object> genToken(String username, int diasvalido) {
		try {
			AuthToken token = buildToken(username, diasvalido);
			return new ResponseEntity<Object>("{\"token\":\"" + token.encodeCookieValue() + "\"}", HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	protected AuthToken buildToken(String username, int diasvalido) throws BusinessException {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, diasvalido);
		AuthToken token = new AuthToken(username, c.getTime());
		authTokenService.save(token);
		return token;

	}
	
	
}
