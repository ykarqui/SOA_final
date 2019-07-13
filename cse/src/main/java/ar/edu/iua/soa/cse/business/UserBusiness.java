package ar.edu.iua.soa.cse.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import ar.edu.iua.soa.cse.model.User;
import ar.edu.iua.soa.cse.persistence.UserRepository;

@Service
public class UserBusiness implements IUserBusiness {

	@Autowired
	private UserRepository userDAO;
	
	@Autowired
	private PasswordEncoder pe;

	@Override
	public User load(long id) throws BusinessException, NotFoundException {
		Optional<User> o;
		try {
			o = userDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (o.isPresent())
			return o.get();
		else
			throw new NotFoundException();
	}
	
	@Override
	public User check(User user) throws BusinessException, NotFoundException {
		User o;
		try {
			o = userDAO.findByUsername(user.getUsername());
			System.out.println("Usuario: " + o.getUsername());
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (o != null) {
			if(pe.matches(user.getPassword(), o.getPassword())) {
				return o;
			}
		}
		throw new NotFoundException();
	}

	@Override
	public User add(User user) throws BusinessException {
		try {
			user.setPassword(pe.encode(user.getPassword()));
			return userDAO.save(user);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(long id) throws BusinessException {
		try {
			userDAO.deleteById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public User update(User user) throws BusinessException {

		try {
			return userDAO.save(user);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public List<User> list() throws BusinessException {
		try {
			return userDAO.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public User load(String usernameOrEmail) throws BusinessException, NotFoundException {
		List<User> l;
		try {
			l=userDAO.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(),e);
		}
		if(l.size()==0)
			throw new NotFoundException("No se encuentra el usuari@ con nombre/email="+usernameOrEmail);
		
		return l.get(0);
	}

}
