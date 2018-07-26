package com.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.entity.HeadlineToday;
@Service
public interface HeadlineTodayService extends BaseService<Integer,HeadlineToday>{
	Object findWith(String day);
	Map<String, String> selectDate(String last_date,String day,String start_date,String end_date);
}
