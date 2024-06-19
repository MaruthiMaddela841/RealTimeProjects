package com.example.librarymanagement.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.librarymanagement.db.DatabaseConnection;

@WebServlet("/student/*")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (request.getRequestURI().endsWith("getAllStudents")) {
    		
    	}
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            switch (action) {
                case "register":
                    registerStudent(request, response, connection);
                    break;
                default:
                    listStudents(request, response, connection);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
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
        request.getRequestDispatcher("get_all_students.jsp").forward(request, response);
    }

    private void registerStudent(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, email) VALUES (?, ?)");
        statement.setString(1, name);
        statement.setString(2, email);
        statement.executeUpdate();

        response.sendRedirect("get_all_students.jsp");
    }
}

