package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendNonHTMLFilesServlet extends HttpServlet {

	String separator = File.separator;
	String downloadDir;

	public void init() {

		// Here we specify the path to the directory where downloadable files
		// reside.
		// We read the name of the download directory from web.xml file, where
		// the name of
		// the directory is specified under "download-dir" parameter.
		downloadDir = this.getServletContext().getRealPath(getServletContext().getInitParameter("download-dir"))
				+ separator;
	}

	private void sendHTMLErrorMessage(HttpServletRequest request, HttpServletResponse response, String error) {
		response.setContentType("text/html");
		try {
			// Here we initialize the PrintWriter object
			PrintWriter out = response.getWriter();
			// Here we print HTML tags
			out.println("<html>");
			out.println("<head>");
			out.println("<title>File Download Error Message</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Error</h1>");
			out.println("<p><b>Error:</b> " + error);
			out.println("<p><a href='index.html'>Back</a>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getContentType(String fileName) {
		String fileExt = "";
		String contentType = "";
		int i;
		if ((i = fileName.indexOf(".")) != -1) {
			/*
			 * Here we read s substring of filename starting from ".", which
			 * will be the file extension
			 */
			fileExt = fileName.substring(i);
		}
		if (fileExt.equals("doc") || fileExt.equals("docx"))
			contentType = "application/msword";
		else if (fileExt.equals("pdf"))
			contentType = "application/pdf";
		else if (fileExt.equals("mp3"))
			contentType = "audio/mpeg";
		else if (fileExt.equals("jpg") || fileExt.equals("gif") || fileExt.equals("tif") || fileExt.equals("jpeg"))
			contentType = "application/img";
		else if (fileExt.equals("xml"))
			contentType = "txt/xml";
		else if (fileExt.equals("rtf"))
			contentType = "application/rtf";
		else
			contentType = "application/unknown";
		return contentType;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		String error = "";
		String fileName = request.getParameter("fileName");
		if (fileName == null || fileName.equals("")) {
			// throw new ServletException("Null or invalid file name!");
			error = "File name was missing! Please give a file name!";
			sendHTMLErrorMessage(request, response, error);
			return;
		}
		// Here we check where PDF files are kept
		// String
		// downloadDir=getServletContext().getInitParameter("download-dir");
		File downloadFile = new File(downloadDir + fileName);
		if (!downloadFile.exists()) {
			// throw new
			// ServletException("Invalid or non-existent 'pdf-dir
			// context-param!");
			error = fileName + " does not exist!";
			sendHTMLErrorMessage(request, response, error);
			return;
		}
		ServletOutputStream outputStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			outputStream = response.getOutputStream();
			// Here we set the response headers
			response.setContentType(getContentType(fileName));
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setContentLength((int) downloadFile.length());
			// Here we read the content of the file.
			/*
			 * FileInputStream inputStream = new FileInputStream(downloadFile);
			 * inputStreamBuffer = new BufferedInputStream(inputStream);
			 */
			bufferedInputStream = new BufferedInputStream(new FileInputStream(downloadFile));
			int readBytes = 0;
			// Here we read from the file and write to the ServletOutputStream
			while ((readBytes = bufferedInputStream.read()) != -1)
				outputStream.write(readBytes);
		} catch (IOException ioex) {
			// throw new ServletException(ioex.getMessage());
			error = ioex.getMessage();
			sendHTMLErrorMessage(request, response, error);
		} finally {
			// Here we close the input/output streams
			if (outputStream != null)
				outputStream.close();
			if (bufferedInputStream != null)
				bufferedInputStream.close();
		}
	} // This is the end of doGet() method

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		response.sendRedirect("index.html");
	}
}