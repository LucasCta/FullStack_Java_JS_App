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

		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
			buffer.append(System.lineSeparator());
		}
		String data = buffer.toString();

		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection connection;

		JSONObject saidaJSON;
		PrintWriter saida = response.getWriter();
		saidaJSON = new JSONObject();

		try {
			connection = AcessoBanco.conectar();
			connection.setAutoCommit(false);
			String consultar = "select * from users where token=?";
			ps = connection.prepareStatement(consultar);
			ps.setString(1, ID);
			rs = ps.executeQuery();
			if (!rs.next()) {
				saidaJSON.put("erro", true);
				saidaJSON.put("msg", "Invalid Session");
				System.out.println("Session does not check");
			} else {
				String strBank = rs.getString("string");
				if (data.contains(strBank)) {
					saidaJSON.put("erro", false);
					saidaJSON.put("msg", "Passed Captcha!");
					System.out.println("Passed Captcha!");
				} else {
					saidaJSON.put("erro", false);
					saidaJSON.put("msg", "Failed Captha...");
					System.out.println("Strings do not check");
				}
			}
			AcessoBanco.desconectar(connection, ps, rs);
		} catch (SQLException e) {
			saidaJSON.put("erro", true);
			saidaJSON.put("msg", "Couldn't Validate Captcha");
			System.out.println("Error consulting database");
		}

		saida.print(saidaJSON);
		saida.flush();

	}

}
