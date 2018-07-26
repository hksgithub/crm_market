package com.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.dao.TanJiDao;
import com.entity.TanJi;
import com.service.TanJiService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TanJiServiceImpl implements TanJiService {

	private TanJiDao tanJiDao;
	
	public void setTanJiDao(TanJiDao tanJiDao) {
		this.tanJiDao = tanJiDao;
	}

	@Override
	public boolean saveTanJi(TanJi tanji) {
		return tanJiDao.saveTanJi(tanji);
	}

	@Override
	public boolean removeTanji(String companyName) {
		return tanJiDao.removeTanji(companyName);
	}

	@Override
	public boolean getTanJi(String companyName) {
		return tanJiDao.getTanJi(companyName);
	}

	@Override
	public List<TanJi> parseTanJiJson(String json){
		Date now = new Date();
		List<TanJi> tans = new ArrayList<TanJi>();
		try {
			JSONObject obj = JSONObject.fromObject(json);
			JSONArray arr = JSONArray.fromObject(obj.get("contacts"));
			for(int k = 0 ; k < arr.size() ; k ++){
				Object operator = ((JSONObject)arr.get(k)).get("operator");
				if(!StringUtils.isEmpty(operator)){					
					TanJi tan = new TanJi();
					JSONObject tanObj = (JSONObject)arr.get(k);
					tan.setCreateTime(now);
					tan.setPhone(null == tanObj.get("contact")?null:tanObj.get("contact").toString());
					tan.setLabel("--");//所有标签都是自己设置的，拿标签没意义
					tan.setSource(null == tanObj.get("contactSource")?null:tanObj.get("contactSource").toString());
					tan.setContacts(null == tanObj.get("contactName")?null:tanObj.get("contactName").toString());
					tans.add(tan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tans;
	}
}
