package com.service;

import java.util.List;

import com.entity.Sohu;
import com.entity.TmallLicense;

public interface TmallLicenseService {
     
	void save(TmallLicense tmallLicense);
	
	 List<TmallLicense> findWith(String tmall_shop_name);
	
	void updateShop(TmallLicense tmallLicense);
}
