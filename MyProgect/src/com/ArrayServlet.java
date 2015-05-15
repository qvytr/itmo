package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ArrayServlet extends HttpServlet {
	PrintWriter printWriter;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {

		try {
			String request = req.getParameter("request");
			printWriter = resp.getWriter();
			resp.setContentType("text/html");

			switch (Integer.parseInt(request)) {
			case 0:
				printInt(resp, req);
				break;
			case 1:
				printAbc(resp, req);
				break;
			case 2:
				printMatrix(resp, req);
				break;
			case 3:
				printMatrixM(resp);
				break;
			}
		} catch (MalformedInputException exception) {
			printWriter.println(exception.getMessage());
		} catch (IOException e) {

		}
	}

	private void printInt(HttpServletResponse resp, HttpServletRequest req)
			throws IOException, MalformedInputException {
		String var1 = req.getParameter("first");
		String var2 = req.getParameter("second");

		Pattern pattern = Pattern.compile("(\\d)*(\\.)(\\d)+");
		Matcher matcher = pattern.matcher(var1);
		boolean isMalformed = !matcher.find();

		matcher = pattern.matcher(var2);
		isMalformed |= !matcher.find();

		if (isMalformed) {
			throw new MalformedInputException();
		}

		double min = Double.parseDouble(var1);
		double max = Double.parseDouble(var2);

		if (Math.abs(min - 10) <= Math.abs(max - 10)) {
			printWriter.println(min);
		} else {
			printWriter.println(max);
		}
	}

	private void printAbc(HttpServletResponse resp, HttpServletRequest req)
			throws IOException, MalformedInputException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String c = req.getParameter("c");

		Pattern pattern = Pattern.compile("(\\d)*(\\.)(\\d)+");
		Matcher matcher = pattern.matcher(a);
		boolean isMalformed = !matcher.matches();

		matcher = pattern.matcher(b);
		isMalformed |= !matcher.matches();

		matcher = pattern.matcher(c);
		isMalformed |= !matcher.matches();

		if (isMalformed) {
			throw new MalformedInputException();
		}

		double aa = Double.parseDouble(a);
		double bb = Double.parseDouble(b);
		double cc = Double.parseDouble(c);
		if (bb * bb - 4 * aa * cc >= 0) {
			printWriter.println("x1="
					+ (-bb - Math.sqrt(bb * bb - 4 * aa * cc)) / 2 / aa
					+ " x2=" + (-bb + Math.sqrt(bb * bb - 4 * aa * cc)) / 2
					/ aa);
		} else {
			printWriter.println("Нет корней");
		}
	}

	private void printMatrix(HttpServletResponse resp, HttpServletRequest req)
			throws IOException, MalformedInputException {
		String[][] matrix = new String[8][5];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				matrix[i][j] = Integer
						.toString((int) (Math.random() * 90 + 10));
				printWriter.print(matrix[i][j] + " ");
			}
			printWriter.println("<br>");
		}
	}

	private void printMatrixM(HttpServletResponse resp)
			throws IOException, MalformedInputException {
		int[][] matrix = new int[5][8];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 8; j++) {
				matrix[i][j] = (int) (Math.random() * 199 - 99);
				printWriter.print(matrix[i][j] + " ");
			}
			printWriter.println("<br>");
		}
		int max = matrix[0][0];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 8; j++) {
				if (matrix[i][j] > max) {
					max = matrix[i][j];
				}
			}
		}
		printWriter.println("<br>" + "max="+ max);

	}
}
