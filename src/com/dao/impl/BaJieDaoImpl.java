package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.dao.BaJieDao;
import com.entity.BaJieDailyCost;
import com.entity.BaJieKeyWord;

public class BaJieDaoImpl extends HibernateDaoSupport implements BaJieDao {

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
	public boolean saveBaJieKeyWord(BaJieKeyWord baJieKW) {
		try {
			Session session = this.getSession();
			session.beginTransaction();
			session.save(baJieKW);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBaJieDailyCost(BaJieDailyCost baJieDC) {
		try {
			Session session = this.getSession();
			session.beginTransaction();
			session.save(baJieDC);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isExistKeyWordByOrderNo(String orderNo) {		
		try {
			List<BaJieKeyWord> bajie = new ArrayList<BaJieKeyWord>();
			String sql = "select * from t_bajie_keyword where ORDERNO=:orderNo";
			Session session = this.getSession();
			session.beginTransaction();
			bajie = session.createSQLQuery(sql).addEntity(BaJieKeyWord.class).setParameter("orderNo", orderNo).
			list();
			session.getTransaction().commit();
			session.clear();
			session.close(); // 关闭session
			if(bajie.size() > 0){
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean isExistDailyCostByDate(String perDay) {		
		try {
			List<BaJieDailyCost> bajie = new ArrayList<BaJieDailyCost>();
			String sql = "select * from t_bajie_daily_cost where DATE=:perDay";
			Session session = this.getSession();
			session.beginTransaction();
			bajie = session.createSQLQuery(sql).addEntity(BaJieDailyCost.class).setParameter("perDay", perDay).
			list();
			session.getTransaction().commit();
			session.clear();
			session.close(); // 关闭session
			if(bajie.size() > 0){
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

}
