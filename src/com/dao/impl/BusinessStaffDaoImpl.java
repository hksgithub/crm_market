package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.dao.BusinessStaffDao;
import com.entity.BusinessStaff;
import com.entity.TmallLicense;

public class BusinessStaffDaoImpl extends HibernateDaoSupport implements BusinessStaffDao{

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
	public void save(BusinessStaff businessStaff) {
		
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务  
		session.save(businessStaff);
		session.getTransaction().commit(); // 提交事务  
		session.clear();
	    session.close(); // 关闭session
	}

	@Override
	public Object findWith(String shopName,String month) {
		String sql = "select * from t_store_turnover where shop_name = '"+shopName+"' AND month_date='"+month+"'";    
		Session session = this.getSession();
		Object  businessStaffs = session.createSQLQuery(sql).addEntity(BusinessStaff.class).uniqueResult();
		session.clear();
	    session.close(); // 关闭session
		return businessStaffs;
	}

	@Override
	public void updateShop(BusinessStaff businessStaff) {
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务
		String shopName=businessStaff.getShop_name();
		String sql = "update t_store_turnover p set p.customer_price = ?,p.payment_amount=?,p.payments_number=?,p.USER_ACCOUNT=? where SHOP_NAME='"+shopName+"'";
		Query query = session.createSQLQuery(sql);
		query.setString(0, businessStaff.getCustomer_price());
        query.setString(1, businessStaff.getPayment_amount());
        query.setInteger(2, businessStaff.getPayments_number());
        query.setString(3, businessStaff.getUser_account());
		query.executeUpdate();
		session.getTransaction().commit(); // 提交事务  
		session.clear();
	    session.close(); // 关闭session
		
	}

}
