package ar.edu.iua.soa.cse.business;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.edu.iua.soa.cse.model.User;
import ar.edu.iua.soa.cse.model.dto.LoginDTO;
import ar.edu.iua.soa.cse.model.dto.UserDTO;
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
	public User check(LoginDTO user) throws BusinessException, NotFoundException {
		User o;
		try {
			o = userDAO.findByUsername(user.getUsername());
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
	public boolean generateToken(String username) throws BusinessException, NotFoundException {
		User o;
		try {
			o = userDAO.findByUsername(username);
			if(o != null) {
				getToken(o);
				update(o);
				return true;
			} else {
				throw new NotFoundException();
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public boolean checkToken(String username) throws BusinessException, NotFoundException, UnauthorizedException {
		User o;
		try {
			o = userDAO.findByUsername(username);
			if(o != null) {
				if (checkTokenSat(o).equals(200)) {
					return true;
				} else {
					throw new UnauthorizedException();
				}
			} else {
				throw new NotFoundException();
			}
		} catch (UnauthorizedException e) {
			throw new UnauthorizedException(e);
		} catch (NotFoundException e) {
			throw new NotFoundException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		} 
	}

	@Override
	public User add(User user) throws BusinessException {
		try {
			user.setPassword(pe.encode(user.getPassword()));
			getToken(user);
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
	
	private void getToken(User user) throws BusinessException {
		UserDTO udto = new UserDTO(user.getUsername(), 1);
		Gson gson = new Gson();
		String json = gson.toJson(udto);
		HttpClient httpClient = HttpClientBuilder.create().build();
		String responseString = "";
		try {
			HttpPost request = new HttpPost("http://sat:8094/sat/v1/authtoken");
			StringEntity body = new StringEntity(json);
			request.addHeader("content-type", "application/json");
			request.addHeader("Authorization", getAuthorization()); 
			request.setEntity(body);
			
			HttpResponse response = httpClient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(responseString);
			
			String[] token = jsonObject.get("token").toString().split("\"");
			user.setToken(token[1]);
			
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}
	
	private Integer checkTokenSat(User user) throws BusinessException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpGet request = new HttpGet("http://sat:8094/sat/v1/authtoken");
			request.addHeader("username", user.getUsername());
			request.addHeader("auth-token", user.getToken());
			
			HttpResponse response = httpClient.execute(request);
			return response.getStatusLine().getStatusCode();
			
		} catch (Exception ex) {
			throw new BusinessException();
		}
	}
	
	private String getAuthorization() {
		String auth = "gettoken:g3tt0k3n";
		StringBuilder sb = new StringBuilder(new String(Base64.getEncoder().encode(auth.getBytes())));
		String encodedAuth = "Basic " + sb.toString();
		return encodedAuth;
	}
}
