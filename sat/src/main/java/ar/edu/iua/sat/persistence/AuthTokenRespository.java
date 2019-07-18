package ar.edu.iua.sat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.iua.sat.model.AuthToken;

@Repository
public interface AuthTokenRespository extends JpaRepository<AuthToken, String> {
	
	@Query(value="SELECT * FROM sat.auth_token WHERE username=? AND token=? ORDER BY end DESC LIMIT 1", nativeQuery=true)
	public AuthToken findByUsernameAndToken(String username, String token);
}
