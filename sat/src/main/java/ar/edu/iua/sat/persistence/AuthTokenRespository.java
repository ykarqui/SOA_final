package ar.edu.iua.sat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.sat.model.AuthToken;

@Repository
public interface AuthTokenRespository extends JpaRepository<AuthToken, String> {

}
