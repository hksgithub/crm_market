package com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.WeiboFansDao;
import com.entity.WeiboFans;
import com.service.WeiboFansService;
@Component
public class WeiboFansServiceImpl extends BaseServiceImpl<Integer,WeiboFans> implements WeiboFansService {
	// 注入serviceDao
	@Resource
    private WeiboFansDao weiboFansDao;
    
	@Override
	public Object findWith(String day) {
		return weiboFansDao.findWith(day);
	}
 
}
