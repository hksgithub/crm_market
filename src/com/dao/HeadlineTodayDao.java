package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.HeadlineToday; 
@Repository
public interface HeadlineTodayDao extends BaseDao<Integer,HeadlineToday>{
	Object findWith(String day);
	List<HeadlineToday> selectDate(String start_date, String end_date);
}
