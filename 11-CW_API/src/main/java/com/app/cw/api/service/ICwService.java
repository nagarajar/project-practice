package com.app.cw.api.service;

import com.app.cw.api.binding.DashboardResponse;
import com.app.cw.api.binding.LoginForm;
import com.app.cw.api.binding.UnlockAccForm;
import com.app.cw.api.entity.CwEntity;

public interface ICwService {
	
	public String unlockAccount(UnlockAccForm unlockAccForm);

	public String login(LoginForm loginForm);

	public String forgotPwd(String email);
	
	public CwEntity updateProfile(CwEntity cwEntity);
	
	public DashboardResponse dashboardDisply();
}
