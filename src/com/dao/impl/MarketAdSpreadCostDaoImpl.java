package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.dao.MarketAdSpreadCostDao;
import com.entity.MarketAdSpreadCost;
import com.entity.TanJi;

public class MarketAdSpreadCostDaoImpl extends HibernateDaoSupport implements MarketAdSpreadCostDao {

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
	public boolean saveAdCost(MarketAdSpreadCost cost) {
		try {
			Session session = this.getSession();
			session.beginTransaction(); // 开启事务
			session.save(cost);
			session.getTransaction().commit(); // 提交事务
			session.clear();
			session.close(); // 关闭session
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
		return false;		
	}

	@Override
	public boolean findAdCostBySourceAndDay(String source, String day) {
		try {
			String sql = "select * from t_market_ad where DATASOURCE=:source and DAY=:day";
			List<MarketAdSpreadCost> cost = new ArrayList<MarketAdSpreadCost>();
			Session session = this.getSession();
			session.beginTransaction(); // 开启事务
			cost = session.createSQLQuery(sql).addEntity(MarketAdSpreadCost.class).setParameter("source", source).
					setParameter("day", day).list();
			session.getTransaction().commit();
			session.clear();
			session.close(); // 关闭session
			if(cost.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return false;
	}

}
