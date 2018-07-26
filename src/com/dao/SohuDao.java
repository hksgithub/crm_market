package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.entity.Sohu; 
@Repository
public interface SohuDao extends BaseDao<Integer,Sohu>{
	Object findWith(String day);
	List<Sohu> selectDate(String start_date, String end_date);
}

