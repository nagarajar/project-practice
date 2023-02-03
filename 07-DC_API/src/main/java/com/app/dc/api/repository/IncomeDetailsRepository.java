package com.app.dc.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dc.api.entity.IncomeDetails;

public interface IncomeDetailsRepository extends JpaRepository<IncomeDetails, Serializable>
{

}
