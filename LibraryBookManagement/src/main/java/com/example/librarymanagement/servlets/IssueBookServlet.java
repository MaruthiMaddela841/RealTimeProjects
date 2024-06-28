package com.example.librarymanagement.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.example.librarymanagement.db.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
			else if (request.getRequestURI().endsWith("deleteIssueRecord")) {
				deleteReturnedRecord(request, response, connection);
			}
			else if(request.getRequestURI().endsWith("issueBookBefore")) {
				issueBookBefore(request, response, connection);
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void issueBookBefore(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		PreparedStatement statement = connection.prepareStatement("SELECT name FROM librarians");
		ResultSet rs = statement.executeQuery();
		List<String> librarians=new ArrayList<>();
		while(rs.next()) {
			String name=rs.getString("name");
			librarians.add(name);
		}
		request.setAttribute("librarians", librarians);
		request.getRequestDispatcher("../issue_book.jsp").forward(request, response);
	}

	private void deleteReturnedRecord(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException, SQLException {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String paidValue=request.getParameter("paidReturnedCheckbox");
		String notPaidValue=request.getParameter("notPaidCheckbox");
		if(notPaidValue!=null) {
			int dueAmount=-2;
			request.setAttribute("dueAmount", dueAmount);
			request.getRequestDispatcher("../due_calculation.jsp").forward(request, response);
			return;
		}
		PreparedStatement studentStatement = connection.prepareStatement("DELETE FROM issues WHERE student_id = ? AND book_id = ?;");
		studentStatement.setInt(1, studentId);
		studentStatement.setInt(2, bookId);
		studentStatement.executeUpdate();
		List<BookIssue> bookIssues = latestRecords(connection,studentId);
		request.setAttribute("bookIssues", bookIssues);
		request.getRequestDispatcher("../student_mapped_books.jsp").forward(request, response);
	}

	private void allIssuedRecords(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		PreparedStatement studentStatement = connection.prepareStatement(
			    "SELECT s.id AS studentId, s.name AS studentName, b.id AS bookId, b.title AS bookTitle, i.librarian AS librarian " +
			    "FROM students s " +
			    "JOIN issues i ON s.id = i.student_id " +
			    "JOIN books b ON i.book_id = b.id " +
			    "ORDER BY s.id ASC");
		ResultSet studentResultSet = studentStatement.executeQuery();

		List<BookIssue> bookIssues = new ArrayList<>();
		while (studentResultSet.next()) {
			BookIssue bookIssue = new BookIssue();
			bookIssue.setStudentId(studentResultSet.getInt("studentId"));
			bookIssue.setStudentName(studentResultSet.getString("studentName"));
			bookIssue.setBookId(studentResultSet.getInt("bookId"));
			bookIssue.setBookTitle(studentResultSet.getString("bookTitle"));
			bookIssue.setLibrarian(studentResultSet.getString("librarian"));
			bookIssues.add(bookIssue);
			System.out.println(bookIssue);
		}
		request.setAttribute("bookIssues", bookIssues);
		request.getRequestDispatcher("../student_mapped_books.jsp").forward(request, response);
	}
	
	private List<BookIssue> latestRecords(Connection connection,int studentId) throws SQLException {
		PreparedStatement studentStatement = connection.prepareStatement(
				"SELECT s.id AS studentId, s.name AS studentName, b.id AS bookId, b.title AS bookTitle, i.librarian AS librarian "
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
			bookIssue.setLibrarian(studentResultSet.getString("librarian"));
			bookIssues.add(bookIssue);
		}
		return bookIssues;
	}
	
	private void returnBook(HttpServletRequest request, HttpServletResponse response, Connection connection) throws SQLException, ServletException, IOException {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		PreparedStatement checkStatement = connection
				.prepareStatement("SELECT * FROM issues WHERE student_id = ? and book_id=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		checkStatement.setInt(1, studentId);
		checkStatement.setInt(2, bookId);
		int dueAmount=0;
		ResultSet rs = checkStatement.executeQuery();
		if(!rs.next()) {
			dueAmount=-1;
			request.setAttribute("dueAmount", dueAmount);
			request.getRequestDispatcher("../due_calculation.jsp").forward(request, response);
			return;
		}
		Date issue_date=rs.getDate("issue_date");
		
		// Convert java.sql.Date to java.time.LocalDate
		LocalDate local_issue_date = issue_date.toLocalDate();
		LocalDate local_due_date = LocalDate.now();

		// Calculate the number of days between the two dates (inclusive)
		long daysBetween = ChronoUnit.DAYS.between(local_issue_date, local_due_date) + 1;
		
		int daysCounter=(int) daysBetween;
		if(daysBetween>15) {
			while(daysCounter!=15) {
				dueAmount=dueAmount+10;
				daysCounter--;
			}
		}
		request.setAttribute("daysBetween", daysBetween);
		request.setAttribute("dueAmount", dueAmount);
		request.setAttribute("studentId", studentId);
		request.setAttribute("bookId", bookId);
		request.getRequestDispatcher("../due_calculation.jsp").forward(request, response);
		
	}

	private void issueTheBook(HttpServletRequest request, HttpServletResponse response, Connection connection2)
			throws SQLException, ServletException, IOException {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String librarianName=request.getParameter("librarianName");
		String issueDateStr = request.getParameter("issueDate");


		 PreparedStatement statementLibrarian = connection2.prepareStatement("SELECT * FROM librarians");
	        List<Librarian> librarians = new ArrayList<>();
	        try (ResultSet resultSet = statementLibrarian.executeQuery()) {
	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("name");
	                String email = resultSet.getString("email");
	                Librarian li = new Librarian(id, name, email);
	                System.out.println(li);
	                librarians.add(li);
	            }
	        }
				
		// Parse issueDateStr into java.sql.Date
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date parsedDate;
	    java.sql.Date issueDate = null;
	    try {
	        parsedDate = dateFormat.parse(issueDateStr);
	        issueDate = new java.sql.Date(parsedDate.getTime());
	    } catch (ParseException e) {
	        e.printStackTrace(); // Handle parsing exception
	    }
	    List<BookIssue> bookIssues=latestRecords(connection2,studentId);
	    
	    PreparedStatement studentStatement = connection2
				.prepareStatement("SELECT COUNT(*) AS students_count FROM books WHERE id = ?");
	    studentStatement.setInt(1, studentId);
		ResultSet rsStudent = studentStatement.executeQuery();
		if (rsStudent.next() && rsStudent.getInt("students_count") != 1) {
			int studentNotFound=-1;
			request.setAttribute("studentNotFound", studentNotFound);
			request.setAttribute("bookIssues", bookIssues);
			request.setAttribute("librarians", librarians);
			request.getRequestDispatcher("/issue_error.jsp").forward(request, response);
			return;
		}
	    
		PreparedStatement bookStatement = connection2
				.prepareStatement("SELECT COUNT(*) AS books_count FROM books WHERE id = ?");
		bookStatement.setInt(1, bookId);
		ResultSet rsBook = bookStatement.executeQuery();
		if (rsBook.next() && rsBook.getInt("books_count") != 1) {
			int bookNotFound=-1;
			request.setAttribute("bookNotFound", bookNotFound);
			request.setAttribute("bookIssues", bookIssues);
			request.getRequestDispatcher("/issue_error.jsp").forward(request, response);
			return;
		}
		PreparedStatement checkStatement = connection2
				.prepareStatement("SELECT COUNT(*) AS bookCount FROM issues WHERE student_id = ?");
		checkStatement.setInt(1, studentId);
		ResultSet rs = checkStatement.executeQuery();
//		bookIssues.forEach(System.out::println);
		if (rs.next() && rs.getInt("bookCount") >= 3) {
			request.setAttribute("recordsCount", rs.getInt("bookCount"));
			request.setAttribute("bookIssues", bookIssues);
			request.getRequestDispatcher("/issue_error.jsp").forward(request, response);
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
			request.getRequestDispatcher("/issue_error.jsp").forward(request, response);
			return;
		}

		PreparedStatement statement = connection2
				.prepareStatement("INSERT INTO issues (student_id, book_id, issue_date,librarian) VALUES (?, ?, ?,?)");
		statement.setInt(1, studentId);
		statement.setInt(2, bookId);
		statement.setDate(3, issueDate);
		statement.setString(4,librarianName);
		statement.executeUpdate();
		bookIssues=latestRecords(connection2,studentId);
		request.setAttribute("bookIssues", bookIssues);
		request.getRequestDispatcher("/student_mapped_books.jsp").forward(request, response);
	}

}
