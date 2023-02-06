package com.app.dc.api.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dc.api.entities.DcKidsEntity;

public interface DcKidsRepository extends JpaRepository<DcKidsEntity, Serializable> {
	
	List<DcKidsEntity> findByCaseIdFk(Integer caseIdFk);
}
