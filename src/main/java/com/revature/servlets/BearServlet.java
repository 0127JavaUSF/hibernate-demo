package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.revature.models.Bear;
import com.revature.models.Cave;
import com.revature.services.BearService;
import com.revature.util.HibernateUtil;

public class BearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	BearService bearService = new BearService();
	
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
		
		if (parts.length < 2) {
			response.setStatus(404);
			return;
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(parts[1]);
		} catch(NumberFormatException e) {
			response.setStatus(404);
			return;
		}
		
		if (parts.length == 2) {
			// /bears/1
			handleBearById(id, response);
			return;
		}
		
		switch(parts[2]) {
			case "siblings": 
				handleBearGetSiblingsById(id, response); break;
			case "cave":
				handleBearGetCaveByBearId(id, response); break;

		}
		
		if (parts[2].equals("siblings")) {
			handleBearGetSiblingsById(id, response);
		}
	}
	
	private void handleBearGetCaveByBearId(int id, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Cave cave = bearService.getCaveByBearId(id);
		response.setContentType("application/json");
		om.writeValue(response.getWriter(), cave);
	}

	private void handleBearGetSiblingsById(int id, HttpServletResponse response)
		throws IOException {
		List<Bear> siblings = bearService.getSiblingsById(id);
		response.setContentType("application/json");
		om.writeValue(response.getWriter(), siblings);
	}

	private void handleBearById(int id, HttpServletResponse response)
			throws ServletException, IOException {

		Bear bear = bearService.getBearById(id);
		
		if (bear == null) {
			response.setStatus(404);
			return;
		}
		
		response.setContentType("application/json");
		om.writeValue(response.getWriter(), bear);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Bear bear = om.readValue(request.getReader(), Bear.class);
		bear = bearService.createBear(bear);
		response.setContentType("application/json");
		response.setStatus(201);
		om.writeValue(response.getWriter(), bear);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Bear bear = om.readValue(req.getReader(), Bear.class);
		System.out.println(bear);
		bear = bearService.updateBear(bear);
		resp.setContentType("application/json");
		resp.setStatus(200);
		om.writeValue(resp.getWriter(), bear);
	}
	
	@Override
	public void destroy() {
		HibernateUtil.shutdownFactory();
	}
}
