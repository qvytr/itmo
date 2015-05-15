package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author q452
 */
class Cell {

	public int rows;
	public int cols;
	public double weight;
	public int width;
	public int height;
}

@SuppressWarnings("serial")
public class BudgetServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		response.setIntHeader("Refresh", 5);
		response.setContentType("text/html");
		response.setStatus(200);

		List<String> row = new ArrayList<>();
		List<Integer> val = new ArrayList<>();

		PrintWriter pw = response.getWriter();
		int maxcell = Integer.parseInt(req.getParameter("max"));
		int sum = 0;
		String bsum = "budgetsum";
		String brow = "budgetrow";
		for (int i = 0; i < maxcell; i++) {
			sum += Integer.parseInt(req.getParameter(bsum.concat(Integer
					.toString(i + 1))));
		}

		for (int i = 0; i < maxcell; i++) {
			row.add(req.getParameter(brow.concat(Integer.toString(i + 1))));
			val.add(Integer.parseInt(req.getParameter(bsum.concat(Integer
					.toString(i + 1)))));
		}

		int m;
		String str;
		if (maxcell > 1) {
			for (int i = 0; i < maxcell; i++) {
				for (int j = 1; j < maxcell; j++) {
					if (val.get(j - 1) < val.get(j)) {
						m = val.get(j);
						val.set(j, val.get(j - 1));
						val.set(j - 1, m);
						str = row.get(j);
						row.set(j, row.get(j - 1));
						row.set(j - 1, str);
					}
				}
			}
		}
		int n = 0;
		m = maxcell % 2 == 0 ? (maxcell + 2) / 2 : (maxcell + 1) / 2;
		n = maxcell % 2 == 0 ? m - 1 : m;

		Cell[] cells = new Cell[maxcell];
		for (int i = 0; i < maxcell; i++) {
			cells[i] = new Cell();
		}

		cells[0].width = (int) (100 * cells[0].weight);
		cells[0].height = 100;

		for (int i = 0; i < maxcell; i++) {
			cells[i].rows = i % 2 == 0 ? n - i / 2 : 1;
			cells[i].cols = i % 2 == 0 ? 1 : m - 1 - i / 2;
			cells[i].weight = 100 * val.get(i) / (double) sum;
		}

		int w = 0;
		int h = 0;

		if (maxcell > 1) {
			cells[0].height = 100;
			cells[0].width = (int) (cells[0].weight);
			w = cells[0].width;
			cells[1].width = 100 - w;
			cells[1].height = (int) (cells[1].weight * 100 / ((double) (cells[1].width)));
			h = cells[1].height;

			for (int i = 2; i < maxcell; i++) {
				if (i % 2 == 0) {
					cells[i].height = 100 - h;
					cells[i].width = (int) (cells[i].weight
							/ (double) (100 - h) * 100);
					w += cells[i].width;

				} else {
					cells[i].width = 100 - w;
					cells[i].height = (int) (cells[i].weight / (100 - w) * 100);
					h += cells[i].height;

				}
			}

		} else {
			cells[0].height = 100;
			cells[0].width = 100;
		}

		String innerHtml = "";
		String in = "";
		String tl = "";
		String in1 = "";
		String tl1 = "";

		for (int i = 0; i < maxcell - 1; i += 2) {

			if (cells[i].weight < 10) {
				in = row.get(i);
				in += " (" + Integer.toString((val.get(i) * 100 / sum));
				in += "%) " + Integer.toString(val.get(i)) + " rub;";

				tl = "title=\"" + in + "\"";
				in = "";
			} else {

				in = row.get(i);
				in += " (" + Integer.toString((val.get(i) * 100 / sum));
				in += "%) " + Integer.toString(val.get(i)) + " rub;";

				tl = "";
			}
			if (cells[i + 1].weight < 10) {

				in1 = row.get(i + 1);
				in1 += " (" + Integer.toString((val.get(i + 1) * 100 / sum));
				in1 += "%) " + Integer.toString(val.get(i + 1)) + " rub;";

				tl1 = "title=\"" + in1 + "\"";
				in1 = "";
			} else {
				in1 = row.get(i + 1);
				in1 += " (" + Integer.toString((val.get(i + 1) * 100 / sum));
				in1 += "%) " + Integer.toString(val.get(i + 1)) + " rub;";

				tl1 = "";
			}

			innerHtml += "<tr>";
			innerHtml += "<td align=center " + tl + " rowspan=" + cells[i].rows
					+ " colspan=" + cells[i].cols + " width=" + cells[i].width
					+ "%" + " height=" + cells[i].height + "%>" + in + "</td>";
			innerHtml += "<td align=center " + tl1 + " rowspan="
					+ cells[i + 1].rows + " colspan=" + cells[i + 1].cols
					+ " height=" + cells[i + 1].height + "%" + " width="
					+ cells[i + 1].width + "%>" + in1 + "</td>";
			innerHtml += "</tr>";
		}

		if (maxcell % 2 != 0) {
			if (cells[cells.length - 1].weight < 10) {
				in = row.get(cells.length - 1);
				in += " ("
						+ Integer
								.toString((val.get(cells.length - 1) * 100 / sum));
				in += "%) " + Integer.toString(val.get(cells.length - 1))
						+ " rub;";
				tl = "title=\"" + in + "\"";
				in = "";
			} else {
				in = row.get(cells.length - 1);
				in += " ("
						+ Integer
								.toString((val.get(cells.length - 1) * 100 / sum));
				in += "%)" + Integer.toString(val.get(cells.length - 1))
						+ " rub;";
				tl = "";
			}
			innerHtml += "<tr>";
			innerHtml += "<td align=center " + tl + " rowspan="
					+ cells[cells.length - 1].rows + " colspan="
					+ cells[cells.length - 1].cols + " height="
					+ cells[cells.length - 1].height + "%" + " width="
					+ cells[cells.length - 1].width + "%>" + in + "</td>";
			innerHtml += "</tr>";
		}

		pw.println("<table border='1' width='640' height='480'>" + innerHtml
				+ "</table>");
	}

	

}
