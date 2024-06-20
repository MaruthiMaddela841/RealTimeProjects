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

@WebServlet("/librarian/*")
public class LibrarianServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
       
    	
    	try (Connection connection = DatabaseConnection.getConnection()) {
			if (request.getRequestURI().endsWith("getAllLibrarians")) {
				listLibrarians(request, response, connection);
			}
			else if (request.getRequestURI().endsWith("registerLibrarian")) {
				registerLibrarian(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("searchByName")) {
				searchLibrarianByName(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("searchById")) {
				searchLibrarianById(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("updateLibrarian")) {
				updateLibrarianById(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("deleteLibrarian")) {
				deleteLibrarianById(request, response, connection);
			}
		}
		catch (SQLException e) {
			throw new ServletException(e);
		}
	}
    
	private void deleteLibrarianById(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, IOException, ServletException{
		
		PreparedStatement statement = connection.prepareStatement("delete from librarians where id=?");
		statement.setString(1,request.getParameter("id"));
		statement.executeUpdate();
		listLibrarians(request, response, connection);
		
	}

	private void updateLibrarianById(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, IOException, ServletException{
		PreparedStatement statement = connection.prepareStatement("update librarians set name=?,email=? where id=?;");
		statement.setString(1,request.getParameter("name"));
		statement.setString(2,request.getParameter("email"));
		statement.setString(3,request.getParameter("id"));
		statement.executeUpdate();
		searchLibrarianById(request, response, connection);
		
	}

	private void searchLibrarianById(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, IOException, ServletException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM librarians WHERE id=?");
		statement.setString(1,request.getParameter("id"));
		List<Librarian> librarians = new ArrayList<>();
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				Librarian li = new Librarian(id, name, email);
				System.out.println(li);
				librarians.add(li);
			}
		}
		request.setAttribute("librarians", librarians);
		request.getRequestDispatcher("../get_all_librarians.jsp").forward(request, response);
	}

	private void searchLibrarianByName(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM librarians WHERE name=?");
		statement.setString(1,request.getParameter("name"));
		List<Librarian> librarians = new ArrayList<>();
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				Librarian li = new Librarian(id, name, email);
				System.out.println(li);
				librarians.add(li);
			}
		}
		request.setAttribute("librarians",librarians);
		request.getRequestDispatcher("../get_all_librarians.jsp").forward(request, response);
	}
	
    private void listLibrarians(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, ServletException, IOException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM librarians");
        List<Librarian> librarian = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Librarian li = new Librarian(id, name, email);
                System.out.println(li);
                librarian.add(li);
            }
        }
        request.setAttribute("librarians", librarian);
        request.getRequestDispatcher("../get_all_librarians.jsp").forward(request, response);
    }

    private void registerLibrarian(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        PreparedStatement statement = connection.prepareStatement("INSERT INTO librarians (name, email) VALUES (?, ?)");
        statement.setString(1, name);
        statement.setString(2, email);
        statement.executeUpdate();
        System.out.println("Registered");
        listLibrarians(request, response, connection);
        //response.sendRedirect("librarians");
    }
}

