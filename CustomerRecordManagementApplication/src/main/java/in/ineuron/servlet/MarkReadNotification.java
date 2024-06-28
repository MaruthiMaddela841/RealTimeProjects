package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import in.ineuron.dao.NotificationDAO;
import in.ineuron.model.Notification;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/readNotification")
public class MarkReadNotification extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doProcess(req, resp);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doProcess(req, resp);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
		System.out.println("MarkReadNotification.doProcess()");
		int id=Integer.parseInt(request.getParameter("notificationId"));
		int customer_id=Integer.parseInt(request.getParameter("customerId"));
		System.out.println(id);
		System.out.println(customer_id);
		NotificationDAO notificationDAO = new NotificationDAO();
		notificationDAO.markNotification(id);
		HttpSession session=request.getSession();
		List<Notification> notifications = notificationDAO.getNotification(customer_id);
		notifications.forEach(System.out::println);
		session.setAttribute("notifications", notifications);
		request.getRequestDispatcher("./customer_dashboard.jsp").forward(request, response);
	}

}
