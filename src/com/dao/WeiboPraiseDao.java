package com.dao;

import org.springframework.stereotype.Repository;

import com.entity.WeiboPraise; 
@Repository
public interface WeiboPraiseDao extends BaseDao<Integer,WeiboPraise>{
	Object findWith(String day);	
}
