package com.vetologic.ktap.model.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vetologic.ktap.beans.users.AdminBean;
import com.vetologic.ktap.model.repository.users.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public AdminBean getUserByName(String username) {
		return adminRepository.getUserByName(username);
	}
	
	@Transactional
	@Override
	public boolean update(Object object) {
		return adminRepository.update(object);
	}
	
	@Override
	public AdminBean getUserDetailsByEmailId(String emailId) {
		return adminRepository.getUserDetailsByEmailId(emailId);
	}
}
