package com.service.impl;

import java.util.List;

import com.dao.TmallLicenseDao;
import com.entity.Sohu;
import com.entity.TmallLicense;
import com.service.TmallLicenseService;

public class TmallLicenseServiceImpl implements TmallLicenseService{

	
	private TmallLicenseDao tmallLicenseDao;
	
    
	public void setTmallLicenseDao(TmallLicenseDao tmallLicenseDao) {
		this.tmallLicenseDao = tmallLicenseDao;
	}

	@Override
	public void save(TmallLicense  tmallLicense) {
		// TODO Auto-generated method stub
		tmallLicenseDao.save(tmallLicense);
	}

	@Override
	public List<TmallLicense> findWith(String tmall_shop_name) {
		// TODO Auto-generated method stub
		return tmallLicenseDao.findWith(tmall_shop_name);
	}

	@Override
	public void updateShop(TmallLicense tmallLicense) {
		// TODO Auto-generated method stub
		tmallLicenseDao.updateShop(tmallLicense);
	}

}
