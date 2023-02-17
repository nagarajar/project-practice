package com.app.ed.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ed.api.entity.CitizenEntity;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Serializable> {

}
