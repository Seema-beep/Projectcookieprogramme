package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDaoImpl;
import pojos.Customer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(value = "/validate", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDaoImpl dao;

	@Override
	// overrding form of the method CAN'T add any BROADER checked excs.
	public void init() throws ServletException {
		try {
			dao = new CustomerDaoImpl();
		} catch (Exception e) {
			// Inform WC that init() has failed --so DON'T proceed to service
			throw new ServletException("err in init of " + getClass().getName(), e);

		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	@Override
	// overrding form of the method CAN'T add any NEW checked excs.
	public void destroy() {
		try {
			dao.cleanUp();
		} catch (Exception e) {
			// since it's invoked @ end of life cycle : may not inform it's details to WC
			// System.out.println("err in destroy "+e);
			throw new RuntimeException("err in destroy of " + getClass().getName(), e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set cont type
		response.setContentType("text/html");
		// pw
		try (PrintWriter pw = response.getWriter()) {
			// get req params
			String email = request.getParameter("em");
			String password = request.getParameter("pass");
			// invoke DAO's method for validation
			Customer authenticateCustomer = dao.authenticateCustomer(email, password);
			if (authenticateCustomer == null)
				pw.print("<h5>Invalid Login , Please <a href='login.html'>Retry</a></h5>");
			else {

				// success
				// create a cookie wrapping auth cust details n add it resp hdr --to be sent to
				// clnt
				Cookie c1 = new Cookie("cust_details", authenticateCustomer.toString());
				response.addCookie(c1);
				// add a link to proceed
				pw.print("<h5>Please <a href='category'>Proceed</a></h5>");
				// what will be URL of the NEXT req : http://host:port/day2.1/category
			}

		} catch (Exception e1) {
			throw new ServletException("err in do-get of " + getClass().getName(), e1);
		}
	}

}
