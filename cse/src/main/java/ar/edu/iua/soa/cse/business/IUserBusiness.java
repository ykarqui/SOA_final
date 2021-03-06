package ar.edu.iua.soa.cse.business;

import java.util.List;

import ar.edu.iua.soa.cse.model.User;
import ar.edu.iua.soa.cse.model.dto.LoginDTO;
import ar.edu.iua.soa.cse.model.dto.UserDTO;


public interface IUserBusiness {
	public User load(long id) throws BusinessException, NotFoundException;
	public User add(User user) throws BusinessException;
	public void delete(long id) throws BusinessException;
	public User update(User user) throws BusinessException;
	public List<User> list() throws BusinessException;
	public User load(String usernameOrEmail) throws BusinessException, NotFoundException;
	public User check(LoginDTO user) throws BusinessException, NotFoundException;
	public boolean generateToken(String username) throws BusinessException, NotFoundException;
	boolean checkToken(String username) throws BusinessException, NotFoundException, UnauthorizedException;
}
