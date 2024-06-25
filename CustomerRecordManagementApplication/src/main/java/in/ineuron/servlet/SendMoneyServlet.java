package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

@WebServlet("/ProcessSendMoneyServlet")
public class SendMoneyServlet extends HttpServlet{

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
		System.out.println("SendMoneyServlet.doProcess()");
		int fromAccountNo=Integer.parseInt(request.getParameter("accountId"));
		String fromAccountName = request.getParameter("fromAccountNo");
		String toAccountName = request.getParameter("toAccountNo");
		String transactionType=request.getParameter("transactionType");
		String amountParam = request.getParameter("amount");
		int customerId=Integer.parseInt(request.getParameter("customerId"));
		Double amount = null;
		if (amountParam != null && !amountParam.isEmpty()) {
		    try {
		        amount = Double.parseDouble(amountParam);
		    } catch (NumberFormatException e) {
		        // Handle parsing exception if necessary
		        e.printStackTrace(); // Example: log the error
		    }
		}
		String description=request.getParameter("description");
		TransactionDAO transactionDAO = new TransactionDAO();
		int toAccountNo=transactionDAO.getAccountId(toAccountName);
		if(toAccountNo==-1) {
			request.setAttribute("toAccountNo",toAccountNo);
			request.setAttribute("fromAccountNo", fromAccountNo);
			request.setAttribute("customerId", customerId);
            request.getRequestDispatcher("/issue_messages.jsp").forward(request, response);
		}
		Transaction transaction=new Transaction();
		transaction.setFromAccountId(fromAccountNo);
		transaction.setToAccountId(toAccountNo);
		transaction.setTransactionType(transactionType);
		transaction.setDescription(description);
		transaction.setAmount(amount);
		if(fromAccountNo==toAccountNo) {
			int sameAccount=1;
			request.setAttribute("sameAccount",sameAccount);
			request.setAttribute("fromAccountNo", fromAccountNo);
			request.setAttribute("customerId", customerId);
            request.getRequestDispatcher("/issue_messages.jsp").forward(request, response);
            return;
		}
		Double amountFromDb=transactionDAO.checkEnoughBalance(transaction);
		if(amount>amountFromDb) {
			request.setAttribute("amountFromDb",amountFromDb);
			request.setAttribute("fromAccountNo", fromAccountNo);
			request.setAttribute("customerId", customerId);
            request.getRequestDispatcher("/issue_messages.jsp").forward(request, response);
            return;
		}
		int addTransaction=transactionDAO.addTransaction(transaction);
		System.out.println(addTransaction);
		if(addTransaction!=-1) {
			List<Transaction> transactions = transactionDAO.getTransactionsByAccountId(fromAccountNo);
			BankAccountDAO bankAccountDAO = new BankAccountDAO();
        	BankAccount account=bankAccountDAO.getAccountDetails(customerId);
			request.setAttribute("account", account);
			request.setAttribute("transactions", transactions);
			request.getRequestDispatcher("/customer_dashboard.jsp").forward(request, response);
		}
		else {
			int transactionNotAdded=1;
			request.setAttribute("transactionNotAdded",transactionNotAdded);
			request.setAttribute("fromAccountNo", fromAccountNo);
			request.setAttribute("customerId", customerId);
            request.getRequestDispatcher("/issue_messages.jsp").forward(request, response);
            return;
		}
		
	}

}
