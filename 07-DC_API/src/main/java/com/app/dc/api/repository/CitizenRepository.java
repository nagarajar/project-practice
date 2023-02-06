package com.app.dc.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dc.api.entities.CitizenEntity;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Serializable> {

}
