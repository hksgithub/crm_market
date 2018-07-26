package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.PublicNumber; 
@Repository
public interface PublicNumberDao extends BaseDao<Integer,PublicNumber>{
	Object findWith(String user,String day);	
	List<PublicNumber> selectDate(String user,String start_date, String end_date);
}
