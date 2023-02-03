package com.app.dc.api.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dc.api.entity.KidsDetails;

public interface KidsDetailsRepository extends JpaRepository<KidsDetails, Serializable> {

}
