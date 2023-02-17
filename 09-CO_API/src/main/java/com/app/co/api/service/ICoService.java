package com.app.co.api.service;

import com.app.co.api.entity.CoBatchResponseEntity;

public interface ICoService {
	
	CoBatchResponseEntity processPendingTriggers();
}
