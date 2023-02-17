package com.app.ed.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ed.api.entity.PlanEntity;

public interface PlanRepository extends JpaRepository<PlanEntity, Serializable>
{

}
