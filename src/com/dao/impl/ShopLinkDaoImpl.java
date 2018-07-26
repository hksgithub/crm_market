package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.dao.ShopLinkDao;
import com.entity.ShopLink;
import com.entity.ShopStatus;
import com.entity.TmallLicense;

public class ShopLinkDaoImpl extends HibernateDaoSupport implements ShopLinkDao {

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
	public void save(ShopLink shopLink) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务
		session.save(shopLink);
		session.getTransaction().commit(); // 提交事务
		session.clear();
		session.close(); // 关闭session
	}

	@Override
	public Object findWith(String shopName) {
		String sql = "select * from t_shop where SHOP_NAME = '" + shopName + "'";
		Session session = this.getSession();
		Object shopNum = session.createSQLQuery(sql).addEntity(ShopLink.class).uniqueResult();
		session.clear();
		session.close(); // 关闭session
		return shopNum;
	}

	@Override
	public void updateShop(ShopLink shopLink) {
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务
		String shopName = shopLink.getShop_name();
		if (shopName != null && shopName != "") {
			String sql = "UPDATE t_shop p SET p.SHOP_NAME=? ,p.SHOP_LINK=? ,p.UPDATE_TIME=?,p.SHOP_TYPE=?,p.WANG_WANG=?,p.SHOP_ADDRESS=?,p.MAIN_PRODUCTS=?,p.SALES_VOLUME=?,p.COMMODITY_NUM=?,p.CONSUMER_PROTECTION=?,p.SHOP_LEVEL=?,p.LOGO_URL=?,p.CATEGORY=?,p.JINPAIMAIJIA=? where SHOP_NAME= '"
					+ shopName + "'";
			Query query = session.createSQLQuery(sql);
			query.setString(0, shopLink.getShop_name());
			query.setString(1, shopLink.getShop_link());
			query.setDate(2, shopLink.getUpdate_time());
			query.setString(3, shopLink.getShop_type());
			query.setString(4, shopLink.getWang_wang());
			query.setString(5, shopLink.getShop_address());
			query.setString(6, shopLink.getMain_products());
			query.setString(7, shopLink.getSales_volume());
			query.setString(8, shopLink.getCommodity_num());
			query.setString(9, shopLink.getConsumer_protection());
			query.setString(10, shopLink.getShop_level());
			query.setString(11, shopLink.getLogo_url());
			query.setString(12, shopLink.getCategory());
			query.setString(13, shopLink.getJinPaiMaiJia());
			query.executeUpdate();
			session.getTransaction().commit(); // 提交事务
			session.clear();
			session.close(); // 关闭session
		}
	}

	@Override
	public void save(ShopStatus shopStatus) {
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务
		session.save(shopStatus);
		session.getTransaction().commit(); // 提交事务
		session.clear();
		session.close(); // 关闭session

	}

	@Override
	public Object findStatus(String keyWord) {
		String sql = "SELECT * FROM t_shop_status WHERE status is not null and runing is not null AND word='" + keyWord
				+ "'";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Object shopStatus = session.createSQLQuery(sql).addEntity(ShopStatus.class).uniqueResult();
		session.getTransaction().commit();
		return shopStatus;
	}

	@Override
	public List<ShopLink> findShopLink(String shopType) {
		String sql = "SELECT * FROM t_shop where STATUS is NULL AND SHOP_TYPE='" + shopType
				+ "' ORDER BY CREATE_TIME ASC  LIMIT 1,100";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<ShopLink> shopData = session.createSQLQuery(sql).addEntity(ShopLink.class).list();
		session.getTransaction().commit();
		return shopData;
	}

	@Override
	public void updateShopLicense(ShopLink shopLink) {
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务
		String shopName = shopLink.getShop_name();
		if (shopName != null && shopName != "") {
			String sql = "UPDATE t_shop  p set p.UPDATE_TIME=?, p.COMPANY_NAME=?,p.CORPORAT_ACCOUNT=?,p.STATUS=?  where SHOP_NAME= '"
					+ shopName + "'";
			Query query = session.createSQLQuery(sql);
			query.setDate(0, shopLink.getUpdate_time());
			query.setString(1, shopLink.getCompany_name());
			query.setString(2, shopLink.getCorporat_account());
			query.setString(3, shopLink.getStatus());
			query.executeUpdate();
			session.getTransaction().commit(); // 提交事务
			session.clear();
			session.close(); // 关闭session
		}

	}

	@Override
	public List<ShopLink> getShopName() {
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务
		String sql = "select * from t_shop where COMPANY_NAME is not null and COMPANY_NAME != ''";
		List<ShopLink> ls = session.createSQLQuery(sql).addEntity(ShopLink.class).list();
		return ls;
	}

	@Override
	public void updateStatus(ShopStatus shopStatus) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务
		String keyWord = shopStatus.getWord();
		if (keyWord != null && keyWord != "") {
			String sql = "UPDATE t_shop_status  p set  p.RUNING=?,p.END_TIME=? ,p.STATUS=?   where WORD= '" + keyWord
					+ "'";
			Query query = session.createSQLQuery(sql);
			query.setInteger(0, shopStatus.getRuning());
			query.setDate(1, shopStatus.getEnd_time());
			query.setInteger(2, shopStatus.getStatus());
			query.executeUpdate();
			session.getTransaction().commit(); // 提交事务
			session.clear();
			session.close(); // 关闭session
		}

	}

	@Override
	public List getTaoShop() {
		Session session = this.getSession();
		session.beginTransaction(); // 开启事务
		String sql = "select id,SHOP_NAME from t_shop where SHOP_TYPE = '淘宝' and (FAVORABLE_RATE is null or FAVORABLE_RATE = '')";
		List ls = session.createSQLQuery(sql).list();
		session.getTransaction().commit(); // 提交事务
		session.clear();
		session.close(); // 关闭session
		return ls;
	}

	@Override
	public boolean updateShopRateById(String id, String rate) {
		String sql = "update t_shop set FAVORABLE_RATE=:rate where id=:id";
		try {
			Session session = this.getSession();
			session.beginTransaction(); // 开启事务
			int count = session.createSQLQuery(sql).setParameter("rate", rate).setParameter("id", id).executeUpdate();
			session.getTransaction().commit(); // 提交事务
			session.clear();
			session.close(); // 关闭session
			if (count > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	@Override
	public List<ShopStatus> findSearchWord() {
		String sql = "SELECT * FROM t_shop_status where STATUS=0 AND RUNING=0 ORDER BY CREATE_TIME ASC LIMIT 0,100 " ;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<ShopStatus> searchWord = session.createSQLQuery(sql).addEntity(ShopStatus.class).list();
		session.getTransaction().commit();
		return searchWord;
	}
}