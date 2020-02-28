package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.revature.models.Cave;
import com.revature.services.CaveService;
import com.revature.util.HibernateUtil;

public class CaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CaveService caveService = new CaveService();
	
	ObjectMapper om = new ObjectMapper();
	
	@Override
	public void init() {
		HibernateUtil.configureHibernate();
		// Provides some functionality for Jackson to integrate with Hibernate
		om.registerModule(new Hibernate5Module());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		if(request.getPathInfo() == null) {
			response.setStatus(404);
			return;
		}
		
		String[] parts = request.getPathInfo().split("/");
		
		if (parts.length < 1) {
			response.setStatus(404);
			return;
		}
		
		String part = parts[1];
		int id = 0;
		try {
			id = Integer.parseInt(part);
		} catch(NumberFormatException e) {
			response.setStatus(404);
			return;
		}
		
		Cave cave = caveService.getCaveById(id);
		
		if (cave == null) {
			response.setStatus(404);
			return;
		}
		
		response.setContentType("application/json");
		om.writeValue(response.getWriter(), cave);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Cave cave = om.readValue(request.getReader(), Cave.class);
		cave = caveService.createCave(cave);
		response.setContentType("application/json");
		response.setStatus(201);
		om.writeValue(response.getWriter(), cave);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cave cave = om.readValue(req.getReader(), Cave.class);
		cave = caveService.updateCave(cave);
		resp.setContentType("application/json");
		resp.setStatus(200);
		om.writeValue(resp.getWriter(), cave);
	}
	
	@Override
	public void destroy() {
		HibernateUtil.shutdownFactory();
	}
}
