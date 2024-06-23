package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;

import in.ineuron.dao.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteCustomer")
public class DeleteCustomerServlet extends HttpServlet{

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
		System.out.println("DeleteCustomerServlet.doProcess()");
		CustomerDAO customerDAO = new CustomerDAO();
		int id = Integer.parseInt(request.getParameter("id"));
        try {
            customerDAO.deleteCustomer(id);
            response.sendRedirect("dashboard");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
