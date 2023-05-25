package web;

import java.security.SecureRandom;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

import model.*;
import java.sql.*;

public class Main extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		String ID = session.getId();

		response.setContentType("text/html; charset=UTF-8");

		SecureRandom rand = new SecureRandom();
		char[] symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();
		StringBuilder rndmString = new StringBuilder();
		for (int i = 0; i < 20; i++)
			rndmString.append(symbols[rand.nextInt(symbols.length - 1)]);
		String strResponse = rndmString.toString();

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection connection = AcessoBanco.conectar();

		try {
			connection.setAutoCommit(false);
			String consultar = "select * from users where token=?";
			ps = connection.prepareStatement(consultar);
			ps.setString(1, ID);
			rs = ps.executeQuery();
			if (!rs.next()) {
				String inserir = "insert into users(token,string) values(?,?)";
				ps = connection.prepareStatement(inserir);
				ps.setString(2, strResponse);
				connection.commit();
			} else {
				strResponse = rs.getString("string");
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
				System.out.println("Successfully rolled back changes from the database!");
			} catch (SQLException e1) {
				System.out.println("Could not rollback updates " + e1.getMessage());
			}
		} finally {
			AcessoBanco.desconectar(connection, ps, rs);
		}

		request.setAttribute("random_string", strResponse);
		RequestDispatcher vista = request.getRequestDispatcher("main.jsp");
		vista.forward(request, response);

	}

}
