package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.daos.BearDao;
import com.revature.models.Bear;
import com.revature.models.Cave;

public class BearService {

	BearDao bearDao = new BearDao();
	Logger logger = Logger.getRootLogger();
	

	public Bear createBear(Bear bear) {
		// internal business logic
		logger.warn("Received bear with fur color: " + bear.getColor());
		return bearDao.createBear(bear);
	}


	public Bear getBearById(int id) {
		// internal business logic
//		return bearDao.getBearById(id);
		return bearDao.loadBearById(id);
	}


	public Bear updateBear(Bear bear) {
		return bearDao.mergeBear(bear);
	}


	public List<Bear> getSiblingsById(int id) {
		return bearDao.getSiblingsById(id);
	}


	public Cave getCaveByBearId(int id) {
		return bearDao.getCaveByBearId(id);
	}
}
