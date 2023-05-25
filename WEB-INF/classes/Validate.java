package web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.json.simple.JSONObject;
import java.io.*;
import java.util.*;

import model.*;
import java.sql.*;

public class Validate extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		String ID = session.getId();

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection connection = AcessoBanco.conectar();

		JSONObject saidaJSON;
		PrintWriter saida = response.getWriter();
		saidaJSON = new JSONObject();

		try {
			connection.setAutoCommit(false);
			String consultar = "select * from users where token=?";
			ps = connection.prepareStatement(consultar);
			ps.setString(1, ID);
			rs = ps.executeQuery();
			if (!rs.next()) {
				saidaJSON.put("erro", true);
				saidaJSON.put("msg", "Invalid Session");
				saida.print(saidaJSON);
				saida.flush();
			} else {
				String strBank = rs.getString("string");
				if (request.getParameter("string").equals(strBank)) {
					saidaJSON.put("erro", false);
					saidaJSON.put("msg", "Passed Captcha!");
					saida.print(saidaJSON);
					saida.flush();
				} else {
					saidaJSON.put("erro", false);
					saidaJSON.put("msg", "Failed Captha...");
					saida.print(saidaJSON);
					saida.flush();
				}
			}
		} catch (SQLException e) {
			saidaJSON.put("erro", true);
			saidaJSON.put("msg", "Couldn't Validate Captcha");
			saida.print(saidaJSON);
			saida.flush();
			System.out.println("Error consulting database");
		} finally {
			AcessoBanco.desconectar(connection, ps, rs);
		}

	}

}
