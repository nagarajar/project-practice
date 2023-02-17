package com.app.co.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.co.api.entity.CoBatchResponseEntity;

public interface CoBatchResponseRepository extends JpaRepository<CoBatchResponseEntity, Serializable>{

}
