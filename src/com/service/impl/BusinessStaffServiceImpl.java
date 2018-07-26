package com.service.impl;

import java.util.List;

import com.dao.BusinessStaffDao;
import com.entity.BusinessStaff;
import com.service.BusinessStaffService;

public class BusinessStaffServiceImpl implements BusinessStaffService{
 
	
	private BusinessStaffDao businessStaffDao;
     

	public void setBusinessStaffDao(BusinessStaffDao businessStaffDao) {
		this.businessStaffDao = businessStaffDao;
	}

	@Override
	public void save(BusinessStaff businessStaff) {
		businessStaffDao.save(businessStaff);
		
	}

	@Override
	public Object findWith(String shopName,String month) {
		
		return businessStaffDao.findWith(shopName, month);
	}

	@Override
	public void updateShop(BusinessStaff businessStaff) {
		businessStaffDao.updateShop(businessStaff);
		
	}

}
