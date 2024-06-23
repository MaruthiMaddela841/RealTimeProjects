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

@WebServlet("/updateCustomerDetails")
public class UpdateCustomersServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("UpdateCustomersServlet.doProcess()");
		CustomerDAO customerDAO = new CustomerDAO();
		int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);

        try {
            customerDAO.updateCustomer(customer);
            response.sendRedirect("dashboard");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
