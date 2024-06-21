package com.example.librarymanagement.servlets;

public class BookIssue {
	
	private Integer studentId;
	private String studentName;
	private Integer bookId;
	private String bookTitle;
	private String librarian;
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getLibrarian() {
		return librarian;
	}
	public void setLibrarian(String librarian) {
		this.librarian = librarian;
	}
	@Override
	public String toString() {
		return "BookIssue [studentId=" + studentId + ", studentName=" + studentName + ", bookId=" + bookId
				+ ", bookTitle=" + bookTitle + ", librarian=" + librarian + "]";
	}
	public BookIssue(Integer studentId, String studentName, Integer bookId, String bookTitle, String librarian) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.librarian = librarian;
	}
	public BookIssue() {
	}
	
	
	

}
