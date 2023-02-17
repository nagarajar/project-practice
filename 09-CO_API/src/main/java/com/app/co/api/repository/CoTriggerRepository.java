package com.app.co.api.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.co.api.entity.CoTrgEntity;

public interface CoTriggerRepository extends JpaRepository<CoTrgEntity, Serializable>{

	List<CoTrgEntity> findByTrgStatus(String trgStatus);
}
