package ar.edu.iua.sat.business;

import ar.edu.iua.sat.model.AuthToken;

public interface IAuthTokenBusiness {
	public AuthToken save(AuthToken at) throws BusinessException;

	public AuthToken load(String series) throws BusinessException, NotFoundException;

	public void delete(AuthToken at) throws BusinessException;

}
