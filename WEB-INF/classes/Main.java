package web;

import java.security.SecureRandom;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

public class Main extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("text/html; charset=UTF-8");

		SecureRandom rand = new SecureRandom();
		char[] symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();
		StringBuilder rndmString = new StringBuilder();
		for (int i = 0; i < 20; i++)
			rndmString.append(symbols[rand.nextInt(symbols.length - 1)]);

		request.setAttribute("random_string", rndmString.toString());

		RequestDispatcher vista = request.getRequestDispatcher("main.jsp");
		vista.forward(request, response);

	}

}
