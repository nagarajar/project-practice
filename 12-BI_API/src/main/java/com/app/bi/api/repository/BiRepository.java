package com.app.bi.api.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.bi.api.entity.BiEntity;
import com.app.bi.api.entity.EdEligDtlsEntity;

public interface BiRepository extends JpaRepository<BiEntity, Serializable>{

}
