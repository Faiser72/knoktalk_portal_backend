package com.vetologic.ktap.jwt_security_config.model.repository;

import com.vetologic.ktap.beans.users.AdminBean;

public interface UserRepository {
	AdminBean findByUsername(String username);
}