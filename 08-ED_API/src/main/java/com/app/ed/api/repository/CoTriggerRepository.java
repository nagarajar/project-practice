package com.app.ed.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ed.api.entity.CoTrgEntity;
import com.app.ed.api.entity.EdEligDtlsEntity;

public interface CoTriggerRepository extends JpaRepository<CoTrgEntity, Serializable>{

}
