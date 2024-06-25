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

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("UpdateCustomersServlet.doProcess()");
		CustomerDAO customerDAO = new CustomerDAO();
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("ID is:"+id);
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setUsername(username);
        customer.setPassword(password);

        try {
            customerDAO.updateCustomer(customer);
            Customer customerNew=customerDAO.getCustomer(id);
            System.out.println("New Customer Details::"+customerNew);
            request.setAttribute("customer",customerNew);
            request.getRequestDispatcher("./customer_dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
