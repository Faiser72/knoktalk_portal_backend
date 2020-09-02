package com.vetologic.ktap.model.repository.users;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vetologic.ktap.beans.users.AdminBean;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public AdminBean getUserByName(String username) {
		Session session = getSession();
		AdminBean user = null;
		try {
			Query<AdminBean> query = session.createQuery("FROM AdminBean WHERE activeFlag=?0 AND username=?1",
					AdminBean.class);
			query.setParameter(0, 1);
			query.setParameter(1, username);
			user = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public boolean update(Object object) {
		Session session = getSession();
		try {
			session.update(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public AdminBean getUserDetailsByEmailId(String emailId) {
		Session session = getSession();
		AdminBean user = null;
		try {
			Query<AdminBean> query = session.createQuery("FROM AdminBean WHERE activeFlag=?0 AND emailId=?1",
					AdminBean.class);
			query.setParameter(0, 1);
			query.setParameter(1, emailId);
			user = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
