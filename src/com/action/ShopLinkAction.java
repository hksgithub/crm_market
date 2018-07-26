package com.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.entity.ShopLink;
import com.entity.ShopStatus;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.ShopLinkService;
import com.util.DataHandling;

public class ShopLinkAction extends ActionSupport implements ModelDriven<ShopLink> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6334005125108360806L;

	protected HttpServletRequest request;

	private ShopLinkService shopLinkService;

	private Map<String, Object> jsonData = new HashMap<String, Object>();

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	public List<ShopLink> shopList = new ArrayList<ShopLink>();

	public List<ShopLink> getShopList() {
		return shopList;
	}

	public void setShopList(List<ShopLink> shopList) {
		this.shopList = shopList;
	}

	@Override
	// 模型驱动
	public ShopLink getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setShopLinkService(ShopLinkService shopLinkService) {
		this.shopLinkService = shopLinkService;
	}

	public String isShopData() throws ParseException, UnsupportedEncodingException {

		Date now = new Date();
		String uid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		HttpServletRequest request = ServletActionContext.getRequest();
		String shopName = request.getParameter("shopName");
		String shopLink = request.getParameter("shopLink");
		String shopType = request.getParameter("shopType");
		String wangwang = request.getParameter("wangwang");
		String shopAddress = request.getParameter("shopAddress");
		String mainProducts = request.getParameter("mainProducts");
		String salesVolume = request.getParameter("salesVolume");
		String commodityNum = request.getParameter("commodityNum");
		String consumerProtection = request.getParameter("consumerProtection");
		String shopLevel = request.getParameter("shopLevel");
		String logoUrl = request.getParameter("logoUrl");
		String category = request.getParameter("category");
		String jinPaiMaiJia = request.getParameter("jinPaiMaiJia");
		if ((shopName == null || shopName == "") || (shopLink == null || shopLink == "")) {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		if ((shopName != null && shopName != "") && (shopLink != null && shopLink != "")) {

			ShopLink s_shopLink = (ShopLink) shopLinkService.findWith(shopName);
			if (s_shopLink == null) {
				ShopLink shopLinkVo = new ShopLink();
				shopLinkVo.setId(uid);
				shopLinkVo.setShop_name(shopName);
				shopLinkVo.setShop_link(shopLink);
				shopLinkVo.setCreate_time(now);
				shopLinkVo.setShop_type(shopType);
				shopLinkVo.setUpdate_time(now);
				shopLinkVo.setWang_wang(wangwang);
				shopLinkVo.setShop_address(shopAddress);
				shopLinkVo.setMain_products(mainProducts);
				shopLinkVo.setSales_volume(salesVolume);
				shopLinkVo.setCommodity_num(commodityNum);
				shopLinkVo.setConsumer_protection(consumerProtection);
				shopLinkVo.setShop_level(shopLevel);
				shopLinkVo.setLogo_url(logoUrl);
				shopLinkVo.setCategory(category);
				shopLinkVo.setJinPaiMaiJia(jinPaiMaiJia);
				shopLinkService.save(shopLinkVo);
			} else {
				s_shopLink.setShop_link(shopLink);
				s_shopLink.setShop_name(shopName);
				s_shopLink.setShop_type(shopType);
				s_shopLink.setUpdate_time(now);
				s_shopLink.setWang_wang(wangwang);
				s_shopLink.setShop_address(shopAddress);
				s_shopLink.setMain_products(mainProducts);
				s_shopLink.setSales_volume(salesVolume);
				s_shopLink.setCommodity_num(commodityNum);
				s_shopLink.setConsumer_protection(consumerProtection);
				s_shopLink.setShop_level(shopLevel);
				s_shopLink.setLogo_url(logoUrl);
				s_shopLink.setCategory(category);
				s_shopLink.setJinPaiMaiJia(jinPaiMaiJia);
				shopLinkService.updateShop(s_shopLink);
			}
			jsonData = DataHandling.returnSuccess();
			return SUCCESS;
		}
		jsonData = DataHandling.returnError();
		return SUCCESS;
	}

	public String upSearchWordStatus() throws ParseException, UnsupportedEncodingException {

		Date now = new Date();
		HttpServletRequest request = ServletActionContext.getRequest();
		String keyWord = request.getParameter("keyWord");
		String status = request.getParameter("status");
		if (keyWord == null || keyWord == "") {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		if (keyWord != null && keyWord != "") {
			ShopStatus shopStatus = new ShopStatus();
			if (!status.equals("0")) {
				shopStatus.setWord(keyWord);
				shopStatus.setStatus(1);
				shopStatus.setRuning(0);
				shopStatus.setEnd_time(now);
				shopLinkService.updateStatus(shopStatus);
			} else {
				shopStatus.setWord(keyWord);
				shopStatus.setStatus(0);
				shopStatus.setRuning(1);
				shopStatus.setEnd_time(now);
				shopLinkService.updateStatus(shopStatus);
			}
		}
		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	}

	public String findSearchWordStatus() throws ParseException, UnsupportedEncodingException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String keyWord = request.getParameter("keyWord");
		int status = 0;
		int running = 0;
		if (keyWord != null && keyWord != "") {
			ShopStatus shopStatus = (ShopStatus) shopLinkService.findStatus(keyWord);
			if (shopStatus != null) {

				status = shopStatus.getStatus();
				running = shopStatus.getRuning();
				jsonData = DataHandling.returnkeyWordStatus(status, running);
				return SUCCESS;

			}
		}
		jsonData = DataHandling.returnkeyWordStatusUse(status, running);
		return SUCCESS;
	}

	// 查询t_shop数据
	public String findShopData() throws ParseException, UnsupportedEncodingException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String shopType = request.getParameter("shopType");
		List<ShopLink> shopLinks = shopLinkService.findShopLink(shopType);

		jsonData = DataHandling.returnShopLink(shopLinks);
		return SUCCESS;
	}

	// 更新t_shop数据
	public String upShopData() throws ParseException, UnsupportedEncodingException {
		Date now = new Date();

		HttpServletRequest request = ServletActionContext.getRequest();
		String corporatAccount = request.getParameter("corporatAccount");
		String companyName = request.getParameter("companyName");
		String shopName = request.getParameter("shopName");
		if (shopName == null || shopName == "") {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}

		if (shopName != null && shopName != "") {
			ShopLink shopLink = new ShopLink();
			shopLink.setShop_name(shopName);
			shopLink.setCompany_name(companyName);
			shopLink.setCorporat_account(corporatAccount);
			shopLink.setStatus("1");
			shopLink.setUpdate_time(now);
			shopLinkService.updateShopLicense(shopLink);
		}
		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	}

	public String getShopName() {
		List<ShopLink> ls = new ArrayList<ShopLink>();
		try {
			ls = shopLinkService.getShopName();
		} catch (Exception e) {

		}
		jsonData = DataHandling.returnShopLink(ls);
		return SUCCESS;
	}

	public String getTaoShop() {
		List ls = shopLinkService.getTaoShop();
		jsonData = DataHandling.returnTaoShop(ls);
		return SUCCESS;
	}

	public String upShopRate() throws ParseException, UnsupportedEncodingException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		String rate = request.getParameter("rate");

		if (id == null || id == "") {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		try {
			shopLinkService.updateShopRateById(id, rate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	}
	public String isSearchWord() {
		Date now = new Date();
		String uid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		HttpServletRequest request = ServletActionContext.getRequest();
		String Word = request.getParameter("Word");
		if (Word == null || Word == "") {
			jsonData = DataHandling.returnError();
			return SUCCESS;
		}
		if (Word != null && Word != "") {
			ShopStatus s_shopStatus = (ShopStatus) shopLinkService.findStatus(Word);
			if (s_shopStatus == null) {
				ShopStatus shopStatus = new ShopStatus();
				shopStatus.setCreate_time(now);
				shopStatus.setId(uid);
				shopStatus.setRuning(0);
				shopStatus.setStatus(0);
				shopStatus.setWord(Word);
				shopStatus.setEnd_time(now);
				shopLinkService.save(shopStatus);
			}
		}
		jsonData = DataHandling.returnSuccess();
		return SUCCESS;
	}

	public String findSearchWord() {
		List<ShopStatus> shopStatus = shopLinkService.findSearchWord();

		jsonData = DataHandling.returnSearchWord(shopStatus);
		return SUCCESS;
	}

}
