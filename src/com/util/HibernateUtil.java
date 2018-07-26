package com.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateUtil {
	public static Session getSessionFactory() {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory)ctx.getBean("sessionFactory");
        Session session = sessionFactory.openSession();
        return session;  
    }  
}
