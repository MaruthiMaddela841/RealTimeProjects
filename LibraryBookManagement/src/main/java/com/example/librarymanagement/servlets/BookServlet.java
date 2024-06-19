package com.example.librarymanagement.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.librarymanagement.db.DatabaseConnection;
import javax.servlet.annotation.WebServlet;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            switch (action) {
                case "add":
                    addBook(request, response, connection);
                    break;
                case "delete":
                    deleteBook(request, response, connection);
                    break;
                case "update":
                    updateBook(request, response, connection);
                    break;
                case "search":
                    searchBook(request, response, connection);
                    break;
                default:
                    listBooks(request, response, connection);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
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
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String category = request.getParameter("category");

        PreparedStatement statement = connection.prepareStatement("INSERT INTO books (title, author, category) VALUES (?, ?, ?)");
        statement.setString(1, title);
        statement.setString(2, author);
        statement.setString(3, category);
        statement.executeUpdate();

        response.sendRedirect("books");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();

        response.sendRedirect("books");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, IOException {
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

        response.sendRedirect("books");
    }

    private void searchBook(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, ServletException, IOException {
        String searchField = request.getParameter("searchField");
        String searchTerm = request.getParameter("searchTerm");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE " + searchField + " LIKE ?");
        statement.setString(1, "%" + searchTerm + "%");
        ResultSet resultSet = statement.executeQuery();
        request.setAttribute("books", resultSet);
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }
}
