package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Qianniu; 
@Repository
public interface QianniuDao extends BaseDao<Integer,Qianniu>{
	Object findWith(String day);
	List<Qianniu> selectDate(String start_date, String end_date);
}
