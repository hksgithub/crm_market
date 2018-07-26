package com.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.dao.HeadlineTodayDao;
import com.entity.HeadlineToday; 
@Repository
public class HeadlineTodayDaoImpl extends BaseDaoImpl<Integer,HeadlineToday> implements HeadlineTodayDao {
	
	public HeadlineTodayDaoImpl(){
		super(HeadlineToday.class);
	}

	@Override
	public Object findWith(String day) {
		String sql = "select * from t_headline_today_day_report where day = '"+day+"'";    
		Session session = getSession();
		Object headlineToday = session.createSQLQuery(sql).addEntity(HeadlineToday.class).uniqueResult();
		return headlineToday;
	}
 
	public List<HeadlineToday> selectDate(String start_date,String end_date) {
		String sql = "select * from t_headline_today_day_report where day >= '"+start_date+"' AND day<'"+end_date+"' ORDER BY day asc";    
		Session session = getSession();
		@SuppressWarnings("unchecked")
		List<HeadlineToday> headlineTodaylist = session.createSQLQuery(sql).addEntity(HeadlineToday.class).list();
		return headlineTodaylist;
	}
 
 
}
