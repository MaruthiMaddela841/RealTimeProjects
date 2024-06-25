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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/book/*")
public class BookServlet extends HttpServlet {
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
			if (request.getRequestURI().endsWith("getAllBooks")) {
				listBooks(request, response, connection);
			}
			else if (request.getRequestURI().endsWith("registerBook")) {
				addBook(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("searchByAuthor")) {
					searchBookByAuthor(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("searchByTitle")) {
				searchBookByTitle(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("searchByCategory")) {
				searchBookByCategory(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("updateBook")) {
				updateBook(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("deleteBook")) {
				deleteBook(request, response, connection);
			}
		}
		catch (SQLException e) {
			throw new ServletException(e);
		}
	}
        

    private void searchBookByCategory(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException{
    	PreparedStatement statement = connection.prepareStatement("SELECT * FROM books where category=?");
    	String categoryInput=request.getParameter("category");
    	statement.setString(1, categoryInput);
        List<Book> books = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");

                Book book = new Book(id, title, author, category);
                books.add(book);
            }
        }
        request.setAttribute("books",books);
        request.getRequestDispatcher("../get_all_books.jsp").forward(request, response);
		
	}

	private void searchBookByAuthor(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
    	PreparedStatement statement = connection.prepareStatement("SELECT * FROM books where author=?");
    	String authorInput=request.getParameter("author");
    	statement.setString(1, authorInput);
        List<Book> books = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");

                Book book = new Book(id, title, author, category);
                books.add(book);
            }
        }
        request.setAttribute("books",books);
        request.getRequestDispatcher("../get_all_books.jsp").forward(request, response);
		
	}

	private void searchBookByTitle(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
    	PreparedStatement statement = connection.prepareStatement("SELECT * FROM books where title=?");
    	String titleInput=request.getParameter("title");
    	statement.setString(1, titleInput);
        List<Book> books = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");

                Book book = new Book(id, title, author, category);
                books.add(book);
            }
        }
        request.setAttribute("books",books);
        request.getRequestDispatcher("../get_all_books.jsp").forward(request, response);
		
	}

	private void listBooks(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, ServletException, IOException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM books");
        List<Book> books = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");

                Book book = new Book(id, title, author, category);
                books.add(book);
            }
        }
        request.setAttribute("books",books);
        request.getRequestDispatcher("../get_all_books.jsp").forward(request, response);
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, IOException, ServletException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String category = request.getParameter("category");

        PreparedStatement statement = connection.prepareStatement("INSERT INTO books (title, author, category) VALUES (?, ?, ?)");
        statement.setString(1, title);
        statement.setString(2, author);
        statement.setString(3, category);
        statement.executeUpdate();
        listBooks(request, response, connection);
        //response.sendRedirect("books");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response, Connection connection)
    		throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
        listBooks(request, response, connection);
        //response.sendRedirect("books");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response, Connection connection)
    		throws SQLException, IOException, ServletException  {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String category = request.getParameter("category");

        PreparedStatement statement = connection.prepareStatement("UPDATE books SET title = ?, author = ?, category = ? WHERE id = ?");
        statement.setString(1, title);
        statement.setString(2, author);
        statement.setString(3, category);
        statement.setInt(4, id);
        statement.executeUpdate();
        listBooks(request, response, connection);
        //response.sendRedirect("books");
    }
}
