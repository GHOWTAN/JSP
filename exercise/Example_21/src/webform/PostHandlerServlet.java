package webform;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostHandlerServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * Here we use the ServletRequest.getParameter(String name) method to
		 * read
		 * 
		 * the values of the parameters in the HTML form
		 */

		String userName = request.getParameter("userName");

		String productName = request.getParameter("productName");

		String unitPrice = request.getParameter("unitPrice");

		String[] purchaseOpt = request.getParameterValues("purchaseOptions");

		// Here we set the content type of the printed page by the servlet

		response.setContentType("text/html");

		// Here we initialize the PrintWriter object

		PrintWriter out = response.getWriter();

		// Here we print HTML tags

		out.println("<html>");

		out.println("<head>");

		out.println("<title>Welcome</title>");

		out.println("</head>");

		out.println("<body>");

		out.println("<h1>Product Registration Results</h1>");

		// Here we at the same time check the value of "name" parameter to make
		// sure that it is not null or empty

		// and print its value accordingly

		out.println("Your name is: "
				+ ((userName == null || userName.equals("")) ? "Unknown"
						: userName));

		out.println("<br><br>");

		// Here we at the same time check the value of "department" parameter to
		// make sure that it is not null or empty

		// and print its value accordingly

		out.println("Product name is: "
				+ ((productName == null || productName.equals("")) ? "Unknown"
						: productName));

		out.println("<br><br>");

		// Here we at the same time check the value of "email" parameter to make
		// sure that it is not null or empty

		// and print its value accordingly

		out.println("Product unit price is: "
				+ ((unitPrice == null || unitPrice.equals("")) ? "Unknown"
						: unitPrice));

		out.println("Purchase options are:");
		out.println("<ul>");

		if (purchaseOpt != null) {
			for (String opt : purchaseOpt)
				out.println("<li>" + opt);
		}
		out.println("</ul>");

		out.println("<hr>");

		out.println("<h2>Here we use HttpServletRequest.getParameterMap</h2>");

		Map<String, String[]> paramMap = request.getParameterMap();

		if (paramMap != null) {

			/*
			 * Here we iterate through the java.util.Map and display posted
			 * parameter values. The keys of the Map.Entry objects are type
			 * String; the values are type String[], or String array.
			 */

			/*
			 * Here we define an iterator over the entry set of the Map.
			 * Iterator takes the place of Enumeration in the Java collections
			 * framework.
			 */

			Iterator<Entry<String, String[]>> iterator = paramMap.entrySet().iterator();

			// Set keys = paramMap.keySet();

			while (iterator.hasNext()) {

				Entry<String, String[]> me = (Entry<String, String[]>) iterator.next();

				out.println(me.getKey() + ":");
				out.println("<ul>");

				String[] parameterValues = (String[]) me.getValue();

				/*
				 * for (int i = 0; i < parameterValues.length; i++) {
				 * out.println((parameterValues[i]==null ||
				 * parameterValues[i]=="" ? "Unknown" : "<li>" +
				 * parameterValues[i]));
				 */

				for (String str : parameterValues)
					out.println((str == null || str == "" ? "<li>Unknown" : "<li>"
							+ str));

				// Here we print commas after multiple values

				// except for the last one

				/*
				 * if (i > 0 && i != parameterValues.length - 1)
				 * 
				 * out.println(",");
				 */

				out.println("</ul>");
			}

			out.println("<hr>");

		}

		out.println("<h2>Reading parameters and their values with HttpServletRequest.getParameterNames()</h2>");

		Enumeration<String> en = request.getParameterNames();

		while (en.hasMoreElements()) {

			String parameter = en.nextElement();

			String[] paramValues = request.getParameterValues(parameter);

			out.println("<p>Values of paramter <b>" + parameter + "</b>");
			out.println("<ul>");

			/*
			 * for(int i=0; i<paramValues.length; i++) out.println("<li>" +
			 * paramValues[i]);
			 */

			for (String str : paramValues)
				out.println("<li>" + str);

			out.println("</ul>");
		}

		// Here we make a link back to the registration page

		out.println("<center><a href='index.html'>Main</a></center>");

		out.println("</body>");

		out.println("</html>");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);

	}

}
