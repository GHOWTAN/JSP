

package servlet;
import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

public class MultipleFileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileRepositoryPath;

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

		// Here we make sure to create the file repository directory.
		File repoDirFile = new File(fileRepositoryPath);
		if (!repoDirFile.exists())
			repoDirFile.mkdirs();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		out.println("<html><head><title>Multiple File Upload</title>"
				+ "</head><body><h2>Information about uploaded files</h2>");

		try {

			// Here we set the file limit size to 2 MB

			MultipartParser parser = new MultipartParser(request, 2 * 1024 * 1024);

			Part part = null;

			while ((part = parser.readNextPart()) != null) {

				if (part.isFile()) {

					// Here we get some info about the file

					FilePart filePart = (FilePart) part;

					String name = filePart.getFileName();

					if (name != null) {

						long fileSize = filePart.writeTo(new File(fileRepositoryPath));

						out.println("The user's file path for the file: " + filePart.getFilePath() + "<br>");

						out.println("The content type of the file: " + filePart.getContentType() + "<br>");

						out.println("The file size: " + fileSize + " bytes<br><br>");

					} else {

						out.println("<p>No file was uploaded for this empty part!</p>");

					}

				} else if (part.isParam()) {

					ParamPart parameter = (ParamPart) part;
					String paramName = parameter.getName();
					String paramValue = parameter.getStringValue();

					out.println("<p>Ordinary parameter: " + paramName + "=" + paramValue + "</p>");

				}

			} // end of while

			out.println("<br>");

			out.println("<hr>");

			out.println("<center>");
			out.println("<a href='index.html'>Back to Main Page</a>");

			out.println("</center></body>");

			out.println("</html>");

		} catch (java.io.IOException ioe) {

			// We should have mapped an error-page to the java.io.IOException

			// in the deployment descriptor

			/*
			 * throw new java.io.IOException("IOException occurred in: " +
			 * getClass().getName());
			 */

			out.println("<p>IOException occurred in: " + getClass().getName() + "</p>");

		}

	} // end of doPost()

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		response.sendRedirect("index.html");

	}

}