package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.entity.PublicNumber;
@Service
public interface PublicNumberService extends BaseService<Integer,PublicNumber>{	
	Object findWith(String user,String day);
	Map<String, String> selectDate(String user,String last_date,String day,String start_date,String end_date);
}
