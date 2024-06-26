package in.ineuron.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.ineuron.dao.CustomerDAO;
import in.ineuron.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProcessSearchCustomer")
public class AdminGetCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AdminGetCustomerServlet.doProcess()");
        int id=Integer.parseInt(request.getParameter("customerId"));
        List<Customer> customers=new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            Customer customer = customerDAO.getCustomer(id);
            customers.add(customer);
            HttpSession session=request.getSession();
    		session.setAttribute("customers", customers);
            request.getRequestDispatcher("./admin_dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
}
