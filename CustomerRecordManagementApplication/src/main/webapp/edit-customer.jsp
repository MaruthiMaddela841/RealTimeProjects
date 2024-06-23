<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ page import="in.ineuron.model.Customer" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Edit Customer</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/edit-customer.css">
</head>
<body>
    <h2>Edit Customer</h2>
    <form action="${pageContext.request.contextPath}/updateCustomerDetails" method="POST">
        <input type="hidden" name="id" value="${param.id}">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${param.name}" placeholder="admin" required><br><br>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${param.email}" required><br><br>
        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" value="${param.phone}" required><br><br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${param.address}" required><br><br>
        <button type="submit">Update Customer</button>
    </form>
</body>
</html>
