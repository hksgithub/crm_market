package com.dao;

import org.springframework.stereotype.Repository;

import com.entity.WeiboFans; 
@Repository
public interface WeiboFansDao extends BaseDao<Integer,WeiboFans>{
	Object findWith(String day);	
}
