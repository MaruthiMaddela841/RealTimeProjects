package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import in.ineuron.dao.CustomerDAO;
import in.ineuron.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doProcess(req,resp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doProcess(req,resp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
	
		System.out.println("DashboardServlet.doProcess()");
		CustomerDAO customerDAO=new CustomerDAO();
		try {
            List<Customer> customers = customerDAO.getAllCustomers();
            System.out.println(customers);
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("./admin_dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
