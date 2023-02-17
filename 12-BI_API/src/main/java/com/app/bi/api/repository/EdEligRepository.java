package com.app.bi.api.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.bi.api.entity.EdEligDtlsEntity;

public interface EdEligRepository extends JpaRepository<EdEligDtlsEntity, Serializable>{
	
	List<EdEligDtlsEntity> findByPlanStatusAndPlanEndDateAfter(String status, LocalDate today);
}
