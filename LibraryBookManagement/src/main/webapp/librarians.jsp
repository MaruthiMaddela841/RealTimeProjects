<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Librarians</title>
</head>
<body>
    <h1>Librarians</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <c:forEach var="librarian" items="${librarians}">
            <tr>
                <td>${librarian.id}</td>
                <td>${librarian.name}</td>
                <td>${librarian.email}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
