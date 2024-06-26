package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;

import in.ineuron.dao.AdminDAO;
import in.ineuron.model.Admin;
import in.ineuron.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateAdminDetails")
public class UpdateAdminServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doProcess(req,resp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doProcess(req,resp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		System.out.println("UpdateCustomersServlet.doProcess()");
		AdminDAO adminDAO = new AdminDAO();
		int id = Integer.parseInt(request.getParameter("adminId"));
		System.out.println("ID is:"+id);
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        Admin admin = new Admin();
        admin.setId(id);
        admin.setName(name);
        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setAddress(address);
        admin.setUsername(username);
        admin.setPassword(password);

        adminDAO.updateAdmin(admin);
		Admin adminNew=adminDAO.getAdmin(id);
		System.out.println("New Customer Details::"+adminNew);
		request.setAttribute("admin",adminNew);
		request.getRequestDispatcher("./admin_dashboard.jsp").forward(request, response);
	}

}
