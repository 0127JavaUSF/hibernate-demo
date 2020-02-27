package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Bear;
import com.revature.services.BearService;

public class BearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	BearService bearService = new BearService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Bear bear = om.readValue(request.getReader(), Bear.class);
		bear = bearService.createBear(bear);
		response.setContentType("application/json");
		response.setStatus(201);
		om.writeValue(response.getWriter(), bear);
	}
	
}
