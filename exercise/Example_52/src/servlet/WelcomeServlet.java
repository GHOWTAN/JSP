

package servlet;

import java.io.IOException; import java.io.PrintWriter; import java.util.Date;

import javax.servlet.ServletException; import javax.servlet.http.HttpServlet; import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpServletResponse;

public class WelcomeServlet extends HttpServlet{

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

  response.setContentType( "text/html" );

PrintWriter out=response.getWriter();

out.println( "<html>" );

out.println( "<head>" );

out.println( "<title>Dynamic Update</title>" );

out.println( "</head>" );

out.println( "<body bgcolor='#fff90e'>" );

out.println( "<h1>Welocme to the world!</h1>" );

out.println( "<p>Something will happen here...</P>" );

out.println("<hr/>");

out.println("Date is : " + new Date() + " Good day there...!");

out.println( "</body>" );

out.println( "</html>" );

}

}
