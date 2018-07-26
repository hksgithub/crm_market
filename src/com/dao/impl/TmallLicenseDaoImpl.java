package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.dao.TmallLicenseDao;
import com.entity.Sohu;
import com.entity.TmallLicense;

public class TmallLicenseDaoImpl extends HibernateDaoSupport  implements TmallLicenseDao {
	private SessionFactory sessionFactory;
	
	public void setSessionFacotry(SessionFactory sessionFacotry) {  
	      super.setSessionFactory(sessionFacotry);  
	}
	
	public Session getSession(){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory)ctx.getBean("sessionFactory");
        Session session = sessionFactory.openSession();
        return session;
	}
	
	@Override
	public void save(TmallLicense tmallLicense) {
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务  
		session.save(tmallLicense);
		session.getTransaction().commit(); // 提交事务  
		session.clear();
	    session.close(); // 关闭session
		
	}

	@Override
	public List<TmallLicense> findWith(String tmall_shop_name) {
		String sql = "select * from t_tmall_license where tmall_shop_name = '"+tmall_shop_name+"'";    
		Session session = this.getSession();
		List<TmallLicense> tmallLicenses = session.createSQLQuery(sql).addEntity(TmallLicense.class).list();
		session.clear();
	    session.close(); // 关闭session
		return tmallLicenses;
	}

	@Override
	public void updateShop(TmallLicense tmallLicense) {
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务  
		String sql = "update t_tmall_license p set p.business_license_address = ?,p.shop_link=?,p.tmall_shop_name=?";
		Query query = session.createSQLQuery(sql);
		query.setString(0, tmallLicense.getBusiness_license_address());
        query.setString(1, tmallLicense.getShop_link());
        query.setString(2, tmallLicense.getTmall_shop_name());
		query.executeUpdate();
		session.getTransaction().commit(); // 提交事务  
		session.clear();
	    session.close(); // 关闭session
	   
	}

}
