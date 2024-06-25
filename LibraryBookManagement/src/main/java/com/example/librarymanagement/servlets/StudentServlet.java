package com.example.librarymanagement.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.librarymanagement.db.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/*")
public class StudentServlet extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			if (request.getRequestURI().endsWith("getAllStudents")) {
				listStudents(request, response, connection);
			}
			else if (request.getRequestURI().endsWith("registerStudent")) {
					registerStudent(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("searchByName")) {
					searchStudentByName(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("searchById")) {
				searchStudentById(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("updateStudent")) {
				updateStudentById(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("deleteStudent")) {
				deleteStudentById(request, response, connection);
			}
		}
		catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void deleteStudentById(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		PreparedStatement statement = connection.prepareStatement("delete from students where id=?");
		statement.setString(1,request.getParameter("id"));
		statement.executeUpdate();
		listStudents(request, response, connection);
	}

	private void updateStudentById(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		PreparedStatement statement = connection.prepareStatement("update students set name=?,email=? where id=?;");
		statement.setString(1,request.getParameter("name"));
		statement.setString(2,request.getParameter("email"));
		statement.setString(3,request.getParameter("id"));
		statement.executeUpdate();
		searchStudentById(request, response, connection);
		
	}

	private void searchStudentById(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id=?");
		statement.setString(1,request.getParameter("id"));
		List<Students> students = new ArrayList<>();
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				Students stundet = new Students(id, name, email);
				System.out.println(stundet);
				students.add(stundet);
			}
		}
		request.setAttribute("students", students);
		request.getRequestDispatcher("../get_all_students.jsp").forward(request, response);
		
	}

	private void searchStudentByName(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE name=?");
		statement.setString(1,request.getParameter("name"));
		List<Students> students = new ArrayList<>();
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				Students stundet = new Students(id, name, email);
				System.out.println(stundet);
				students.add(stundet);
			}
		}
		request.setAttribute("students", students);
		request.getRequestDispatcher("../get_all_students.jsp").forward(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws SQLException, ServletException, IOException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
		List<Students> students = new ArrayList<>();
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				Students book = new Students(id, name, email);
				students.add(book);
			}
		}
		request.setAttribute("students", students);
		request.getRequestDispatcher("../get_all_students.jsp").forward(request, response);
	}

	private void registerStudent(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws SQLException, IOException, ServletException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, email) VALUES (?, ?)");
		statement.setString(1, name);
		statement.setString(2, email);
		statement.executeUpdate();
		listStudents(request, response, connection);
		//response.sendRedirect("get_all_students.jsp");
	}
}
