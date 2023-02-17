package com.app.cw.api.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.cw.api.entity.EdEligDtlsEntity;

public interface EdEligRepository extends JpaRepository<EdEligDtlsEntity, Serializable>{
	
	EdEligDtlsEntity findByCaseNum(Integer caseNum);

	List<EdEligDtlsEntity> findByPlanStatus(String status);
}
