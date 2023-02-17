package com.app.admin.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.admin.api.entity.CwEntity;

public interface CwRepository extends JpaRepository<CwEntity, Serializable> {

	CwEntity findByEmail(String email);

}
