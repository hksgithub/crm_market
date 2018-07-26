package com.dao.impl;

import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.dao.WeiboFansDao;
import com.entity.WeiboFans; 
@Repository
public class WeiboFansDaoImpl extends BaseDaoImpl<Integer,WeiboFans> implements WeiboFansDao {
 
	public WeiboFansDaoImpl(){
		super(WeiboFans.class);
	}

	@Override
	public Object findWith(String day) {
		String sql = "select * from t_weibo_fans_day_report where day = '"+day+"'";    
		Session session = getSession();
		Object weiboFans = session.createSQLQuery(sql).addEntity(WeiboFans.class).uniqueResult();
		return weiboFans;
	}
 
}
