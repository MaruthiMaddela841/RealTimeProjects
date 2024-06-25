package com.example.librarymanagement.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.librarymanagement.db.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try (Connection connection = DatabaseConnection.getConnection()) {
			if (request.getRequestURI().endsWith("access")) {
				
			}
			
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

}
