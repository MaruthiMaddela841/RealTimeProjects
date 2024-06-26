<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ page import="in.ineuron.model.Admin" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Edit Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/edit-customer.css">
</head>
<body>
    <h2>Edit Admin</h2>
    <form action="${pageContext.request.contextPath}/updateAdminDetails" method="POST">
        <input type="hidden" name="adminId" value="${param.id}">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${param.name}" required><br><br>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${param.email}" required><br><br>
            <label for="phone">Phone:</label>
            <input type="text" id="phone" name="phone" value="${param.phone}" required><br><br>
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="${param.address}" required><br><br>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="${param.username}" required><br><br>
            <label for="password">Password:</label>
            <input type="text" id="password" name="password" value="${param.password}" required><br><br>
            <button type="submit" class="btn">Update Admin</button>
    </form>
</body>
</html>
