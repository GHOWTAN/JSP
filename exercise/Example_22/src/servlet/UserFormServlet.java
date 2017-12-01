
//This is the content of servlet.UserForm.java file:
package servlet;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/index.html")

public class UserFormServlet extends HttpServlet {
	// Here we define a directory for user information.
	Hashtable<String, String> userDirectory;

	public void init() {
		// Here we initialize the user information directory.
		userDirectory = new Hashtable<String, String>();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// Here we read the value of the submit button
		String action = request.getParameter("submit");

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String msg = request.getParameter("msg");
		boolean fNameCheck = checkParameter(firstName);
		boolean lNameCheck = checkParameter(lastName);
		/*
		 * boolean emailCheck = (email != null && !email.equals("") && email
		 * .indexOf("@") != -1);
		 */
		boolean emailCheck = checkParameter(email) && (email.indexOf("@") > 0);
		boolean msgCheck = checkParameter(msg);

		// Here we set the MIME type of the response, "text/html"
		response.setContentType("text/html");

		// Here we use a PrintWriter to send text data
		// to the client who has requested the servlet
		PrintWriter out = response.getWriter();
		// Here we start assembling the HTML content
		out.println("<html><head>");
		out.println("<title>User Registeration Page</title>");
		out.println("<link rel='stylesheet' type='text/css' href='styles.css'>");

		out.println("</head><body>");
		out.println("<div class='user_form'>");
		out.println("<h2>Please submit your information</h2>");
		// Here we set the value for method to post, so that
		// the servlet service method calls doPost in the
		// response to this form submit

		out.println("<form method='GET' + action='index.html'>");
		out.println("<table border><tr><td>");
		out.println("Your first name: </td><td ");

		if (action != null && !fNameCheck)
			out.println(" style='background-color:red;'");

		out.println("><input type='text' name='firstname' value='"
				+ (firstName == null ? "" : firstName)
				+ "' size='20'></td></tr>");

		out.println("<tr><td>Your last name: </td><td ");

		if (action != null && !lNameCheck)
			out.println(" style='background-color:red;'");
		out.println("><input type='text' name='lastname' value='"
				+ (lastName == null ? "" : lastName) + "' size='20'></td></tr>");

		out.println("<tr><td>Your email: </td><td ");

		if (action != null && !emailCheck)
			out.println(" style='background-color:red;'");

		out.println("><input type='text' name='email' value='"
				+ (email == null ? "" : email) + "' size='20'></td></tr>");

		out.println("<tr><td>Your message: </td><td ");

		if (action != null && !msgCheck)
			out.println(" style='background-color:red;'");

		out.println("><textarea name='msg' rows='10' cols='30'>");
		if (msg != null)
			out.println(msg);

		out.println("</textarea></td></tr>");

		out.println("<tr><td></td><td><input type='submit' name='submit' value='Submit Info'></td></tr>");
		out.println("</table></form>");

		out.println("</div>");

		out.println("</body></html>");
		if (fNameCheck && lNameCheck && emailCheck && msgCheck)
			doPost(request, response);
	}

	private boolean checkParameter(String parameter) {
		if (parameter != null && !parameter.equals("")
				&& !parameter.equals("null"))
			return true;

		return false;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String msg = request.getParameter("msg");

		// Here we reset the response
		response.reset();
		// Here we set the MIME type of the response, "text/html"
		response.setContentType("text/html");
		// Here we use a PrintWriter to send text data to the client
		PrintWriter out = response.getWriter();
		// Here we start assembling the HTML content
		out.println("<html><head>");
		out.println("<title>Registered Users</title></head><body>");
		out.println("<center>");
		userDirectory.put(firstName + " " + lastName + " (" + email + ")", msg);

		Enumeration<String> hashTableKeys = userDirectory.keys();
		while (hashTableKeys.hasMoreElements()) {
			String selectedKey = hashTableKeys.nextElement();
			out.println("<p>" + selectedKey + " --> "
					+ userDirectory.get(selectedKey));
		}

		out.println("<br><br><a href='index.html'>Back</a>");
		out.println("</center>");
		out.println("</body></html>");
	}
}
