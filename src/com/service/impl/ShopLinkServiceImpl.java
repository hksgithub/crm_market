package com.service.impl;

import java.util.List;

import com.dao.ShopLinkDao;
import com.entity.ShopLink;
import com.entity.ShopStatus;
import com.service.ShopLinkService;

public class ShopLinkServiceImpl implements ShopLinkService {

	private ShopLinkDao shopLinkDao;

	public void setShopLinkDao(ShopLinkDao shopLinkDao) {
		this.shopLinkDao = shopLinkDao;
	}

	@Override
	public void save(ShopLink shopLink) {
		// TODO Auto-generated method stub
        shopLinkDao.save(shopLink);
	}

	@Override
	public Object findWith(String shopName) {
	  return shopLinkDao.findWith(shopName);
	}

	@Override
	public void updateShop(ShopLink shopLink) {
		// TODO Auto-generated method stub
		shopLinkDao.updateShop(shopLink);
	}

	@Override
	public void save(ShopStatus shopStatus) {
       
		shopLinkDao.save(shopStatus);
	}

	@Override
	public Object findStatus(String keyWord) {
		// TODO Auto-generated method stub
       return shopLinkDao.findStatus(keyWord);
	}

	@Override
	public List<ShopLink> findShopLink(String shopType) {
		// TODO Auto-generated method stub
		return shopLinkDao.findShopLink(shopType);
	}

	@Override
	public void updateShopLicense(ShopLink shopLink) {
		
        shopLinkDao.updateShopLicense(shopLink);
		
	}

	@Override
	public void updateStatus(ShopStatus shopStatus) {
		// TODO Auto-generated method stub
		shopLinkDao.updateStatus(shopStatus);
	}

	@Override
	public List<ShopLink> getShopName(){
		return shopLinkDao.getShopName();
	}
	
	@Override
	public List getTaoShop(){
		return shopLinkDao.getTaoShop();
	}
	
	@Override
	public boolean updateShopRateById(String id , String rate){
		return shopLinkDao.updateShopRateById(id, rate);
	}
	@Override
	public List<ShopStatus> findSearchWord() {
		// TODO Auto-generated method stub
		return shopLinkDao.findSearchWord();
	}
}
