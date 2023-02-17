package com.app.ed.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ed.api.entity.DcIncomeEntity;

public interface DcIncomeRepository extends JpaRepository<DcIncomeEntity, Serializable>
{
	DcIncomeEntity findByCaseIdFk(Integer caseIdFk);
}
