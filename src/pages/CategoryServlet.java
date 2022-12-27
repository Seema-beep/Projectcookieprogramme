package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			pw.print("<h5>Login Successful </h5>");
			// check for cookies : if available : look for a cookie "cust_details" --get its
			// value
			// o.w give err mesg
			// get cookies
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				// cookies are avlable
				for (Cookie c : cookies)
					if (c.getName().equals("cust_details"))
						pw.print("Customer Details via Cookie  " + c.getValue());
			} else
				pw.print("<h5>No Session Tracking!!!!!</h5>");

			pw.print("<h5><a href='logout'>Log Me Out</a></h5>");
		}
	}

}
