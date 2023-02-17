package com.app.ed.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ed.api.entity.DcEducationEntity;

public interface DcEducationRepository extends JpaRepository<DcEducationEntity, Serializable> {
	
	DcEducationEntity findByCaseIdFk(Integer caseIdFk);
}
