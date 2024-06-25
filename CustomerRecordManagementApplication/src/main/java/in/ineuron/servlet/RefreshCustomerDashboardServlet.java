package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import in.ineuron.dao.BankAccountDAO;
import in.ineuron.dao.TransactionDAO;
import in.ineuron.model.BankAccount;
import in.ineuron.model.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/refreshCustomerDashboard")
public class RefreshCustomerDashboardServlet extends HttpServlet{

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
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
		System.out.println("RefreshCustomerDashboardServlet.doProcess()");
		TransactionDAO transactionDAO = new TransactionDAO();
		System.out.println("From account No:"+request.getParameter("fromAccountNo"));
		List<Transaction> transactions = transactionDAO.getTransactionsByAccountId(Integer.parseInt(request.getParameter("fromAccountNo")));
		BankAccountDAO bankAccountDAO = new BankAccountDAO();
		int customerId=Integer.parseInt(request.getParameter("customerId"));
    	BankAccount account=bankAccountDAO.getAccountDetails(customerId);
		request.setAttribute("account", account);
		request.setAttribute("transactions", transactions);
		request.getRequestDispatcher("/customer_dashboard.jsp").forward(request, response);
	}

}
