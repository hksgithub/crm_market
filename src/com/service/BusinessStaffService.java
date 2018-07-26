package com.service;

import java.util.List;

import com.entity.BusinessStaff;
import com.entity.TmallLicense;

public interface BusinessStaffService {
   
	void save(BusinessStaff businessStaff);
	
     Object findWith(String shopName,String month);
	
	void updateShop(BusinessStaff businessStaff);
}
