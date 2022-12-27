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
 * Servlet implementation class LogOutServlet
 */
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			pw.print("<h5>In logout page</h5>");
			// retrieve clnt details from a cookie
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies)
					if (c.getName().equals("cust_details")) {
						pw.print("Customer Details via Cookie  " + c.getValue());
						// set max age=0 n send cookie again in resp hdr
						c.setMaxAge(0);
						response.addCookie(c);

					}
			} else
				pw.print("<h5>No Session Tracking!!!!!</h5>");

			pw.print("<h5><a href='login.html'>Visit Again</a></h5>");

		}

	}
}
