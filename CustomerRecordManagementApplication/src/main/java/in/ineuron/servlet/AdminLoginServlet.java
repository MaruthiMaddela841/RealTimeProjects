package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;

import in.ineuron.dao.AdminDAO;
import in.ineuron.model.Admin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminLoginPage")
public class AdminLoginServlet extends HttpServlet{

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

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		System.out.println("LoginServlet.doProcess()");
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
        	AdminDAO adminDAO=new AdminDAO();
        	Admin admin = adminDAO.login(username, password);
        	System.out.println(admin);
        	if(admin!=null) {
        		HttpSession session=request.getSession();
        		session.setAttribute("admin", admin);
        		response.sendRedirect("dashboard");
        	}
        	else {
                response.sendRedirect("index.jsp?error=Invalid%20username%20or%20password");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	

}
