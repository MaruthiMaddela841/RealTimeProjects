package in.ineuron.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.ineuron.util.DatabaseConnection;

//@WebServlet("/librarians")
public class LibrarianServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            switch (action) {
                case "register":
                    registerLibrarian(request, response, connection);
                    break;
                default:
                    listLibrarians(request, response, connection);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listLibrarians(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, ServletException, IOException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM librarians");
        ResultSet resultSet = statement.executeQuery();
        request.setAttribute("librarians", resultSet);
        request.getRequestDispatcher("librarians.jsp").forward(request, response);
    }

    private void registerLibrarian(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        PreparedStatement statement = connection.prepareStatement("INSERT INTO librarians (name, email) VALUES (?, ?)");
        statement.setString(1, name);
        statement.setString(2, email);
        statement.executeUpdate();

        response.sendRedirect("librarians");
    }
}

