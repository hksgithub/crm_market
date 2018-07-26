package com.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.dao.QianniuDao;
import com.entity.Qianniu; 
@Repository
public class QianniuDaoImpl extends BaseDaoImpl<Integer,Qianniu> implements QianniuDao {
 
	public QianniuDaoImpl(){		
		super(Qianniu.class);
	}
 
	@Override
	public Object findWith(String day) {
		String sql = "select * from t_qianniu_day_report where day = '"+day+"'";    
		Session session = getSession();
		Object qianniu = session.createSQLQuery(sql).addEntity(Qianniu.class).uniqueResult();
		return qianniu;
	}
 
  	public List<Qianniu> selectDate(String start_date,String day) {
	    String sql = "select * from t_qianniu_day_report where day >= '"+start_date+"' AND day<'"+day+"' ORDER BY day asc";    
	    Session session = getSession();
	    @SuppressWarnings("unchecked")
		List<Qianniu> list = session.createSQLQuery(sql).addEntity(Qianniu.class).list();
	    return list;
	}
 
}
