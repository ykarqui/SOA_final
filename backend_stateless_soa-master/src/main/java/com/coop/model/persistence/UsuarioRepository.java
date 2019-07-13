package com.coop.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	
	//https://docs.spring.io/spring-data/jpa/docs/2.1.3.RELEASE/reference/html/#repositories.query-methods.details (ver Table 3. Supported keywords inside method names)
	public List<Usuario> findByFirstNameLikeAndEnabledTrueOrderByUsername(String parteDelNombre);

	public List<Usuario> findByUsernameOrEmail(String username, String email);
	
	public List<Usuario> findByUsername(String username);

}
