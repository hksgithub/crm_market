package com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.WeiboPraiseDao;
import com.entity.WeiboPraise;
import com.service.WeiboPraiseService;
@Component
public class WeiboPraiseServiceImpl extends BaseServiceImpl<Integer,WeiboPraise> implements WeiboPraiseService {
	// 注入serviceDao
	@Resource
    private WeiboPraiseDao weiboPraiseDao;
    
	@Override
	public Object findWith(String day) {
		return weiboPraiseDao.findWith(day);
	}
 
}
