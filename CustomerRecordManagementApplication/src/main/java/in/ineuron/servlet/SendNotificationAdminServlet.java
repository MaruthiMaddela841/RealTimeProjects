package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;

import in.ineuron.dao.NotificationDAO;
import in.ineuron.model.Notification;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sendNotificationAdmin")
public class SendNotificationAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doProcess(req, resp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doProcess(req, resp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException {
		System.out.println("SendNotificationAdminServlet.doProcess()");
		String message=request.getParameter("message");
		int customer_id=Integer.parseInt(request.getParameter("customerId"));
		int admin_id=Integer.parseInt(request.getParameter("adminId"));
		System.out.println(customer_id);
		System.out.println(message);
		System.out.println(admin_id);
		NotificationDAO notificationDAO = new NotificationDAO();
		Notification notification=new Notification();
		notification.setAdmin_id(admin_id);
		notification.setCustomer_id(customer_id);
		notification.setMessage(message);
		notificationDAO.sendNotificationToCustomer(notification);
//		HttpSession session=request.getSession();
//		session.setAttribute("admin", admin);
		response.sendRedirect("dashboard");
	}

}
