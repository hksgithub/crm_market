package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.WeiboArticle; 
@Repository
public interface WeiboArticleDao extends BaseDao<Integer,WeiboArticle>{
	Object findWith(String day);
	List<Map<String, String>> selectDate(String start_date, String end_date);
}
