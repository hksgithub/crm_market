package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.entity.Qianniu;
@Service
public interface QianniuService extends BaseService<Integer,Qianniu>{
	Object findWith(String day);
	Map<String, String> selectDate(String last_date,String day,String start_date,String end_date);
}
