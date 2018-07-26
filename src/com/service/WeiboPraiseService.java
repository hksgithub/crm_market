package com.service;

import org.springframework.stereotype.Service;

import com.entity.WeiboPraise;
@Service
public interface WeiboPraiseService extends BaseService<Integer,WeiboPraise>{
	Object findWith(String day);
}
