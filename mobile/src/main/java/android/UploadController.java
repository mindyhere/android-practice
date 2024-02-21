package android;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10, location = "c:/upload/")
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filename = "-";
		int filesize = 0;

		try {
			for (Part part : request.getParts()) {
				filename = part.getSubmittedFileName();
				if (filename != null && !filename.equals("null") && !filename.equals("")) {
					filesize = (int) part.getSize();
					part.write(filename);
					response.getWriter().println("success! fileName: " + filename + "(fileSize: " + filesize + ")");
					// response.getWriter() → 브라우저에 출력할 수 있는 객체생성
					// println → 웹브라우저에 출력할 내용
					break;
				}
			}
		} catch (Exception e) {
			response.getWriter().println("error: " + e.toString());
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
