package com.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.dao.PublicNumberDao;
import com.entity.PublicNumber; 
@Repository
public class PublicNumberDaoImpl extends BaseDaoImpl<Integer,PublicNumber> implements PublicNumberDao {
	
	public PublicNumberDaoImpl(){
		super(PublicNumber.class);
	}

	@Override
	public Object findWith(String user,String day) {
		String sql = "select * from t_public_number_day_report where uid = '"+user+"' and day = '"+day+"'";    
		Session session = getSession();
		Object publicNumber = session.createSQLQuery(sql).addEntity(PublicNumber.class).uniqueResult();
		return publicNumber;
	}
 
	public List<PublicNumber> selectDate(String user,String start_date,String day) {
	    String sql = "select * from t_public_number_day_report where "
	    		+ "uid = '"+user+"' and day >= '"+start_date+"' AND day<'"+day+"' ORDER BY day asc";    
	    Session session = getSession();
	    @SuppressWarnings("unchecked")
		List<PublicNumber> list = session.createSQLQuery(sql).addEntity(PublicNumber.class).list();
	    return list;
	 }

}
