package servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ViewResourceServlet")
public class ViewResourceServlet extends HttpServlet {

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private void displayError(HttpServletRequest request,
   HttpServletResponse response,String error) {
  
  response.setContentType("text/html");
  try {
   // Here we initialize the PrintWriter object
   PrintWriter out = response.getWriter();
   // Here we print HTML tags
   out.println("<html>");
   out.println("<head>");
   out.println("<title>View Resource Servlet Error Message</title>");
   out.println("</head>");
   out.println("<body>");
   out.println("<center>");
   out.println("<h1>Error</h1>");
   out.println("<p><b>Error:</b> " + error);
   out.println("<p><a href='index.html'>Back</a>");
   out.println("</center>");
   out.println("</body>");
   out.println("</html>");
   out.close();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
 public void doPost(HttpServletRequest request, HttpServletResponse response,String error)
   throws ServletException, IOException {
  // String separator = System.getProperty("file.separator");
  // String privateFilePath =
  // "/WEB-INF/private_files/".replace(File.separatorChar, '/');
  // String absolutePath = getServletContext().getRealPath(separator) +
  // separator;
  // In the following we initialize necessary objects
  URL url = null;
  URLConnection urlConnection = null;
  PrintWriter printWriter = null;
  BufferedReader reader = null;
  printWriter = response.getWriter();
  
  try {
   // Here we access the web resource within the web application
   // as a URL object
   // url = getServletContext().getResource(filePath);
   url = new URL(request.getParameter("site_name"));
   String searchPhrase = request.getParameter("search_phrase");
   response.setContentType("text/html");
   response.setCharacterEncoding("UTF-16");
   
   urlConnection = url.openConnection();
   // Here we establish connection with URL representing web.xml
   urlConnection.connect();
   // The following would be useful to read data in binary format
   /*
    * BufferedInputStream inputStream = new
    * BufferedInputStream(urlConnection.getInputStream()); int
    * readByte; while((readByte=inputStream.read())!=-1)
    * printWriter.write(readByte);
    */
   reader = new BufferedReader(new InputStreamReader(
     urlConnection.getInputStream()));
   String line = "";
   String urlContent = "";
   while ((line = reader.readLine()) != null) {
    urlContent += line + "\r\n";
   }
   
   
   int selectedIndex = -1;
   if (!searchPhrase.equals(""))
    selectedIndex = urlContent.indexOf(searchPhrase);
   if (selectedIndex != -1) {
    printWriter.write("<p>Search resul for " + searchPhrase
      + " from " + url.toString() + ":<br>");
    // Here we select the content starting from found search phrase
    printWriter.write(urlContent.substring(selectedIndex-1));
   } else {
    printWriter.write("<p>The content of " + url.toString()
      + "</p><br>:");
    printWriter.write(urlContent);
   }
   printWriter
     .println("<hr><center><a href='index.html'>Back</a></center>");
  } catch (Exception e) {
   error = "Something wrong with: " + url.toString() + " " + e;
   displayError(request, response,error);
  } finally {
   // Here we close the input/output streams
   if (printWriter != null)
    printWriter.close();
   if (reader != null) {
    reader.close();
   }
  }
 }
 public void doGet(HttpServletRequest request, HttpServletResponse response, String error)
   throws ServletException, IOException {
  // Here we redirect the request to the index page
  response.sendRedirect("index.html");
  
 
  return;
 }}