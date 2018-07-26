package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.entity.Sohu;
 
@Service
public interface SohuService extends BaseService<Integer,Sohu>{
	Object findWith(String day);
	Map<String, String> selectDate(String last_date,String day,String start_date,String end_date);
 
}
