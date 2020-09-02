package com.vetologic.ktap.model.repository.users;

import com.vetologic.ktap.beans.users.AdminBean;

public interface AdminRepository {
	
	AdminBean getUserByName(String username);
	
	boolean update(Object object);

	AdminBean getUserDetailsByEmailId(String emailId);

}
