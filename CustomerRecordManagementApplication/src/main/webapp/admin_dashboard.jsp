<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="in.ineuron.model.Admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard</title>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/admin_dashboard.css">
</head>
<body>
    <div class="container">
        <!-- Header with Logout Button -->
        <header>
            <div class="header-content">
                <h1>Welcome, ${admin.name}!</h1>
            </div>
        </header>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Left Section: Personal Details -->
            <section class="left-section">
                <h2>Personal Details</h2>
                <ul>
                    <li><strong>Name:</strong> ${admin.name}</li>
                    <li><strong>Email:</strong> ${admin.email}</li>
                    <li><strong>Phone:</strong> ${admin.phone}</li>
                    <li><strong>Address:</strong> ${admin.address}</li>
                    <!-- Add more personal details as needed -->
                </ul>

                <!-- Edit Profile Button -->
                <a href="./edit-admin.jsp?id=${admin.id}&name=${admin.name}&email=${admin.email}&phone=${admin.phone}&address=${admin.address}&username=${admin.username}&password=${admin.password}"
                    class="btn btn-edit-profile">Edit Profile</a>
                <form action="LogoutServletAdmin" method="POST">
                    <button type="submit" class="btn-logout">Logout</button>
                </form>
            </section>

            <!-- Middle Section: Bank Account Details and Transactions -->
            <section class="middle-section">
                <h2>Send Money Form</h2><br>
                <form action="${pageContext.request.contextPath}/ProcessSearchCustomer" method="POST" onsubmit="showCustomerDetails();">
                    <label for="customerId">Enter Customer ID:</label>
                    <input type="text" id="customerId" name="customerId" required>
                    <button type="submit">Search Customer</button>
                </form>

                <!-- Customer Details Table (Initially hidden) -->
                <div id="customerTable" class="customer-container" style="display: block;">
                    <h2>Customer Details</h2>
                    <table class="customer-table">
    <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
    </thead>
    <tbody>
        <!-- Iterate over customers list -->
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.name}</td>
                <td>${customer.email}</td>
                <td>${customer.phone}</td>
                <td>${customer.address}</td>
                <td class="btn-cell">
                    <a href="./edit_admin_customer.jsp?id=${customer.id}&name=${customer.name}&email=${customer.email}&phone=${customer.phone}&address=${customer.address}&username=${customer.username}&password=${customer.password}"
                        class="btn btn-edit">Edit</a>
                </td>
                <td class="btn-cell">
                    <a href="./deleteCustomer?id=${customer.id}" class="btn btn-delete">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
                </div>
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
