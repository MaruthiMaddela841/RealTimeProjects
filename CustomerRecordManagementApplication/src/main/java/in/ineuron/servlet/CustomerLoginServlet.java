package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import in.ineuron.dao.BankAccountDAO;
import in.ineuron.dao.CustomerDAO;
import in.ineuron.dao.TransactionDAO;
import in.ineuron.model.BankAccount;
import in.ineuron.model.Customer;
import in.ineuron.model.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/CustomerLoginPage")
public class CustomerLoginServlet extends HttpServlet{

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
		
		System.out.println("CustomerLoginServlet.doProcess()");
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
        	CustomerDAO customerDAO=new CustomerDAO();
        	Customer customer = customerDAO.isCustomerAdmin(username, password);
        	System.out.println(customer);
        	if(customer.getId()!=0) {
        		BankAccountDAO bankAccountDAO = new BankAccountDAO();
            	System.out.println(customer.getId());
            	BankAccount account=bankAccountDAO.getAccountDetails(customer.getId());
            	TransactionDAO transactionDAO = new TransactionDAO();
            	List<Transaction> transactions = transactionDAO.getTransactionsByAccountId(account.getAccountId());
            	System.out.println(account);
            	transactions.forEach(System.out::println);
        		HttpSession session=request.getSession();
        		session.setAttribute("customer", customer);
        		session.setAttribute("account", account);
        		session.setAttribute("transactions", transactions);
        		response.sendRedirect("./customer_dashboard.jsp");
        	}
        	else {
        		response.sendRedirect("index.html?error=Invalid%20username%20or%20password");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

}
