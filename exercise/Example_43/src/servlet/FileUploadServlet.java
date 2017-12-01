package servlet;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String fileRepositoryPath;
	private File repositoryFile;

	public void init() {
		/*
		 * Below we set the path for the directory where uploaded files are
		 * saved
		 *
		 * getServletContext().getRealPath(separator) returns the path to the
		 * root directory of
		 *
		 * the servlet. Variable separator indicates the directory separator on
		 * the system.
		 */
		String separator = System.getProperty("file.separator");
		fileRepositoryPath = getServletContext().getRealPath(separator)
				+ getServletContext().getInitParameter("upload_dir") + separator;
		// Here we create the directory if it does not exist
		repositoryFile = new File(fileRepositoryPath);
		if (!repositoryFile.exists())
			repositoryFile.mkdirs();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		/*
		 * Here we set the limit size of 2 MB We set the destination directory
		 * to fileRepositoryPath and use our own file renaming policy; an
		 * instance of CustomFileRenamePolicy and then upload the files.
		 */
		MultipartRequest mpr = new MultipartRequest(request, fileRepositoryPath, 2 * 1024 * 1024,
				new CustomFileRenamePolicy());

		// Here we would use the default file renaming policy,
		// which would rename similar file names by adding an integer
		// number to the name of the file

		/*
		 * MultipartRequest mpr=new MultipartRequest(request,
		 * fileRepositoryPath, 2*1024*1024, new DefaultFileRenamePolicy());
		 */

		// Here we would overwrite files with the same name
		/*
		 * MultipartRequest mpr=new MultipartRequest(request,
		 * fileRepositoryPath, 2*1024*1024);
		 */

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>File Upload Servlet</title>");
		out.println("</head>");
		out.println("<body>");

		out.println("<p>The list of uploaded files:</p>");
		out.println("<ul>");
		String[] uploadedFiles = repositoryFile.list();

		for (String fileName : uploadedFiles) {
			out.println("<li>" + fileName + "</li>");
		}

		out.println("</ul>");

		// Here we read the names of the files to an enumeration
		/*
		 * Enumeration<String> fileEnum = mpr.getFileNames(); for (int i = 1;
		 * fileEnum.hasMoreElements(); i++) { String fileName =
		 * mpr.getFilesystemName(fileEnum.nextElement()); out.println(
		 * "The name of uploaded file " + i + " is: " + fileName + "<br><br>");
		 * }
		 */

		out.println("<hr>");
		out.println("<a href='index.html'>Back to File Upload</a>");
		out.println("</body>");
		out.println("</html>");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		// Here we redirect Http GET requests to the starting page
		response.sendRedirect("index.html");
		/*
		 * response.setContentType("text/html"); PrintWriter out =
		 * response.getWriter(); out.println("<html>"); out.println("<head>");
		 * out.println("<title>File Upload Servlet</title>");
		 * out.println("</head>"); out.println("<body>"); out.println(
		 * "<p>This servlet should be called through HTTP POST method!</p>");
		 * out.println("</body>"); out.println("</html>");
		 */
	}
}