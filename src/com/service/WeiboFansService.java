package com.service;

import org.springframework.stereotype.Service;

import com.entity.WeiboFans;
@Service
public interface WeiboFansService extends BaseService<Integer,WeiboFans>{
	Object findWith(String day);
}
