package com.dao;

import java.util.List;

import com.entity.BusinessStaff;
import com.entity.ShopLink;
import com.entity.ShopStatus;

public interface ShopLinkDao {

	void save(ShopLink  shopLink);
	
	Object findWith(String shopName);
	void updateShop(ShopLink shopLink);
	
	void save(ShopStatus shopStatus);
	
	void updateStatus(ShopStatus shopStatus);
	
	Object findStatus(String keyWord);
	
	List<ShopLink> findShopLink(String shopType);
	
	void updateShopLicense(ShopLink shopLink);
	
	public List<ShopLink> getShopName();
	
	public List getTaoShop();
	
	public boolean updateShopRateById(String id , String rate);
	
	List<ShopStatus> findSearchWord();
}
