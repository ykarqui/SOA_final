package ar.edu.iua.soa.cse.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.soa.cse.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByUsernameOrEmail(String username, String email);
	public User findByUsername(String username);
}
