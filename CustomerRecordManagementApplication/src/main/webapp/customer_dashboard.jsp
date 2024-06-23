<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Dashboard</title>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/customer_dashboard.css">
</head>
<body>
    <div class="container">
        <!-- Header with Logout Button -->
        <header>
            <div class="header-content">
                <h1>Welcome, ${customer.name}!</h1>
                <form action="LogoutServlet" method="POST">
                    <button type="submit" class="btn-logout">Logout</button>
                </form>
            </div>
        </header>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Left Section: Personal Details -->
            <section class="left-section">
                <h2>Personal Details</h2>
                <ul>
                    <li><strong>Name:</strong> ${customer.name}</li>
                    <li><strong>Email:</strong> ${customer.email}</li>
                    <li><strong>Phone:</strong> ${customer.phone}</li>
                    <li><strong>Address:</strong> ${customer.address}</li>
                    <!-- Add more personal details as needed -->
                </ul>

                <!-- Edit Profile Button -->
                <a href="./edit-customer.jsp?id=${customer.id}&name=${customer.name}&email=${customer.email}&phone=${customer.phone}&address=${customer.address}&username=${customer.username}&password=${customer.password}" 
                    class="btn btn-edit-profile">Edit Profile</a>
            </section>

            <!-- Middle Section: Bank Account Details and Transactions -->
            <section class="middle-section">
                <h2>Bank Account Details</h2>
                <!-- Bank Account Details here -->

                <h2>Transaction Details</h2>
                <!-- Transaction Details here -->
            </section>

            <!-- Right Section: Notifications -->
            <section class="right-section">
                <h2>Notifications</h2>
                <ul>
                    <li><a href="#">Notification 1</a></li>
                    <li><a href="#">Notification 2</a></li>
                    <!-- List notifications dynamically -->
                </ul>
            </section>
        </div>

        <!-- Footer -->
        <footer>
            <p>&copy; 2024 Customer Record Management</p>
        </footer>
    </div>
</body>
</html>
