package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.*;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class JavaAndArrayServlet extends HttpServlet {

	PrintWriter printWriter;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String request = req.getParameter("request");
			printWriter = resp.getWriter();
			resp.setContentType("text/html");
			
			switch (Integer.parseInt(request)) {
			case 0:
				processHello(req, resp);
				break;
			case 1:
				processCalc(req, resp);
				break;
			}
		} catch (MalformedInputException exception) {
			printWriter.println(exception.getMessage());
		} catch (IOException exception) {
			printWriter.println(exception.getMessage());
		}
	}

	private void processHello(HttpServletRequest req, HttpServletResponse resp) throws IOException, MalformedInputException {
		printWriter.println("Hello world!");
	}

	private void processCalc(HttpServletRequest req, HttpServletResponse resp) throws IOException, MalformedInputException {
		String var1 = req.getParameter("var1");
		String var2 = req.getParameter("var2");
		String operation = req.getParameter("operation");
		
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(var1);
		boolean isMalformed = !matcher.matches();
		matcher = pattern.matcher(var2);
		isMalformed |= !matcher.matches();

		if (isMalformed) {
			throw new MalformedInputException();
		}

		int i1 = Integer.parseInt(var1);
		int i2 = Integer.parseInt(var2);

		int result;
		if (operation.equals("0")) {
			result = i1 + i2;
		} else if (operation.equals("1")) {
			result = i1 - i2;
		} else if (operation.equals("2")) {
			result = i1 * i2;
		} else {
			result = i1 / i2;
		}

		resp.getWriter().println(result);
	}
}
