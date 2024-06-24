package in.ineuron.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ProcessSendMoneyServlet")
public class SendMoneyServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(req, resp);
	}
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("SendMoneyServlet.doProcess()");
		String fromAccountNo = request.getParameter("fromAccountNo");
		String toAccountNo = request.getParameter("toAccountNo");
		String amountParam = request.getParameter("amount");
		Double amount = null;
		if (amountParam != null && !amountParam.isEmpty()) {
		    try {
		        amount = Double.parseDouble(amountParam);
		    } catch (NumberFormatException e) {
		        // Handle parsing exception if necessary
		        e.printStackTrace(); // Example: log the error
		    }
		}
		
		
	}

}
