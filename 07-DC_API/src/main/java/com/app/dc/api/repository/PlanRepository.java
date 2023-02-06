package com.app.dc.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dc.api.entities.PlanEntity;

public interface PlanRepository extends JpaRepository<PlanEntity, Serializable>
{

}
