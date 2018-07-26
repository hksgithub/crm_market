package com.dao;

import java.util.List;

import com.entity.BusinessStaff;
import com.entity.TmallLicense;

public interface BusinessStaffDao {

	void save(BusinessStaff  businessStaff);

	Object findWith(String shopName,String month);

	void updateShop(BusinessStaff businessStaff);
}
