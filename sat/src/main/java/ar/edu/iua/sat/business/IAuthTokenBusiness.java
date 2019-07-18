package ar.edu.iua.sat.business;

import ar.edu.iua.sat.model.AuthToken;

public interface IAuthTokenBusiness {
	public AuthToken save(AuthToken at) throws BusinessException;
	
	public void check(String username, String token) throws BusinessException, NotFoundException, ExpiredTokenException, InvalidCookieData;

	public AuthToken load(String series) throws BusinessException, NotFoundException;

	public void delete(AuthToken at) throws BusinessException;

}
