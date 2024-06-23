package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;

import in.ineuron.dao.CustomerDAO;
import in.ineuron.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CustomerAddPage")
public class AddCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("AddCustomerServlet.doProcess()");
        CustomerDAO customerDAO = new CustomerDAO();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setUsername(username);
        customer.setPassword(password);
        try {
            customerDAO.addCustomer(customer);
            response.sendRedirect("./customer_dashboard.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
