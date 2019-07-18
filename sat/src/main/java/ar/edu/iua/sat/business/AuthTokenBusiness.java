package ar.edu.iua.sat.business;

import java.security.InvalidParameterException;
import java.util.Base64;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ar.edu.iua.sat.model.AuthToken;
import ar.edu.iua.sat.persistence.AuthTokenRespository;


@Service
public class AuthTokenBusiness implements IAuthTokenBusiness {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthTokenRespository authTokenDAO;

	@Override
	public AuthToken save(AuthToken at) throws BusinessException {
		try {
			return authTokenDAO.save(at);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public AuthToken check(String username, String token) throws BusinessException{
		AuthToken at;
		try {
			at = authTokenDAO.findByUsernameAndToken(username, token);
			System.out.println(at);
		} catch (Exception e) {
			System.out.println("Catch business");
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		return at;
	}

	@Override
	public AuthToken load(String series) throws BusinessException, NotFoundException {
		Optional<AuthToken> atO;
		try {
			atO = authTokenDAO.findById(series);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!atO.isPresent())
			throw new NotFoundException("No se encuentra el token de autenticación serie=" + series);
		return atO.get();
	}

	@Override
	public void delete(AuthToken at) throws BusinessException {
		try {
			authTokenDAO.delete(at);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}
}
