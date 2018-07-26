package com.dao.impl;

import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.dao.WeiboArticleDao;
import com.entity.WeiboArticle;

@Repository
public class WeiboArticleDaoImpl extends BaseDaoImpl<Integer,WeiboArticle> implements WeiboArticleDao {
 
	public WeiboArticleDaoImpl(){		 
		super(WeiboArticle.class);
	}

	@Override
	public Object findWith(String day) {
		String sql = "select * from t_weibo_article_day_report where day = '"+day+"'";    
		Session session = getSession();
		Object weiboArticle = session.createSQLQuery(sql).addEntity(WeiboArticle.class).uniqueResult();
		return weiboArticle;
	}
 
	public List <Map<String,String>> selectDate(String start_date,String day) {
	    String sql = "select a.DAY,a.PUBLIC_NUM,f.CARE_NUM,f.CANCLE_NUM,f.TOTAL_NUM,p.COMMENT_NUM,p.READ_NUM,p.FORWARDING_NUM,p.PRAISE_NUM "
	    		+ "from t_weibo_article_day_report a INNER JOIN t_weibo_fans_day_report f ON a.`DAY` = f.`DAY` "
	    		+ "INNER JOIN t_weibo_praise_day_report p ON a.`DAY` = p.`DAY` where a.day>='"+start_date+"' and "
	    		+ "a.`DAY` < '"+day+"' and f.`DAY` >= '"+start_date+"' and f.`DAY` < '"+day+"' and "
	    		+ "p.`DAY` >= '"+start_date+"' and p.`DAY` < '"+day+"' ORDER BY a.day asc";    
	    Session session = getSession();
	    @SuppressWarnings("unchecked")
		List <Map<String,String>> list = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return list;
	  }


}
