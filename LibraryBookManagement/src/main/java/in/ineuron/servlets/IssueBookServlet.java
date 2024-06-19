package in.ineuron.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.ineuron.util.DatabaseConnection;

//@WebServlet("/issue")
public class IssueBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));

            PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) AS bookCount FROM issues WHERE student_id = ?");
            checkStatement.setInt(1, studentId);
            ResultSet rs = checkStatement.executeQuery();
            if (rs.next() && rs.getInt("bookCount") >= 3) {
                response.getWriter().write("Student has already borrowed maximum number of books");
                return;
            }

            PreparedStatement statement = connection.prepareStatement("INSERT INTO issues (student_id, book_id, issue_date, due_date) VALUES (?, ?, ?, ?)");
            statement.setInt(1, studentId);
            statement.setInt(2, bookId);
            Date issueDate = new Date(System.currentTimeMillis());
            statement.setDate(3, issueDate);
            Date dueDate = new Date(issueDate.getTime() + 15L * 24 * 60 * 60 * 1000);  // 15 days later
            statement.setDate(4, dueDate);
            statement.executeUpdate();

            response.sendRedirect("students");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

