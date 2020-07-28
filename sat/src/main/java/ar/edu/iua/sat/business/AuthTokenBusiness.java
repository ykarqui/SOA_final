package ar.edu.iua.sat.business;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.sat.model.AuthToken;
import ar.edu.iua.sat.persistence.AuthTokenRespository;


@Service
public class AuthTokenBusiness implements IAuthTokenBusiness {
	//private Logger log = LoggerFactory.getLogger(this.getClass());

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
	public void check(String username, String token)
			throws BusinessException, NotFoundException, ExpiredTokenException, InvalidCookieData{
		Optional<AuthToken> atO;
		String[] data;
		try {
			data = decodeCookie(token);
			atO = authTokenDAO.findById(data[0]);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!atO.isPresent())
			throw new NotFoundException("No se encuentra el token de autenticación serie=" + username);
		AuthToken at = atO.get();
		if(at.getUsername().equals(username)&&at.getToken().equals(data[1])) {
			Calendar today = Calendar.getInstance();
			today.setTime(new Date());
			if(today.getTime().compareTo(at.getTo())<0) {
			} else {
				throw new ExpiredTokenException();
			}
		} else {
			throw new InvalidCookieData();
		}
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
	
	private String[] decodeCookie(String cookie) {
		StringBuilder dsb = new StringBuilder(new String(Base64.getDecoder().decode(cookie.getBytes())));
		return dsb.toString().split(":");
	}
}
