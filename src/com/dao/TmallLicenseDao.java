package com.dao;

import java.util.List;

import com.entity.Sohu;
import com.entity.TmallLicense;

public interface TmallLicenseDao {
   
	void save(TmallLicense  tmallLicense);

	List<TmallLicense> findWith(String tmall_shop_name);

	void updateShop(TmallLicense tmallLicense);
}
