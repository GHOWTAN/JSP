package servlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
public class MultipleFileUploadRenameServlet extends HttpServlet {
 private String fileRepositoryPath;
 private File repoDirFile;
 private String[] uploadedFileList;
 public void init() {
 
  // Here we save files to a uploaded_files directory in the webapps
  String separator = System.getProperty("file.separator");
  fileRepositoryPath = getServletContext().getRealPath(separator)
  + getServletContext().getInitParameter("upload_dir") + separator;
  repoDirFile = new File(fileRepositoryPath);
  // Here we make sure to create the file repository directory.
  if (!repoDirFile.exists())
   repoDirFile.mkdir();
 }
 
 
 public void doPost(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, java.io.IOException {
 
  String userName = "";
  CustomFileRenamePolicy customFileRenamePolicy = null;
  response.setContentType("text/html");
  PrintWriter out = response.getWriter();
  out.println("<html>" + "<head>" + "<title>Multiple File Upload</title>"
  + "</head>" + "<body>"
  + "<h2>Information about uploaded files</h2>");
 
 
  try {
   // Here we set the file limit size to 5 MB
 
   MultipartParser parser = new MultipartParser(request,
     3 * 1024 * 1024);
   Part part = null;
   while ((part = parser.readNextPart()) != null) {
    if (part.isParam()) {
     // Here we define parameter, which is a ParamPart
     // by type casting part object.
     ParamPart parameter = (ParamPart) part;
     // Here we initialize userName variable with the
     // value of parameter object
     if (parameter.getName().equals("username"))
      userName = parameter.getStringValue();
    
     // In the following we print error message if user name
     // is missing.
     if (userName == null || userName.equals("")) {
     
      response.sendError(401, "Unauthorized");
      //response.sendRedirect("error.html");
     }
     
    
     out.println("<p>File upload for " + userName + "!</p><br>");
     // Here we initialize the customFileRenamePolicy object with
     // given user name
     customFileRenamePolicy = new CustomFileRenamePolicy(userName);
    }
    else if (part.isFile()) {
     // Here we get some info about the file
     FilePart filePart = (FilePart) part;
     String fileName = filePart.getFileName();
     if (fileName != null && customFileRenamePolicy !=null) {
      // Here we would use the default file renaming policy to
      // enumerate uploaded files with the same name.
      // filePart.setRenamePolicy(new
      // DefaultFileRenamePolicy());
      filePart.setRenamePolicy(customFileRenamePolicy);
      long fileSize = filePart.writeTo(new File(fileRepositoryPath));
      out.println("The user's file path for the file: "
      + filePart.getFilePath() + "<br>");
      out.println("The content type of the file: "
      + filePart.getContentType() + "<br>");
      out.println("The file size: " + fileSize
      + " bytes<br><br>");
     } else {
      out.println("No file was uploaded for this part!<br><br>");
     }
    }
    /*
     *
     * else if(part.isParam()){ out.println( part.toString() +
     *
     * " is not a file!"); }
     */
   }// end of while
   out.println("<br>");
  
   out.println("<p>The list of uploaded files:</p>");
   uploadedFileList = repoDirFile.list();
   out.println("<p style='color:red'>Uploaded files:</p>");
   out.println("<ul>");
   for (String f : uploadedFileList) {
    out.println("<li>" + f + "</li>");
   }
   out.println("</ul>");
   out.println("<hr>");
   out.println("<a href='index.html'>Back to Main Page</a>");
   out.println("</body>");
   out.println("</html>");
   // Here we close the output stream.
   if (out != null)
    out.close();
  } catch (IOException e) {
  
   response.sendError(403, "Forbidden");
   //response.sendRedirect("error.html");
  
  }
 }// end of doPost()
 public void doGet(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, java.io.IOException {
  // Here we redirect the request to the index page
  response.sendRedirect("index.html");
  return;
 }
}