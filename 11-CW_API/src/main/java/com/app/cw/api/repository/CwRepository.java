package com.app.cw.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.cw.api.entity.CwEntity;

public interface CwRepository extends JpaRepository<CwEntity, Serializable> {

	CwEntity findByEmail(String email);
	
	CwEntity findByEmailAndPassword(String email, String password);

}
