package android;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		BookDAO dao = new BookDAO();

		if (url.indexOf("xml.do") != -1) {
			String result = dao.xml();
			response.getWriter().println(result);
			//		 출력객체(브라우저)		 xml 코드 브라우저 출력
		} else if (url.indexOf("json.do") != -1) {
			String result = dao.json();
			response.getWriter().println(result);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
