package com.ar.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ar.api.entity.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Serializable> {

}
