package com.coop.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop.model.AuthToken;
@Repository
public interface AuthTokenRespository extends JpaRepository<AuthToken, String> {

}
