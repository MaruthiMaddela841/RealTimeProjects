<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<div class="login-container">
		<h2>Login</h2>
		<form action="${pageContext.request.contextPath}/loginPage"
			method="POST">
			<div class="form-group">
				<label for="username">Username:</label> <input type="text"
					id="username" name="username" required>
			</div>
			<div class="form-group">
				<label for="password">Password:</label> <input type="password"
					id="password" name="password" required>
			</div>
			<button type="submit" class="btn">Login</button>
		</form>
		<c:if test="${not empty param.error}">
			<p class="error">${param.error}</p>
		</c:if>
	</div>
</body>
</html>