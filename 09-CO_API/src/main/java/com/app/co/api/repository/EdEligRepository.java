package com.app.co.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.co.api.entity.EdEligDtlsEntity;

public interface EdEligRepository extends JpaRepository<EdEligDtlsEntity, Serializable>{
	
	EdEligDtlsEntity findByCaseNum(Integer caseNum);
}
