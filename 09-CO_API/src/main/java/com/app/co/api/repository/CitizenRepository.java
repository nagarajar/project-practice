package com.app.co.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.co.api.entity.CitizenEntity;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Serializable> {

}
