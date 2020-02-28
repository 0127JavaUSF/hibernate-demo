package com.revature.services;

import com.revature.daos.CaveDao;
import com.revature.models.Bear;
import com.revature.models.Cave;

public class CaveService {

	CaveDao caveDao = new CaveDao();
	
	public Cave getCaveById(int id) {
		return caveDao.getById(id);
	}

	public Cave createCave(Cave cave) {
		return caveDao.save(cave);
	}

	public Cave updateCave(Cave cave) {
		return caveDao.update(cave);
	}

}
