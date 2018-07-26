package com.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.dao.WeiboPraiseDao;
import com.entity.WeiboPraise; 
@Repository
public class WeiboPraiseDaoImpl extends BaseDaoImpl<Integer,WeiboPraise> implements WeiboPraiseDao {
 
	public WeiboPraiseDaoImpl(){
		super(WeiboPraise.class);
	}

	public Object findWith(String day) {
		String sql = "select * from t_weibo_praise_day_report where day = '"+day+"'";    
		Session session = getSession();
		Object weiboPraise = session.createSQLQuery(sql).addEntity(WeiboPraise.class).uniqueResult();
		return weiboPraise;
	}
 
}
