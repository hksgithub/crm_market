package com.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.dao.SohuDao;
import com.entity.Sohu; 
@Repository
public class SohuDaoImpl extends BaseDaoImpl<Integer,Sohu> implements SohuDao { 
	
	public SohuDaoImpl(){
		super(Sohu.class);
	}
 
	@Override
	public Object findWith(String day) {
		Session session = getSession();
		String sql = "select * from t_sohu_day_report where day = '"+day+"'";    
		Object sohu = session.createSQLQuery(sql).addEntity(Sohu.class).uniqueResult();
		return sohu;
	}
 
 	public List<Sohu> selectDate(String start_date,String day) {
 		Session session = getSession();
	    String sql = "select * from t_sohu_day_report where day >= '"+start_date+"' AND day<'"+day+"' ORDER BY day asc";
	    @SuppressWarnings("unchecked")
		List<Sohu> list = session.createSQLQuery(sql).addEntity(Sohu.class).list();
	    return list;
	}
 
}
