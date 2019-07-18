package ar.edu.iua.sat.business;

import javax.security.auth.message.AuthException;

import org.springframework.stereotype.Service;

@Service
public class CheckBasicAuthorization {
	
	public boolean check(String auth) throws AuthException {
		if(auth.equals("Basic Z2V0dG9rZW46ZzN0dDBrM24=")) {
			return true;
		}else
			throw new AuthException("Invalid Credentials");
	}
}
