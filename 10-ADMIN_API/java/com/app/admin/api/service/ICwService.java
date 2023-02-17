package com.app.admin.api.service;

import java.util.List;

import com.app.admin.api.binding.CwRequest;
import com.app.admin.api.binding.CwResponse;
import com.app.admin.api.entity.CwEntity;

public interface ICwService {
	Integer createCw(CwRequest cwRequest);
	CwResponse findByCwId(Integer id);
	List<CwEntity> getAllCws();
	CwEntity updateCw(CwResponse cwResponse);
	String activateOrDeactivateCw(Integer id);
	String deleteByCwId(Integer id);
}
