package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class DomRabServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter pw = resp.getWriter();
		String min = req.getParameter("intMin");
		String max = req.getParameter("intMax");
		resp.setContentType("text/html");
		int minInt = 0;
		int maxInt = 0;
		try {
			minInt = Integer.parseInt(min);
			maxInt = Integer.parseInt(max);
		} catch (Exception e) {
			pw.println("Invalid input format2");
		}
		printInt(minInt, maxInt, resp);

	}

	static void printInt(int min, int max, HttpServletResponse resp)
			throws IOException {
		PrintWriter pw = resp.getWriter();
		pw.println("<html>"
				+ "<head> "
				+ "<style>.text {color:red;}</style>"
				+ "<title></title>"
				+ "<style>td {width:10%;display:block}</style>"
				+ "<meta http-equiv =\"Content-Type\"content=\"text/html;charset=Unicode\">"
				+ "</head>" + "<body>" + "<table border=1 width = 1000 >");
		pw.println("<tr>");
		for (int i = min; i < max; i++) {
			pw.println("<td>");
			pw.println("<span class=\"text\">&#" + i + "</span> code0d: " + i
					+ " code0x: " + Integer.toHexString(i));
			pw.println("</td>");
			if (i != min && (i - min + 1) % 10 == 0) {
				pw.println("</tr>");
				pw.println("<tr>");
			}
		}
		pw.println("</tr>");
		pw.println("	</table>" + "</body>" + "</html>");
		pw.close();

	}

}
