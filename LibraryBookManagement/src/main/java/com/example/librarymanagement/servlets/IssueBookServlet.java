package com.example.librarymanagement.servlets;

import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
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

@WebServlet("/issue/*")
public class IssueBookServlet extends HttpServlet {
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
			if (request.getRequestURI().endsWith("register")) {
				issueTheBook(request, response, connection);
			}
			else if (request.getRequestURI().endsWith("allIssuedRecords")) {
				allIssuedRecords(request, response, connection);
			}
			else if (request.getRequestURI().endsWith("returnBook")) {
				returnBook(request, response, connection);
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void allIssuedRecords(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		PreparedStatement studentStatement = connection.prepareStatement(
				"SELECT s.id AS studentId, s.name AS studentName, b.id AS bookId, b.title AS bookTitle "
						+ "FROM students s " + "JOIN issues i ON s.id = i.student_id "
						+ "JOIN books b ON i.book_id = b.id " +
		                "ORDER BY s.id ASC");
		ResultSet studentResultSet = studentStatement.executeQuery();

		List<BookIssue> bookIssues = new ArrayList<>();
		while (studentResultSet.next()) {
			BookIssue bookIssue = new BookIssue();
			bookIssue.setStudentId(studentResultSet.getInt("studentId"));
			bookIssue.setStudentName(studentResultSet.getString("studentName"));
			bookIssue.setBookId(studentResultSet.getInt("bookId"));
			bookIssue.setBookTitle(studentResultSet.getString("bookTitle"));
			bookIssues.add(bookIssue);
		}

		request.setAttribute("bookIssues", bookIssues);
		request.getRequestDispatcher("../student_mapped_books.jsp").forward(request, response);
	}
	
	private List<BookIssue> latestRecords(Connection connection,int studentId) throws SQLException {
		PreparedStatement studentStatement = connection.prepareStatement(
				"SELECT s.id AS studentId, s.name AS studentName, b.id AS bookId, b.title AS bookTitle "
						+ "FROM students s " + "JOIN issues i ON s.id = i.student_id "
						+ "JOIN books b ON i.book_id = b.id " + "WHERE s.id = ?");
		studentStatement.setInt(1, studentId);
		ResultSet studentResultSet = studentStatement.executeQuery();

		List<BookIssue> bookIssues = new ArrayList<>();
		while (studentResultSet.next()) {
			BookIssue bookIssue = new BookIssue();
			bookIssue.setStudentId(studentResultSet.getInt("studentId"));
			bookIssue.setStudentName(studentResultSet.getString("studentName"));
			bookIssue.setBookId(studentResultSet.getInt("bookId"));
			bookIssue.setBookTitle(studentResultSet.getString("bookTitle"));
			bookIssues.add(bookIssue);
		}
		return bookIssues;
	}
	
	private void returnBook(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		PreparedStatement checkStatement = connection
				.prepareStatement("SELECT * FROM issues WHERE student_id = ? and book_id=?");
		checkStatement.setInt(1, studentId);
		checkStatement.setInt(2, bookId);
		ResultSet rs = checkStatement.executeQuery();
		
		List<BookIssue> bookIssues=latestRecords(connection,studentId);
		int count=0;
		while(rs.next()) {
			if(count==0) {
				
				request.setAttribute("bookIssues", bookIssues);
				request.getRequestDispatcher("../issue_error.jsp").forward(request, response);
			}
			count++;
		}
		
	}

	private void issueTheBook(HttpServletRequest request, HttpServletResponse response, Connection connection2)
			throws SQLException, ServletException, IOException {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		PreparedStatement checkStatement = connection2
				.prepareStatement("SELECT COUNT(*) AS bookCount FROM issues WHERE student_id = ?");
		checkStatement.setInt(1, studentId);
		ResultSet rs = checkStatement.executeQuery();
		
		List<BookIssue> bookIssues=latestRecords(connection2,studentId);
		
		if (rs.next() && rs.getInt("bookCount") >= 3) {
			request.setAttribute("recordsCount", rs.getInt("bookCount"));
			request.setAttribute("bookIssues", bookIssues);
			request.getRequestDispatcher("../issue_error.jsp").forward(request, response);
			return;
		}
		PreparedStatement checkStatement2 = connection2
				.prepareStatement("SELECT COUNT(*) as dup FROM issues WHERE student_id = ? and book_id=?");
		checkStatement2.setInt(1, studentId);
		checkStatement2.setInt(2, bookId);
		ResultSet rs2 = checkStatement2.executeQuery();
		if (rs2.next() && rs2.getInt("dup") ==1) {
			request.setAttribute("duplicate", rs2.getInt("dup"));
			request.setAttribute("bookIssues", bookIssues);
			request.getRequestDispatcher("../issue_error.jsp").forward(request, response);
			return;
		}

		PreparedStatement statement = connection2
				.prepareStatement("INSERT INTO issues (student_id, book_id, issue_date, due_date) VALUES (?, ?, ?, ?)");
		statement.setInt(1, studentId);
		statement.setInt(2, bookId);
		Date issueDate = new Date(System.currentTimeMillis());
		statement.setDate(3, issueDate);
		Date dueDate = new Date(issueDate.getTime() + 15L * 24 * 60 * 60 * 1000); // 15 days later
		statement.setDate(4, dueDate);
		statement.executeUpdate();
		bookIssues=latestRecords(connection2,studentId);
		request.setAttribute("bookIssues", bookIssues);
		request.getRequestDispatcher("../student_mapped_books.jsp").forward(request, response);
	}

}
