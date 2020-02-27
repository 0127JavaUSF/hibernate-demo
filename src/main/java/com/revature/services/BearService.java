package com.revature.services;

import org.apache.log4j.Logger;

import com.revature.daos.BearDao;
import com.revature.models.Bear;

public class BearService {

	BearDao bearDao = new BearDao();
	Logger logger = Logger.getRootLogger();
	

	public Bear createBear(Bear bear) {
		// internal business logic
		logger.warn("Received bear with fur color: " + bear.getColor());
		return bearDao.createBear(bear);
		
		
	}
}
