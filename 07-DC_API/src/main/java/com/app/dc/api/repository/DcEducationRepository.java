package com.app.dc.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dc.api.entities.DcEducationEntity;

public interface DcEducationRepository extends JpaRepository<DcEducationEntity, Serializable> {
	
	DcEducationEntity findByCaseIdFk(Integer caseIdFk);
}
