package com.app.dc.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dc.api.entities.DcIncomeEntity;

public interface DcIncomeRepository extends JpaRepository<DcIncomeEntity, Serializable>
{
	DcIncomeEntity findByCaseIdFk(Integer caseIdFk);
}
