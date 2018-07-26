package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


import com.dao.TanJiDao;
import com.entity.TanJi;

public class TanJiDaoImpl extends HibernateDaoSupport implements TanJiDao {

	private SessionFactory sessionFactory;

	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	public Session getSession() {
		if (sessionFactory == null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
			sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		}
		Session session = sessionFactory.openSession();
		return session;
	}
	
	@Override
	public boolean saveTanJi(TanJi tanji) {
		try {
			Session session = this.getSession();
			session.beginTransaction();
			session.save(tanji);
			session.getTransaction().commit();
			session.clear();
			session.close(); // 关闭session
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeTanji(String companyName) {
		try {
			String sql = "delete from t_tanji where companyname=:companyName";
			Session session = this.getSession();
			session.beginTransaction();
			session.createSQLQuery(sql).addEntity(TanJi.class).setParameter("companyName", companyName).
			executeUpdate();
			session.getTransaction().commit();
			session.clear();
			session.close(); // 关闭session
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean getTanJi(String companyName) {		
		try {
			List<TanJi> tanJi = new ArrayList<TanJi>();
			String sql = "select * from t_tanji where companyname=:companyName";
			Session session = this.getSession();
			session.beginTransaction();
			tanJi = session.createSQLQuery(sql).addEntity(TanJi.class).setParameter("companyName", companyName).
			list();
			session.getTransaction().commit();
			session.clear();
			session.close(); // 关闭session
			if(tanJi.size() > 0){
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

}
