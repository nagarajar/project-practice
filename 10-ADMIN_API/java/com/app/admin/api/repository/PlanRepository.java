package com.app.admin.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.admin.api.entity.PlanEntity;

public interface PlanRepository extends JpaRepository<PlanEntity, Serializable>
{

}
